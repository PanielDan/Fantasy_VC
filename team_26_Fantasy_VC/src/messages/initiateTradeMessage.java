package messages;

public class initiateTradeMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public initiateTradeMessage() {
		super(MessageType.initiateTrade);
	}
}
