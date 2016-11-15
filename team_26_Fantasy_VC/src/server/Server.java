package server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.Vector;

import exceptions.PortBoundException;
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
	private ServerSocket ss;
	private int numberOfPlayers;

	
	public Server(int port) throws PortBoundException {
		this.ss = null;
		this.numberOfPlayers = hostGUI.getSlider().getValue();
		this.serverThreads = new Vector<ServerClientCommunicator>();
		this.teamNames = new Vector<String>();
		
		try {
			ss = new ServerSocket(port);
			this.start();
		} catch (BindException be) {
			throw new PortBoundException();
		} catch (IllegalArgumentException iae) {
			hostGUI.setLobbyStatus("Invalid port.");
			hostGUI.reenableAfterDisconnection();
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
			while (!this.allConnected) {
				try {
					System.out.println("Waiting for connection...");
					Thread.yield();
					Socket s = ss.accept();
					System.out.println("Connection from " + s.getInetAddress());
					ServerClientCommunicator scc = new ServerClientCommunicator(s, this, hostGUI.getUser());
					serverThreads.add(st);
					hostGUI.incrementPlayers();
					allClientsUpdateLobbyStatus(hostGUI.getPlayers() + "/" + numberOfPlayers + " players joined...");
					System.out.println(hostGUI.getPlayers() + "/" + numberOfPlayers + " players joined...");
					if (hostGUI.getPlayersYetToJoin() == 0) { allConnected = true; } 
				} catch (SocketException se) {
					System.out.println("SOCKET EXCEPTION");
					if (teamNames.isEmpty()) {
						return;
					} else {
						HostQuitInitMessage message = new HostQuitInitMessage(teamNames.get(0));
						this.sendHostDroppedMessage(message);
						for (int i = 0; i < 20; i++) Thread.yield();
						return;
					}
				}
			}
			
			/* Yield until all of the team name have been received! */
			while (!this.allNamesReceived) {
				try {
					Thread.sleep(10);
					if (teamNames.size() == numberOfPlayers) { 
						this.allNamesReceived = true;
					}
				} catch (InterruptedException ie) {
					System.out.println("InterruptedException in Server::run(): " + ie.getMessage());
					ie.printStackTrace();
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
		teamNames.remove(teamName);
		serverThreads.remove(thread);
		hostGUI.decrementPlayers();
		allClientsUpdateLobbyStatus(hostGUI.getPlayers() + "/" + numberOfPlayers + " players joined...");
	}
	
	public void allClientsUpdateLobbyStatus(String message) {
		for (ServerClientCommunicator thread : serverThreads) {
			thread.sendMessage(new Message(0, message));
		}
	}

	public void allClientsToMainGUI() {
		
		while (teamNames.contains(null)) {
			System.out.println("Yielding...");
			Thread.yield();
		}
		
		System.out.println("TEAM NAMES:" );
		for (String name : teamNames) {
			System.out.println(name);
		}
		
		int numberOfQuestions = hostGUI.getQuickPlay() ? 5 : 25;
		Random rand = new Random();
		int firstTurn = Math.abs(rand.nextInt()) % numberOfPlayers;
		for (ServerClientCommunicator st : serverThreads) {
			StartMainGUIMessage message = new StartMainGUIMessage();
			message.setTeamNames(teamNames);
			message.setNumberOfQuestions(numberOfQuestions);
			message.setGameFile(gameFile);
			message.setFirstTurn(firstTurn);
			st.sendMessage(message);
		}
	}

	public void sendExitMessage(ClientQuitGameMessage message) {
		String name = message.getTeamName();
		for (ServerClientCommunicator st : serverThreads) {
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
