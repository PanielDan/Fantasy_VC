package messages;

public class LobbyPlayerReadyMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LobbyPlayerReadyMessage() {
		super(MessageType.lobbyPlayerReady);
		
	}
}
