package messages;

import java.io.Serializable;
import java.util.Vector;

import gameplay.User;

public class FinalMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<User> winners;
	
	public FinalMessage(Vector<User> winners) {
		this.winners = winners;
	}
	
	public Vector<User> getWinners() {
		return winners;
	}

}
