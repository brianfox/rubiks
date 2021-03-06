package com.seefoxrun.util;

/**
 * A simple and perhaps naive class that can be used as a performance 
 * stopwatch for tasks.  The timing measurements are based on 
 * System.nanoTime() and Runtime.getRuntime(), inheriting all the 
 * advantages and disadvantages of these methods.
 * 
 * Various methods can show the elapsed times and memory usage in 
 * different units of measurement.
 * 
 * @author Brian Fox
 *
 */
public class PerformanceTimer {

	long timeStart; 
	long memStart;
	
	boolean running;
	
	public PerformanceTimer() {
		reset();
	}

	public void reset() {
		timeStart = System.nanoTime();
		memStart = Runtime.getRuntime().freeMemory();
		running = true;
	}

	public float millisecondsElapsed() {
		return (System.nanoTime() - timeStart)/1000000F;
	}

	public long memoryUsed() {
		return Runtime.getRuntime().freeMemory() - memStart;
	}
	
}
