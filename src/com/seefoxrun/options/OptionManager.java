package com.seefoxrun.options;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class OptionManager implements Comparable<OptionManager> {

	private static final String PROPERTY_NAME_DELIMITER = "_";
	private HashMap<Scope, OptionType> options;

	private class Scope implements Comparable<Scope> {
		private String scope;
		private String key;
		
		public Scope(String scope, String key) {
			this.scope = scope == null ? "" : scope;
			this.key = key;
		}

		@Override
		public int compareTo(Scope s) {
			return (scope + key).compareTo(s.scope + s.key);
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
	        if (!(o instanceof Scope))
	            return false;
	        Scope s = (Scope)o;
	        return (s.key.compareTo(this.key) == 0 && s.scope.compareTo(this.scope) == 0);
	    }
		
		@Override
		public String toString() {
			return String.format("Scope: %s   Key: %s", scope, key);
		}
		
		@Override
		public int hashCode() {
			int ret = 0;
			for (byte b : scope.getBytes()) {
				ret += b;
			}
			for (byte b : key.getBytes()) {
				ret += b;
			}
			return ret;
		}

		public Object getScope() {
			return scope;
		}

		public Object getKey() {
			return key;
		}
		
	}
	
	public OptionManager() {
		options = new HashMap<Scope, OptionType>();
	}

	
	public OptionManager(String filename) throws IOException, OptionException {
		this();
		load(filename);
	}

	public OptionManager(OptionList list) {
		this();
		for (OptionList.Entry e : list) {
			this.addOption(e.scope, e.key, e.option);
		}
	}


	public void addOption(String scope, String key, OptionType o) {
		options.put(new Scope(scope,key),o);
	}

	public void addOption(Scope scope, OptionType o) {
		options.put(scope, o);
	}

	public OptionType getOption(String scope, String name) {
		return options.get(new Scope(scope, name));
	}


	public void save(File file) throws IOException {
		Properties p = new Properties();
		
		for (Scope s : options.keySet()) {
			OptionType o = options.get(s);
			p.setProperty(toPropertyName(s,o), options.get(s).toOptionString());
		}
		
		FileOutputStream out = new FileOutputStream(file);
		p.store(out, null);
		out.close();
	}

	
	public void save(String filename) throws IOException {
		save(new File(filename));
	}

	
	public void load(File f) throws IOException, OptionException {
		Properties p = loadIniOrXml(f);
		Enumeration<?> e = p.propertyNames();
		while (e.hasMoreElements()) {
			String key = e.nextElement().toString();
			String[] pieces = key.split(PROPERTY_NAME_DELIMITER);
			String scope = "";
			String name = "";
			String cname = "";
			
			switch (pieces.length) {
				case 2: 
					name = pieces[0];
					cname = pieces[1];
					break;
				case 3: 
					scope = pieces[0];
					name = pieces[1];
					cname = pieces[2];
					break;
				default:	
					throw new OptionException("Invalid key: " + key);
			}

			try {
				OptionType i = constructIOption(name, cname, p.getProperty(key));
				options.put(new Scope(scope, name), i);
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (OptionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	private OptionType constructIOption(String key, String cname, String value) 
	throws 
		OptionException, 
		ClassNotFoundException, 
		InstantiationException, 
		IllegalAccessException, 
		IllegalArgumentException, 
		InvocationTargetException 
	{
		Class<?> c = Class.forName(cname);
		if (!implementsIOption(c))
			throw new OptionException("Class " + cname + " does not implement the IOption interface");
		
		Constructor<?> ct;
		ct = getDefaultConstructor(c);
		if (ct != null) {
			OptionType io = (OptionType)ct.newInstance();
			io.fromOptionString(value);
			return io;
		}
		ct = getStringConstructor(c);
		if (ct != null) {
            Object arglist[] = new Object[1];
            arglist[0] = value;

			OptionType io = (OptionType)ct.newInstance(arglist);
			return io;
		}
		return null;
	}
	
	private boolean implementsIOption(Class<?> c) {
		Class<?>[] i = c.getInterfaces();
		for (Class<?> c1 : i) {
			if (c1 == OptionType.class) {
				return true;
			}
		}
		return false;
	}

	private Constructor<?> getDefaultConstructor(Class<?> c) {
		Constructor<?> list[] = c.getDeclaredConstructors();
		for (Constructor<?> ct : list) {
			if (ct.getParameterTypes().length ==0)
				return ct;
		}
		return null;
	}		
		
	private Constructor<?> getStringConstructor(Class<?> c) {
		Constructor<?> list[] = c.getDeclaredConstructors();
		for (Constructor<?> ct : list) {
			Class<?> pvec[] = ct.getParameterTypes();
			if (pvec.length != 1)
				continue;
			for (Class<?> p : pvec)
				if (p == String.class)
					return ct;
		}
		return null;
	}

	public void load(String filename) throws IOException, OptionException {
		load(new File(filename));
	}

	public void load(OptionManager o) {
		for (Scope s : o.options.keySet()) {
			this.addOption(s, o.options.get(s));
		}
	}

	private Properties loadIniOrXml(File f) throws IOException {
		Properties p = new Properties();
		FileInputStream in = new FileInputStream(f);
		try {
				p.load(in);
		}
		catch (IOException e1) {
			try {
				p.loadFromXML(in);
			}
			catch (IOException e2) {
				throw new IOException("Could not load file " + f.getName() + " as XML or text.");
			}
		}
		in.close();
		
		return p;
	}
	
	private String toPropertyName(Scope s, OptionType o) {
		StringBuilder sb = new StringBuilder();
		if (s.scope != null && s.scope.trim().length() != 0)
			sb.append(s.scope + PROPERTY_NAME_DELIMITER);
		sb.append(s.key + PROPERTY_NAME_DELIMITER);
		sb.append(o.getClass().getName());
		return sb.toString();
	}

	
	/*
	private String fromPropertyName(Scope s, OptionType o) {
		StringBuilder sb = new StringBuilder();
		if (s.scope != null && s.scope.trim().length() != 0)
			sb.append(s.scope + PROPERTY_NAME_DELIMITER);
		sb.append(s.scope + PROPERTY_NAME_DELIMITER);
		sb.append(o.getClass().getName());
		return sb.toString();
	}
	*/


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String scopeFormat = "%s:%s=";
		int maxScope = 0;
		
		for (Scope s : options.keySet()) {
			int len = String.format(scopeFormat, s.getScope(), s.getKey()).length();
			maxScope = (len > maxScope) ? len : maxScope;
		}
		for (Scope s : options.keySet()) {
			OptionType o = options.get(s);
			String scope = String.format(scopeFormat, s.getScope(), s.getKey());
			sb.append(String.format("%-" + maxScope + "s %s%n", scope, o.toOptionString()));
		}
		return sb.toString();
	}
	
	
	@Override
	public int compareTo(OptionManager arg0) {
		if (options.size() != arg0.options.size())
			return options.size() - arg0.options.size();
		for (Scope s : options.keySet()) {
			
			if (!arg0.options.containsKey(s)) {
				return -1;
			}
			int diff = options.get(s).compareTo(arg0.options.get(s));
			if (diff != 0) {
				return diff;
			}
		}
		return 0;
	}
	
	public float getFloat(String scope, String key) {
		return (Float)(getOption(scope, key).getValue());
	}

	public int getInt(String scope, String key) {
		return (Integer)(getOption(scope, key).getValue());
	}

	public boolean getBoolean(String scope, String key) {
		return (Boolean)(getOption(scope, key).getValue());
	}

	public String getString(String scope, String key) {
		return (String)(getOption(scope, key).getValue());
	}
}
