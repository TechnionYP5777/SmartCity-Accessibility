package smartcity.accessibility.exceptions;

/**
 * @author Kolikant
 *
 */
public class illigalString extends Exception {

	private static final long serialVersionUID = 12L;
	
	@Override
	public String getMessage() {
		return "string contained illigel combinations";
	}

}
