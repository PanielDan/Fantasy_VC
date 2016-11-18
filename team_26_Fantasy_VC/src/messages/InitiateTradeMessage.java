package messages;

public class InitiateTradeMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String offeringTeam, receivingTeam;
	
	// send a notification.
	public InitiateTradeMessage(String offeringTeam, String receivingTeam) {
		super(MessageType.initiateTrade);
		this.offeringTeam = offeringTeam;
		this.receivingTeam = receivingTeam;
	}
}
