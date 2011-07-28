/*
 * $Id: ByteSerializable.java,v 1.3 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: ByteSerializable.java,v $
 * Revision 1.3  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.util;

public interface ByteSerializable {
	byte[] byteSerialize();
	Object byteDeserialize(byte[] source);
}
