package messages;

public class DeclineTradeMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeclineTradeMessage() {
		super(MessageType.declineTrade);
	}
}
