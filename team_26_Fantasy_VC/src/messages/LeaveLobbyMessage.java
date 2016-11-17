package messages;

public class LeaveLobbyMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LeaveLobbyMessage() {
		super(MessageType.leaveLobby);
	}
}
