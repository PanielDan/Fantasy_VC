package messages;

/**
 * Send the {@code ClientExitMessage} to all {@code Client} classes 
 * connected to a game when one of the players in the game disconnects.
 * @author alancoon
 *
 */
public class AuctionDetailsUpdateUserMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	public AuctionDetailsUpdateUserMessage(String username) {
		super(MessageType.AuctionDetailsUpdateUser);
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}
