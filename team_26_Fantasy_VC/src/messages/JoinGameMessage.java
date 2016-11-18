package messages;

public class JoinGameMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String username, lobbyName;

	public JoinGameMessage(String username, String lobbyName) {
		super(MessageType.joinGame);
		this.username = username;
		this.lobbyName = lobbyName;
	}

}
