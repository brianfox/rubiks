package com.seefoxrun.visualization.measurement;

public class Conversion {
	final public static double INCHES_TO_MM     = 25.4;
	final public static double INCHES_TO_INCHES =  1.0;
	final public static double INCHES_TO_DOTS   = 72.0;

	final public static double MM_TO_MM         = 1.0;
	final public static double MM_TO_INCHES     = 1/INCHES_TO_MM;
	final public static double MM_TO_DOTS       = MM_TO_INCHES * INCHES_TO_DOTS;

	final public static double DOTS_TO_INCHES   = 1/INCHES_TO_DOTS;
	final public static double DOTS_TO_MM       = 1/MM_TO_DOTS;
	final public static double DOTS_TO_DOTS     = 1.0;

}
