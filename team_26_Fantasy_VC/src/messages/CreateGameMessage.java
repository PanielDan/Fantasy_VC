package messages;

import gameplay.User;

public class CreateGameMessage extends Message{
	public String gamename;
	public User hostUser;
	public int numUsers;
	public CreateGameMessage(String gamename, int numUsers, User hostUser) {
		super(MessageType.createGame);
		this.gamename = gamename;
		this.numUsers = numUsers;
		this.hostUser = hostUser;
	}
}
