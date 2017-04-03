package smartcity.accessibility.navigation.mapquestcommunication;

/**
 * This class hold information about the response from mapquest
 * 
 * @author yael
 *
 */
public class Info {
	private Integer statuscode;
	private String[] messages;

	public Info() {
	}

	public Integer getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(Integer statuscode) {
		this.statuscode = statuscode;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

}
