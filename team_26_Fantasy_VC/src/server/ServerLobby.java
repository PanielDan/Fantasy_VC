package server;

import java.util.Vector;

import gameplay.User;
import messages.ReadyGameMessage;
import messages.UserListMessage;

public class ServerLobby extends Thread{
	private Vector<ServerClientCommunicator> sccVector;
	private Vector<User> users;
	private Server server;
	private String lobbyName, hostName;
	private int numPlayers;
	
	public ServerLobby(Vector<ServerClientCommunicator> sccVector, Server server, String lobbyName, User host, int numPlayers) {
		this.sccVector = sccVector;
		this.server = server;
		this.lobbyName = lobbyName;
		this.hostName = hostName;
		this.numPlayers = numPlayers;
		users = new Vector<User>();
		users.add(host);
		this.start();
		sendToAll(new UserListMessage(users, numPlayers - users.size()));
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
	
	public Vector<User> getUsers() {
		return users;
	}
	
	public void sendToAll(Object msg) {
		System.out.println("send");
		for (ServerClientCommunicator scc : sccVector) {
			scc.sendMessage(msg);
		}
	}
	
	public void addToLobby(ServerClientCommunicator scc, User user) {
		System.out.println("add");
		sccVector.add(scc);
		users.add(user);
		// TODO: send to people in the lobby how many to wait on		
		sendToAll(new UserListMessage(users, numPlayers - users.size()));
	}
	
	public synchronized boolean checkReady() {
		for (User user : users) {
			if (!user.getReady()) {
				return false;
			}
		}
		return true;
	}
	
	public synchronized void setReady(String username, String teamname) {
		for (User user : users) {
			if(user.getUsername().equals(username)) {
				System.out.println(user.getUsername() + " ready");
				user.setReady();
				user.setCompanyName(teamname);
			}
		}
	}
	
	public void run() {
		while (sccVector.size() < numPlayers);
		// TODO: Send signal that the lobby has enough players and start game
		server.removeServerLobby(this);
		
		while(!checkReady());
		this.sendToAll(new ReadyGameMessage(users));
	}
}
