package messages;

import java.io.Serializable;
import java.util.Vector;

import gameplay.User;

public class ReadyGameMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<User> users;
	
	public ReadyGameMessage(Vector<User> users) {
		this.users = users;
	}
	
	public Vector<User> getUsers() {
		return users;
	}

}
