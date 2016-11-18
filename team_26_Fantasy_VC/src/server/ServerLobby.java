package server;

import java.util.Vector;

import messages.Message;
import gameplay.Game;

public class ServerLobby extends Thread{
	private Vector<ServerClientCommunicator> sccVector;
	private Server server;
	private String lobbyName;
	private int hostID, numPlayers;
	private Game game;
	
	public ServerLobby(Vector<ServerClientCommunicator> sccVector, Server server, String lobbyName, int hostID, int numPlayers, Game game) {
		this.sccVector = sccVector;
		this.server = server;
		this.lobbyName = lobbyName;
		this.hostID = hostID;
		this.numPlayers = numPlayers;
		this.game = game;
	}
	
	public String getLobbyName() {
		return lobbyName;
	}
	
	public void sendToAll(Message msg) {
		for (ServerClientCommunicator scc : sccVector) {
			scc.sendMessage(msg);
		}
	}
	
	public void addToLobby(ServerClientCommunicator scc) {
		sccVector.add(scc);
		// TODO: send to people in the lobby how many to wait on
	}
	
	public void run() {
		while (sccVector.size() < numPlayers);
		// TODO: Send signal that the lobby has enough players and start game
		server.removeServerLobby(this);
	}
}
