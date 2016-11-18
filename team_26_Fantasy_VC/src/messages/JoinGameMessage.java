package messages;

public class JoinGameMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String teamName;

	public JoinGameMessage(String teamName) {
		super(MessageType.joinGame);
		this.teamName = teamName;
	}

}
