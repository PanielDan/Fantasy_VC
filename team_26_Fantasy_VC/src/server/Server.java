package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import gameplay.Lobby;
import messages.LobbyListMessage;
import messages.Message;

public class Server extends Thread{
	private ServerSocket ss;
	private Vector<ServerClientCommunicator> sccVector;
	private Map<String, ServerLobby> lobbies;
	
	public Server() {
		try {
			ss = new ServerSocket(8008);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		sccVector = new Vector<ServerClientCommunicator>();
		lobbies = new HashMap<String, ServerLobby>();
		this.start();
	}
	
	public void removeServerClientCommunicator(ServerClientCommunicator scc) {
		sccVector.remove(scc);
	}
	
	public void removeServerLobby(ServerLobby sl) {
		System.out.println("remove");
		lobbies.remove(sl.getLobbyName());
		// TODO: Send updated listing of available lobbies
		sendLobbies();
	}
	
	public void run() {
		try {
			while(true) {
				Socket s = ss.accept();
				System.out.println("accepted");
				ServerClientCommunicator scc = new ServerClientCommunicator(s, this);
				scc.start();
				sccVector.addElement(scc);
				sendLobbies();
			}
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (ss != null) {
				try {
					ss.close();							
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
	
	public void sendToAll(Message msg) {
		for(ServerClientCommunicator scc : sccVector) {
			System.out.println("sent");
			scc.sendMessage(msg);
		}
	}
	
	public synchronized void createLobby(ServerClientCommunicator scc, String lobbyName, String hostName, int numPlayers) {
		if (lobbies.containsKey(lobbyName)) {
			// TODO: Send message that lobby name is already taken
			return;
		}
		Vector<ServerClientCommunicator> sccVector2 = new Vector<ServerClientCommunicator>();
		sccVector2.add(scc);
		sccVector.remove(scc);
		
		ServerLobby sl = new ServerLobby(sccVector2, this, lobbyName, hostName, numPlayers);

		scc.setLobby(sl);
		
		lobbies.put(lobbyName, sl);
		sendLobbies();
		System.out.println("Lobby created");
		// TODO: Send updated listing of all the lobbies
	}
	
	public synchronized void addToLobby(ServerClientCommunicator scc, String lobbyName, String username) {
		if(!lobbies.containsKey(lobbyName)) {
			// TODO: Send message that lobby does not exist
			System.out.println("not found");
		}
		else {
			lobbies.get(lobbyName).addToLobby(scc, username);
			sccVector.remove(scc);
			
			scc.setLobby(lobbies.get(lobbyName));
			sendLobbies();
			System.out.println("Lobby joined");
		}
		
		// TODO: Send updated listing of all the lobbies (since they have more players available now)
	}
	
	public void sendLobbies() {
		Vector<Lobby> lobbies = new Vector<Lobby>();
		for(ServerLobby sl : this.lobbies.values()) {
			Lobby lobby = new Lobby(sl.getLobbyName(), sl.getHostName(), sl.getGameSize(), sl.getUserNames());
			lobbies.add(lobby);
		}
		System.out.println("Sending lobbies");
		sendToAll(new LobbyListMessage(lobbies));
	}
	
	public static void main(String [] args) {
		new Server();
	}
}