package gameplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class Lobby implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String lobbyName, hostName;
	private int gameSize;
	private ArrayList<String> username;
	
	public Lobby(String lobbyName, String hostName, int gameSize, ArrayList<String> username) {
		this.lobbyName = lobbyName;
		this.hostName = hostName;
		this.gameSize = gameSize;
		this.username = username;
	}

	public String getLobbyName() {
		return lobbyName;
	}

	public void setLobbyName(String lobbyName) {
		this.lobbyName = lobbyName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getGameSize() {
		return gameSize;
	}

	public void setGameSize(int gameSize) {
		this.gameSize = gameSize;
	}

	public ArrayList<String> getUsername() {
		return username;
	}

	public void setUsername(ArrayList<String> username) {
		this.username = username;
	}
	

}
