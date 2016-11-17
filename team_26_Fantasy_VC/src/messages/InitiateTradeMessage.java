package messages;

public class InitiateTradeMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InitiateTradeMessage() {
		super(MessageType.initiateTrade);
	}
}
