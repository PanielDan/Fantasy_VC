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
		beginQuarterly, beginTimelapse, chatMessage};
	
	private MessageType type;
	
	public Message(MessageType type){
		this.type = type;
	}
	
	public MessageType getType(){
		return type;
	}
	
}
