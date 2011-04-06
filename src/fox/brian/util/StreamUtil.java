/*
 * $Id: StreamUtil.java,v 1.2 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: StreamUtil.java,v $
 * Revision 1.2  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {
	static public byte[] readFully(InputStream in, byte[] b) throws IOException {
	    readFully(in, b, 0, b.length);
	    return b;
	}

	static public byte[] readFully(InputStream in, byte[] b, int off, int len) throws IOException
	{
	    while (len > 0) {
			int n = in.read(b, off, len);
			if (n < 0) 
			    throw new EOFException();
			
			off += n;
			len -= n;
	    }
	    return b;
	}
}




