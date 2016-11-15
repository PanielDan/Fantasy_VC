package messages;

public class BeginAuctionMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BeginAuctionMessage() {
		super(MessageType.beginAuction);
	} 
}
