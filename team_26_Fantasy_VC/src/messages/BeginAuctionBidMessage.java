package messages;

public class BeginAuctionBidMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companyName;
	
	public BeginAuctionBidMessage(String companyName) {
		super(MessageType.beginBid);
		this.companyName = companyName;
	} 
	
	public String getCompanyName(){
		return companyName;
	}
}
