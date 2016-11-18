package messages;

public class LobbyPlayerReadyMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String teamName;

	public LobbyPlayerReadyMessage(String teamName) {
		super(MessageType.lobbyPlayerReady);
		this.teamName = teamName;
	}
}
