package messages;

import gameplay.User;

public class InitiateTradeMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User initiator;
	private User target;
	
	// send a notification.
	public InitiateTradeMessage(User initiator, User target) {
		super(MessageType.initiateTrade);
		this.initiator = initiator;
		this.target = target;
	}

	public User getInitiator() { 
		return initiator;
	}
	
	public User getTarget() {
		return target;
	}
}
