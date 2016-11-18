package messages;

import java.util.Vector;

public class UserListMessage extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String [] user;
	public int waitingOn;

	public UserListMessage(String [] user, int waitingOn) {
		super(MessageType.UserList); 
		this.user = user;
		this.waitingOn = waitingOn;
		for(String u : user) {
			System.out.println(u);
		}
	}

}
