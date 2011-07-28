/*
 * $Id: Report.java,v 1.2 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: Report.java,v $
 * Revision 1.2  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.rubiks.model.stateful.tree;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.seefoxrun.rubiks.model.cube.Cube;

public class Report {
	
	
	public static void printHeader(int parms) {
		String hostName = "Unknown";
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostName = addr.getHostName();
		} catch (Exception e) {
		}
		
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String now = sdf.format(cal.getTime());


		System.out.printf("%-15s %-25s%n", "Hostname:", hostName);
		System.out.printf("%-15s %-25s%n", "Date:", now);
		System.out.printf("%-15s %-25s%n", "Spatial:", (parms & Cube.USE_SPATIAL) != 0 ? "yes" : "no");
		System.out.printf("%-15s %-25s%n", "Reverse:", (parms & Cube.USE_REVERSE) != 0  ? "yes" : "no");
		System.out.printf("%-15s %-25s%n", "Color Map:", (parms & Cube.USE_COLORMAP) != 0  ? "yes" : "no");
		
		System.out.printf("LVL      FOUND     STORED     LIVING       DEAD   SYMMETRY       TIME%n");
		System.out.printf("---------------------------------------------------------------------%n");

	}

	public static void printLevel(int step, int nFound, int nStored, int nliving, int ndead, int sym, float estimatedTime, int[] histogram) {
		StringBuilder sb = new StringBuilder();
		for (int i : histogram)
			sb.append(i + " ");
		

		System.out.printf(
				"%3d %10d %10d %10d %10d %10d %10.3f %s%n",
				step, 
				nFound,
				nStored,
				nliving, 
				ndead,
				sym,
				estimatedTime,
				sb
			);
	}
	
	public static void printFooter() {
	}

}
