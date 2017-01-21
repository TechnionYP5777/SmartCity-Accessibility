package smartcity.accessibility.navigation.exception;

/**
 * This class represent that the communication with MapQuest servers failed for
 * some reason
 * 
 * @author yael
 *
 */
public class CommunicationFailed extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public CommunicationFailed(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
