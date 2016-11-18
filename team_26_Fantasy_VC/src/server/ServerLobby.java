package server;

import java.util.Vector;

import messages.Message;

public class ServerLobby extends Thread{
	private Vector<ServerClientCommunicator> sccVector;
	private Vector<String> usernames;
	private Server server;
	private String lobbyName, hostName;
	private int numPlayers;
	
	public ServerLobby(Vector<ServerClientCommunicator> sccVector, Server server, String lobbyName, String hostName, int numPlayers) {
		this.sccVector = sccVector;
		this.server = server;
		this.lobbyName = lobbyName;
		this.hostName = hostName;
		this.numPlayers = numPlayers;
		usernames = new Vector<String>();
		usernames.add(hostName);
		this.start();
	}
	
	public String getLobbyName() {
		return lobbyName;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public int getGameSize() {
		return numPlayers;
	}
	
	public Vector<String> getUserNames() {
		return usernames;
	}
	
	public void sendToAll(Message msg) {
		for (ServerClientCommunicator scc : sccVector) {
			scc.sendMessage(msg);
		}
	}
	
	public void addToLobby(ServerClientCommunicator scc, String username) {
		System.out.println("add");
		sccVector.add(scc);
		usernames.add(username);
		for(String u : usernames) {
			System.out.println(u);
		} 
		System.out.println(sccVector.size() + " " + numPlayers);
		// TODO: send to people in the lobby how many to wait on
	}
	
	public void run() {
		while (sccVector.size() < numPlayers);
		System.out.print("full");
		// TODO: Send signal that the lobby has enough players and start game
		server.removeServerLobby(this);
	}
}
