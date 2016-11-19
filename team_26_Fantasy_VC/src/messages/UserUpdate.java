package messages;

import java.io.Serializable;
import java.util.Vector;

import gameplay.Company;
import gameplay.User;

public class UserUpdate  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User username;
	
	public UserUpdate(User username) {
		this.username = username;
	}
	
	public User getUser() {
		return username;
	}
	

}
