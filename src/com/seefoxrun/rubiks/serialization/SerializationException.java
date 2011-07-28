/*
 * $Id: SerializationException.java,v 1.2 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: SerializationException.java,v $
 * Revision 1.2  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.rubiks.serialization;

public class SerializationException extends Exception {

	public SerializationException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;

}
