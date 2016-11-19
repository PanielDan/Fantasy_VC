package server;

import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import gameplay.Game;
import gameplay.User;
import messages.ReadyGameMessage;
import messages.UserListMessage;
import threads.Timer;

public class ServerLobby extends Thread{
	protected Vector<ServerClientCommunicator> sccVector;
	private Vector<User> users;
	private Server server;
	private String lobbyName, hostName;
	public Lock lock;
	public Condition condition;
	private int numPlayers;
	private Timer timer;
	private Game seedGame;
	
	public ServerLobby(Vector<ServerClientCommunicator> sccVector, Server server, String lobbyName, User host, int numPlayers) {
		this.sccVector = sccVector;
		this.server = server;
		this.lobbyName = lobbyName;
		this.hostName = host.getUsername();
		this.numPlayers = numPlayers;
		this.lock = new ReentrantLock();
		this.condition = lock.newCondition();
		timer = null;
		users = new Vector<User>();
		users.add(host);
		this.start();
		sendToAll(new UserListMessage(users, numPlayers - users.size()));
	}
	
	public synchronized void removeServerClientCommunicator(ServerClientCommunicator scc) {
		sccVector.remove(scc);
		System.out.println("remove " + users.size());
		sendToAll(new UserListMessage(users, numPlayers - users.size()));
		if (sccVector.isEmpty()) {
			System.out.println("remvove");
			server.removeServerLobby(this);
		}
	}
	
	public synchronized void removeUser(String username) {
		lock.lock();
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				users.remove(u);
			}
		}
		lock.unlock();
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
		System.out.println("This is SL, sending to all!");
		for (ServerClientCommunicator scc : sccVector) {
			scc.sendMessage(msg);
			System.out.println(scc.getName() + " sent!");
		}
	}
	
	public void addToLobby(ServerClientCommunicator scc, User user) {
		System.out.println("add");
		sccVector.add(scc);
		users.add(user);
		System.out.println(users.get(0).getReady());
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
	
	private synchronized void initializeGame() { 
		// TODO arschroc and alancoon implement logic from Company class
		// to disseminate uniform data about Companies
		seedGame = new Game(users);
	}
	
	public void run() {
		while (sccVector.size() < numPlayers);
		// TODO: Send signal that the lobby has enough players and start game
		server.removeServerLobby(this);
		System.out.println("full");
		
		while(!checkReady());
		lock.lock();
		try {
			condition.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock.unlock();
		initializeGame();
		this.sendToAll(seedGame);
		this.sendToAll(new ReadyGameMessage(users));
	}
	
	public void startTimer(int time) {
		if(timer != null) {
			timer.kill();
		}
		timer = new Timer(this, time);
	}
	
	public void nullifyTimer() {
		timer = null;
	}
}
