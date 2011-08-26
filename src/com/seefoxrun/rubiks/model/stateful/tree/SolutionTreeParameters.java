package com.seefoxrun.rubiks.model.stateful.tree;

import java.util.Date;

public class SolutionTreeParameters {
	private Date date;
	private String osArch;
	private String osName;
	private String osVersion;
	private String command;

	private int processors;
	private long maxMemory;
	
	private String name;
	private boolean useSpatial;
	private boolean useColorMap;

	public SolutionTreeParameters(
			String name, 
			boolean useSpatial, 
			boolean useColorMap
	) {
		this.date = new Date();
		
		Runtime runtime = Runtime.getRuntime();
	    this.processors = runtime.availableProcessors();
		this.maxMemory = runtime.maxMemory();

		this.osArch = System.getProperty("os.arch");
		this.osName = System.getProperty("os.name");
		this.osVersion = System.getProperty("os.version");
		this.command = System.getProperty("sun.java.command");

		
		this.name = name;
		this.useSpatial = useSpatial;
		this.useColorMap = useColorMap;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Date:             %s%n" +
				"Command:          %s%n" +
				"Processors:       %d%n" +
				"Memory:           %d%n" +
				"OS Architecture:  %s%n" +
				"OS Name:          %s%n" +
				"OS Version:       %s%n%n" +
				"Name:             %s%n" +
				"Spatial:          %s%n" +
				"ColorMap:         %s",
				date,
				command,
				processors,
				maxMemory,
				osArch,
				osName,
				osVersion,
				name,
				useSpatial, 
				useColorMap);
	}
}
