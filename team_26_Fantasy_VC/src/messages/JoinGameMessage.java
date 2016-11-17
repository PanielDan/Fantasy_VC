package messages;

public class JoinGameMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JoinGameMessage() {
		super(MessageType.joinGame);
	}

}
