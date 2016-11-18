package server;

import java.util.ArrayList;
import java.util.Vector;

import messages.Message;
import messages.UserListMessage;

public class ServerLobby extends Thread{
	private Vector<ServerClientCommunicator> sccVector;
	private ArrayList<String> usernames;
	private Server server;
	private String lobbyName, hostName;
	private int numPlayers;
	
	public ServerLobby(Vector<ServerClientCommunicator> sccVector, Server server, String lobbyName, String hostName, int numPlayers) {
		this.sccVector = sccVector;
		this.server = server;
		this.lobbyName = lobbyName;
		this.hostName = hostName;
		this.numPlayers = numPlayers;
		usernames = new ArrayList<String>();
		usernames.add(hostName);
		this.start();
		String [] arr = new String[usernames.size()];
		for (int i = 0; i < usernames.size(); i++) {
			arr[i] = usernames.get(i);
		}
		sendToAll(new UserListMessage(arr, numPlayers - usernames.size()));
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
	
	public ArrayList<String> getUserNames() {
		return usernames;
	}
	
	public void sendToAll(Message msg) {
		System.out.println("send");
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
		// TODO: send to people in the lobby how many to wait on		
		String [] arr = new String[usernames.size()];
		for (int i = 0; i < usernames.size(); i++) {
			arr[i] = usernames.get(i);
		}
		sendToAll(new UserListMessage(arr, numPlayers - usernames.size()));
	}
	
	public void run() {
		while (sccVector.size() < numPlayers);
		// TODO: Send signal that the lobby has enough players and start game
		server.removeServerLobby(this);
	}
}
