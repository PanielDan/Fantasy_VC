package messages;

/**
 * Send the {@code ClientExitMessage} to all {@code Client} classes 
 * connected to a game when one of the players in the game disconnects.
 * @author alancoon
 *
 */
public class AuctionDetailsUpdateCompanyMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String companyName;
	
	public AuctionDetailsUpdateCompanyMessage(String companyName) {
		super(MessageType.AuctionDetailsUpdateCompany);
		this.companyName = companyName;
	}
	
	public String getCompanyName() {
		return companyName;
	}
}
