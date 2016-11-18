package messages;

import java.util.Vector;

import gameplay.Company;

public class AcceptTradeMessage extends Message{
	/**
	 * 
	 */
	public String team1, team2;
	public Vector <Company> team1Trade, team2Trade;
	private static final long serialVersionUID = 1L;

	public AcceptTradeMessage(String team1, String team2, Vector <Company> team1Trade, Vector <Company> team2Trade) {
		super(MessageType.acceptTrade);
		this.team1 = team1;
		this.team2 = team2;
		this.team1Trade = team1Trade;
		this.team2Trade = team2Trade;
	}
}
