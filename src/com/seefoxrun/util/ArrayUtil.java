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
	 * Returns an array of bytes representing the value of the integer provided.
	 *   
	 * @param integer The integer to be converted.
	 * @return a byte array.
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


