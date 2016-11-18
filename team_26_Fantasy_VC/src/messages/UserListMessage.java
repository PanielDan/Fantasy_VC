package messages;

import java.util.Vector;

public class UserListMessage extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Vector<String> user;
	public int waitingOn;

	public UserListMessage(Vector<String> user, int waitingOn) {
		super(MessageType.UserList); 
		this.user = user;
		this.waitingOn = waitingOn;
		for(String u : user) {
			System.out.println(u);
		}
	}

}
