package messages;

public class AcceptTradeMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AcceptTradeMessage() {
		super(MessageType.acceptTrade);
	}
}
