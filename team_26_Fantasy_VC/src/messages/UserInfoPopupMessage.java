package messages;

import java.io.Serializable;

/**
 * Send the {@code ClientExitMessage} to all {@code Client} classes 
 * connected to a game when one of the players in the game disconnects.
 * @author alancoon
 *
 */
public class UserInfoPopupMessage extends Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserInfoPopupMessage() {
		super(MessageType.userInfo);
	}
}
