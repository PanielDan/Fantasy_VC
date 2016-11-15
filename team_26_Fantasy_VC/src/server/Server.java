package server;

/**
 * The {@code Server} is a {@code Thread} that
 * handles connects and handles messages from 
 * {@code Client} instantiations that wish to connect to it.
 * 
 * @author alancoon
 *
 */
public class Server extends Thread {
	private StartWindowGUI hostGUI;
	private MainGUI mGUI;
	private Vector<JeopardyServerThread> serverThreads;
	private ServerSocket ss;
	private int numberOfPlayers;
	private GameFile gameFile;
	private Vector<String> teamNames;
	
	private QuestionState currentQuestionState;
	private int numberOfBets;
	private int numberOfAnswers;


	private boolean allConnected;

	
	public JeopardyServer(StartWindowGUI hostGUI, int port, GameFile gameFile) throws PortBoundException {
		this.ss = null;
		this.hostGUI = hostGUI;
		this.numberOfPlayers = hostGUI.getSlider().getValue();
		this.serverThreads = new Vector<JeopardyServerThread>();
		this.gameFile = gameFile;
		this.teamNames = new Vector<String>();
		this.allConnected = false;
		this.allNamesReceived = false;
		this.allBetsReceived = false;
		this.allAnswersReceived = false;
		
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
			System.out.println("IOException in JeopardyServer::JeopardyServer(): " + ioe.getMessage());
			ioe.printStackTrace();
			try { 
				if (ss != null) { ss.close(); }
			} catch (IOException iioe) { 
				System.out.println("IOException closing ServerSocket in JeopardyServer(): " + iioe.getLocalizedMessage());
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
					JeopardyServerThread st = new JeopardyServerThread(s, this, hostGUI.getUser());
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
	//					this.kill();
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
					System.out.println("InterruptedException in JeopardyServer::run(): " + ie.getMessage());
					ie.printStackTrace();
				}
			}
			
			
			/* Inform the BuzzerActionListener of our team names. */
			BuzzerActionListener.setTeamNames(teamNames);
			
			/* Send all clients a copy of the official team list! */
			distributeTeamList();

			/* Send all clients to the MainGUI. */
			allClientsToMainGUI();
			
//			while (!this.allBetsReceived) { 
//				Thread.yield();
//				if (numberOfBets == numberOfPlayers) { this.allBetsReceived = true; }
//			}
			
			/* Reveal answer to players. */
//			RevealFinalQuestionMessage message = new RevealFinalQuestionMessage();
//			sendMessageToAllClients(message);
			
			
//			while (!this.allAnswersReceived) { 
//				Thread.yield();
//				if (numberOfAnswers == numberOfPlayers) { allAnswersReceived = true; }
//			}
		} catch (IOException ioe) {
			System.out.println("IOException in JeopardyServer::run(): " + ioe.getMessage());
			ioe.printStackTrace();
		} finally {
			if (ss != null) {
				try {
					ss.close();
				} catch (IOException ioe) {
					System.out.println("IOException in JeopardyServer::run(), closing ServerSocket: " + ioe.getMessage());
					ioe.printStackTrace();
				}
			}
		}
	}

	public void removePlayerFromLobby(String teamName, JeopardyServerThread thread) { 
		teamNames.remove(teamName);
		serverThreads.remove(thread);
		hostGUI.decrementPlayers();
		allClientsUpdateLobbyStatus(hostGUI.getPlayers() + "/" + numberOfPlayers + " players joined...");
	}
	
	public void allClientsUpdateLobbyStatus(String message) {
		for (JeopardyServerThread thread : serverThreads) {
			thread.sendMessage(new JeopardyMessage(0, message));
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
		for (JeopardyServerThread st : serverThreads) {
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
		for (JeopardyServerThread st : serverThreads) {
			String threadTeamName = st.getTeamName();
			if (!threadTeamName.equals(name)) {
				st.sendMessage(message);
			}
		}
	}
	
//	public void sendExitMessage(HostQuitInitMessage message) {
//		String name = message.getTeamName();
//		for (JeopardyServerThread st: serverThreads) { 
//			String threadTeamName = st.getTeamName();
//			System.out.println(name + " ? " + threadTeamName);
//			if (!threadTeamName.equals(name)) {
//				st.sendMessage(message);
//			}
// 		}
//	}
	
	public void sendHostDroppedMessage(HostQuitInitMessage message) { 
		for (JeopardyServerThread st : serverThreads) { 
			System.out.println(st.getTeamName() + " !!! ");
			st.sendMessage(message);
		}
	}
	
	public synchronized void sendMessageToAllClients(JeopardyMessage message) {
		for (JeopardyServerThread st : serverThreads) {
			st.sendMessage(message);
		}
	}

	public void sendTeamName(String message) {
		teamNames.add(message);
	}

	public void distributeTeamList() {
		DistributeTeamListMessage message = new DistributeTeamListMessage("Distributing team list.", teamNames);
		for (JeopardyServerThread st : serverThreads) {
			st.sendMessage(message);
		}
	}

	public void resetServer() {
		this.numberOfBets = 0;
		this.numberOfAnswers = 0;
		this.currentQuestionState = null;
		this.allAnswersReceived = false;
		this.allBetsReceived = false;
	}
	
	public void kill() {
		try {
			if (ss != null) { ss.close(); }
		} catch (IOException ioe) {
			System.out.println("IOException in JeopardyServer::kill(): " + ioe.getLocalizedMessage());
			ioe.printStackTrace();
		}
	}

	public void incrementNumberOfBets() {
		this.numberOfBets++;
	}
	
	public void setCurrentQuestionState(QuestionState currentQuestionState) {
		this.currentQuestionState = currentQuestionState;
	}
}
