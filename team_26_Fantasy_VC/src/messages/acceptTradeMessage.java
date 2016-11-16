package messages;

public class acceptTradeMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public acceptTradeMessage() {
		super(MessageType.acceptTrade);
	}
}
