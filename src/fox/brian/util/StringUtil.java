/*
 * $Id: StringUtil.java,v 1.3 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: StringUtil.java,v $
 * Revision 1.3  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.util;

public class StringUtil {
	static final char[] HEX_ALPHA = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' }; 

	
	/**
	 * Creates an ASCII representation of several faces by repeatedly calling
	 * the concatenateTwoFaceStrings method.
	 */
	public static StringBuilder concatenateMultipleStrings(String padding,
			StringBuilder... sb) {
		StringBuilder next = new StringBuilder(sb[0]);
		for (int i = 1; i < sb.length; i++) {
			next = concatenateStrings(padding, next, sb[i]);
		}
		return next;
	}

	public static String concatenateStrings(String padding, String first,
			String second) {

		char[] firstarr = first.toCharArray();
		char[] secondarr = second.toCharArray();
		int i = 0;
		int j = 0;

		StringBuilder sb = new StringBuilder();
		while (i < firstarr.length) {
			if (firstarr[i] == '\n') {
				sb.append(padding);
				while (j < secondarr.length && secondarr[j] != '\n')
					sb.append(secondarr[j++]);
				j++;
				i++;
				sb.append('\n');
				continue;
			}
			sb.append(firstarr[i++]);
		}
		while (j < secondarr.length) {
			sb.append(secondarr[j++]);
		}
		return sb.toString();
	}

	/**
	 * This method concatenates two ASCII blocks with the so called "block
	 * paste." Where as normal concatenation methods focus on two long arrays,
	 * this method focuses on two rectangular regions.
	 */
	private static StringBuilder concatenateStrings(String padding,
			StringBuilder first, StringBuilder second) {

		char[] firstarr = first.toString().toCharArray();
		char[] secondarr = second.toString().toCharArray();
		int i = 0;
		int j = 0;

		StringBuilder sb = new StringBuilder();
		while (i < firstarr.length) {
			if (firstarr[i] == '\n') {
				sb.append(padding);
				while (j < secondarr.length && secondarr[j] != '\n')
					sb.append(secondarr[j++]);
				j++;
				i++;
				sb.append('\n');
				continue;
			}
			sb.append(firstarr[i++]);
		}
		return sb;
	}
	

	static public String hexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes)
			sb.append(hex(b) + " ");
		return sb.toString();
	}

	static public String hex(byte b) {
		return new String(new char[] {HEX_ALPHA[b >>> 4],HEX_ALPHA[b & 0xF]}); 
	}

}
