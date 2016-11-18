package messages;

public class CreateGameMessage extends Message{
	public String gamename, hostUser;
	public int numUsers;
	public CreateGameMessage(String gamename, int numUsers, String hostUser) {
		super(MessageType.createGame);
		this.gamename = gamename;
		this.numUsers = numUsers;
		this.hostUser = hostUser;
	}
}
