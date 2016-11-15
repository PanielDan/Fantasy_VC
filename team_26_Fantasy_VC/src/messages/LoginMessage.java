package messages;

public class LoginMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginMessage() {
		super(MessageType.login);
	}
}
