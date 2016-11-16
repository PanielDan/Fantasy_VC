package messages;

/**
 * Send the {@code ClientExitMessage} to all {@code Client} classes 
 * connected to a game when one of the players in the game disconnects.
 * @author alancoon
 *
 */
public class AuctionBidUpdateMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private int bidAmount;
	
	public AuctionBidUpdateMessage(String username, int amount) {
		super(MessageType.AuctionBidUpdate);
		this.username = username;
		bidAmount = amount;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getBidAmount(){
		return bidAmount;
	}
}
