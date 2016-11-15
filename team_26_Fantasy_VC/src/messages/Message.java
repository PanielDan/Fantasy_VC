package messages;

import java.io.Serializable;

/**
 * 
 * @author alancoon
 *
 */
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static enum MessageType {addUser, startGame, beginAuction, endGame, clientExit,
<<<<<<< HEAD
		beginQuarterly, beginTimelapse, chatMessage, quarterlyReady, initiateTrade, login, createGame};
=======
		beginQuarterly, beginTimelapse, chatMessage, userInfo, beginBid, AuctionDetailsUpdateUser,
		AuctionDetailsUpdateCompany};
>>>>>>> origin/danny_branch
	
	private MessageType type;
	
	public Message(MessageType type){
		this.type = type;
	}
	
	public MessageType getType(){
		return type;
	}
	
}
