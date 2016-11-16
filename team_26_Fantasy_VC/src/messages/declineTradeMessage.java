package messages;

public class declineTradeMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public declineTradeMessage() {
		super(MessageType.declineTrade);
	}
}
