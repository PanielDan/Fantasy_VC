package messages;

public class LeaveLobbyMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String teamName;

	public LeaveLobbyMessage(String teamName) {
		super(MessageType.leaveLobby);
		this.teamName = teamName;
	}
}
