package messages;

public class CreateGameMessage extends Message{
	public String gamename;
	public int numUsers;
	public CreateGameMessage(String gamename, int numUsers) {
		super(MessageType.createGame);
		this.gamename = gamename;
		this.numUsers = numUsers;
	}
}
