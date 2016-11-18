package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.Vector;

import exceptions.PortBoundException;
import gameplay.User;
import messages.ClientExitMessage;
import messages.JoinGameMessage;
import messages.Message;

/**
 * The {@code Server} is a {@code Thread} that
 * handles connects and handles messages from 
 * {@code Client} instantiations that wish to connect to it.
 * 
 * @author alancoon
 *
 */
public class Server extends Thread {
	
	private Vector<ServerClientCommunicator> serverClientCommunicators;
	private Vector<User> users;
	private ServerSocket ss;
	
	
	private int numberOfPlayers;
	private int playersJoined;
	
	public Server(int port, int numberOfPlayers) throws PortBoundException {
		this.ss = null;
		this.numberOfPlayers = numberOfPlayers;
		this.playersJoined = 0;
		this.serverClientCommunicators = new Vector<ServerClientCommunicator>();
		this.users = new Vector<User>();
		
		try {
			ss = new ServerSocket(port);
			this.start();
		} catch (BindException be) {
			throw new PortBoundException();
		} catch (IllegalArgumentException iae) {
//			hostGUI.setLobbyStatus("Invalid port.");
//			hostGUI.reenableAfterDisconnection();
			this.kill();
		} catch (IOException ioe) {
			System.out.println("IOException in Server::Server(): " + ioe.getMessage());
			ioe.printStackTrace();
			try { 
				if (ss != null) { ss.close(); }
			} catch (IOException iioe) { 
				System.out.println("IOException closing ServerSocket in Server(): " + iioe.getLocalizedMessage());
				iioe.printStackTrace();
			}
		} 
	}

	public void run() {
		try {
			// ATTN:  TAKE CARE OF STATE PROGRESSION WITHIN METHODS
			
			/* Wait until all of the teams have connected. */
			boolean allConnected = false;
			while (!allConnected) {
				try {
					// Look for a connecting Client
					Thread.yield();
					Socket s = ss.accept();
					System.out.println("Connection from " + s.getInetAddress());
					ServerClientCommunicator scc = new ServerClientCommunicator(s, this);
					serverClientCommunicators.add(scc);
					
					// Change things on the Client side
					playersJoined++;
					JoinGameMessage message = new JoinGameMessage(users.get(playersJoined - 1).getUsername());
					sendMessageToAllClients(message);
					
					if (playersJoined == numberOfPlayers) { allConnected = true; }
				} catch (SocketException se) {
					System.out.println("SocketException: " + se.getLocalizedMessage());
					se.printStackTrace();
				}
			}
		} catch (IOException ioe) {
			System.out.println("IOException in Server::run(): " + ioe.getMessage());
			ioe.printStackTrace();
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					System.out.println("IOException in Server::run(), closing ServerSocket: " + ioe.getMessage());
					ioe.printStackTrace();
				}
			}
		}
	}

	public void removePlayerFromLobby(String teamName, ServerClientCommunicator thread) { 
		users.remove(teamName);
		serverClientCommunicators.remove(thread);
		// Remove from GUIs
	}
	

	public void allClientsToMainGUI() {
		
		while (users.contains(null)) {
			System.out.println("Yielding...");
			Thread.yield();
		}
		
		Random rand = new Random();
		
		/* Decide the first player to pick a team. */
		int firstTurn = Math.abs(rand.nextInt()) % numberOfPlayers;
		
		for (ServerClientCommunicator scc : serverClientCommunicators) {
//			StartMainGUIMessage message = new StartMainGUIMessage();
//			message.setTeamNames(teamNames);
//			message.setNumberOfQuestions(numberOfQuestions);
// 			message.setFirstTurn(firstTurn);
//			st.sendMessage(message);
		}
	}

	public void sendExitMessage(ClientExitMessage message) {
		String name = message.getUsername();
		for (ServerClientCommunicator st : serverClientCommunicators) {
			String threadTeamName = st.getTeamName();
			if (!threadTeamName.equals(name)) {
				st.sendMessage(message);
			}
		}
	}

	public synchronized void sendMessageToAllClients(Message message) {
		for (ServerClientCommunicator st : serverClientCommunicators) {
			st.sendMessage(message);
		}
	}
	
	public void kill() {
		try {
			if (ss != null) { ss.close(); }
		} catch (IOException ioe) {
			System.out.println("IOException in Server::kill(): " + ioe.getLocalizedMessage());
			ioe.printStackTrace();
		}
	}

}
