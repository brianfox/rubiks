/*
 * $Id: StateException.java,v 1.2 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: StateException.java,v $
 * Revision 1.2  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.rubiks.model.stateful.experimental;

public class StateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StateException(String message) {
		super(message);
	}

}
