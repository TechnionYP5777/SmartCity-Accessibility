/**
 * 
 */
package smartcity.accessibility.exceptions;

/**
 * @author ArthurSap
 *
 */
public class EmptySearchQuery extends Exception {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Query didn't return any availble location";
	}

}
