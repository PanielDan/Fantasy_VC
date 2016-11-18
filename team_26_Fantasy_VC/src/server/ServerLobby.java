package server;

import java.util.Vector;

import messages.Message;
import gameplay.Game;

public class ServerLobby extends Thread{
	private Vector<ServerClientCommunicator> sccVector;
	private Server server;
	private String lobbyName, hostName;
	private int numPlayers;
	
	public ServerLobby(Vector<ServerClientCommunicator> sccVector, Server server, String lobbyName, String hostName, int numPlayers) {
		this.sccVector = sccVector;
		this.server = server;
		this.lobbyName = lobbyName;
		this.hostName = hostName;
		this.numPlayers = numPlayers;
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
