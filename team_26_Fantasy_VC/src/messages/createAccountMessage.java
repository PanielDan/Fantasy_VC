package messages;

public class createAccountMessage extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public createAccountMessage(){
		super(MessageType.createAccount);
	}

}
