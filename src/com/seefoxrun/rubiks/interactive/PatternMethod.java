package com.seefoxrun.rubiks.interactive;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import com.seefoxrun.rubiks.interactive.Interpreter.InterpreterException;

class PatternMethod {
	private Pattern pattern;
	private Method method;
	
	PatternMethod(Pattern pattern, String methodName) {
		this.pattern = pattern;
		Class<?> c = Interpreter.class;
		Class<?> types[] = new Class[2];
		types[0] = String.class;
		types[1] = PrintStream.class;
		try {
			this.method = c.getMethod(methodName, types);
			System.out.println("Found method: " + methodName);
		}
		catch (NoSuchMethodException ex) {
			System.out.println("No such method: " + methodName);
			
		}
	}
	
	boolean matches(String command) {
		return pattern.matcher(command).matches();
	}
	
	void invoke(Interpreter i, String command, PrintStream out) throws InterpreterException {
		Object arg[] = new Object[2];
		arg[0] = command;
		arg[1] = out;
		try {
			method.invoke(i, arg);
		} catch (IllegalArgumentException e) {
			throw new InterpreterException("Invalid arguments.");				
		} catch (IllegalAccessException e) {
			throw new InterpreterException("Illegal access.");				
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			throw new InterpreterException(cause.getMessage());				
		}
	}
}
