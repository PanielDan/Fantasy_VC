package messages;

import gameplay.Game;

public class BeginQuarterlyMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	public BeginQuarterlyMessage(Game game) {
		super(MessageType.beginQuarterly);
		this.game = game;
	}
	
	public Game getGame() { 
		return game;
	}
}
