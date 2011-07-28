/*
 * $Id: ArrayUtil.java,v 1.3 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: ArrayUtil.java,v $
 * Revision 1.3  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package com.seefoxrun.util;

public class ArrayUtil {
	public static byte[] concatenateArrays(byte[]... sources) {
		int length = 0;
		for (byte[] array : sources)
			length += array.length;
		byte[] ret = new byte[length];
		length = 0;
		for (byte[] array : sources) {
			for (byte b : array) {
				ret[length++] = b;
			}
		}
		return ret;
	}


	public static byte[][] chopArrays(byte[] src, int... offsets) {
		int length = 0;
		int arrayCount = 0;
		
		byte[][] ret = new byte[offsets.length][];
		
		for (int x: offsets) {
			ret[arrayCount] = new byte[x];
			for (int i=0; i < x; i++) {
				ret[arrayCount][i] = src[length + i];
				length++;
			}
			arrayCount++;
		}
		return ret;
	}

	
	/**
	 * Returns a byte array containing the two's-complement representation of the integer.<br>
	 * The byte array will be in big-endian byte-order with a fixes length of 4
	 * (the least significant byte is in the 4th element).<br>
	 * <br>
	 * <b>Example:</b><br>
	 * <code>intToByteArray(258)</code> will return { 0, 0, 1, 2 },<br>
	 * <code>BigInteger.valueOf(258).toByteArray()</code> returns { 1, 2 }. 
	 * @param integer The integer to be converted.
	 * @return The byte array of length 4.
	 */
	public static byte[] intToByteArray (final int integer) {
		byte[] data = new byte[4];
		
		data[0] = (byte)((integer >> 24));
		data[1] = (byte)((integer >> 16));
		data[2] = (byte)((integer >> 8));
		data[3] = (byte)(integer);
		
		return data;
	}


	public static int byteArrayToInt (byte[] data) {
		return 
			((data[0] & 0x00000000FF) << 24) + 
			((data[1] & 0x00000000FF) << 16) +
			((data[2] & 0x00000000FF) << 8 ) + 
			((data[3] & 0x00000000FF)     );
	}

}


