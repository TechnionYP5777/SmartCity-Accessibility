package smartcity.accessibility.exceptions;

import smartcity.accessibility.socialnetwork.User.Privilege;

/**
 * @author Arthur
 *
 */
public class UnauthorizedAccessException extends Exception {

	/**
	 * No idea what this is.
	 */
	private static final long serialVersionUID = -1118622793282487750L;
	
	private Privilege p;
	private String msg;
	
	public UnauthorizedAccessException(Privilege minPrivLevel){
		this.p = minPrivLevel;
		msg = "User is not allowed to preform this action! This action "
				+ "requires privilege level of at least " + p;
	}
	
	public Privilege getPrivilegeLevel(){
		return p;
	}
	
	@Override
	public String getMessage() {
		return msg+"\n"+super.getMessage();
	}

	@Override
	public String toString() {
		return msg+"\n"+super.toString();
	}
	

}
