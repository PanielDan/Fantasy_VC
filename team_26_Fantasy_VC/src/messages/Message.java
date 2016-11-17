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
		beginQuarterly, beginTimelapse, chatMessage, quarterlyReady, initiateTrade, login, createGame, userInfo, beginBid, AuctionDetailsUpdateUser,
		AuctionDetailsUpdateCompany, acceptTrade, declineTrade, lobbyPlayerReady, leaveLobby, hostGame, joinGame, createAccount}; 
=======
		beginQuarterly, beginTimelapse, chatMessage, quarterlyReady, initiateTrade, login, createGame,
		userInfo, beginBid, AuctionDetailsUpdateUser, AuctionDetailsUpdateCompany, AuctionBidUpdate};

>>>>>>> e185bcfed0f3653b6cc75ba7e0d8d9b0278f4dd5
	
	private MessageType type;
	
	public Message(MessageType type){
		this.type = type;
	}
	
	public MessageType getType(){
		return type;
	}
	
}
