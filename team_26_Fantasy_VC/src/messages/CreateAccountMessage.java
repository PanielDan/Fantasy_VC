package messages;

public class CreateAccountMessage extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreateAccountMessage(){
		super(MessageType.createAccount);
	}

}
