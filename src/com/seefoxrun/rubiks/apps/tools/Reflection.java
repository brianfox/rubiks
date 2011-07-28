package com.seefoxrun.rubiks.apps.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Reflection {

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		Class c = Class.forName("com.seefoxrun.options.OptionString");
		Method m[] = c.getDeclaredMethods();
		for (Method m1 : m)
			System.out.println(m1.toString());

		Constructor ctorlist[] = c.getDeclaredConstructors();
		for (int i = 0; i < ctorlist.length; i++) {
			Constructor ct = ctorlist[i];
			System.out.println("name = " + ct.getName());
			System.out.println("decl class = " + ct.getDeclaringClass());
			Class pvec[] = ct.getParameterTypes();
			for (int j = 0; j < pvec.length; j++)
				System.out.println("param #" + j + " " + pvec[j]);
			Class evec[] = ct.getExceptionTypes();
			for (int j = 0; j < evec.length; j++)
				System.out.println("exc #" + j + " " + evec[j]);
			System.out.println("-----");
		}
	}

}
