package server;

import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import gameplay.Game;
import gameplay.User;
import messages.ReadyGameMessage;
import messages.SwitchPanelMessage;
import messages.UserListMessage;
import threads.Timer;

public class ServerLobby extends Thread{
	protected Vector<ServerClientCommunicator> sccVector;
	private Vector<User> users;
	private Server server;
	private String lobbyName, hostName;
	public Lock lock;
	public Condition condition;
	public Semaphore semaphore;
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
		this.semaphore = new Semaphore(this.numPlayers);
		timer = null;
		users = new Vector<User>();
		users.add(host);
		this.start();
		sendToAll(new UserListMessage(users, numPlayers - users.size()));
	}
	
	public void removeServerClientCommunicator(ServerClientCommunicator scc) {
		sccVector.remove(scc);
		System.out.println("remove " + users.size());
		sendToAll(new UserListMessage(users, numPlayers - users.size()));
		if (sccVector.isEmpty()) {
			System.out.println("remvove");
			server.removeServerLobby(this);
		}
	}
	
	public void removeUser(String username) {
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				users.remove(u);
			}
		}
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
	
	public void setUserCompanies(User user) {
		seedGame.updateUser(user);
		for(User u : users) {
			if(u.getUsername().equals(user.getUsername())) {
				u.setReady();
				System.out.println("Unready " + u.getUsername());
			}
		}
		System.out.println("set user lock");
//		lock.lock();
//		condition.signal();
//		lock.unlock();
		semaphore.release();
	}
	
	public synchronized void sendToAll(Object msg) {
		System.out.println("This is SL, sending to all!");
		for (ServerClientCommunicator scc : sccVector) {
			scc.sendMessage(msg);
			System.out.println(msg.getClass() + " " +  System.currentTimeMillis());
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
	
	public boolean checkReady(){
//		if (cond) {
//			System.out.println("Locked: " + System.currentTimeMillis());
//			lock.lock();
//			condition.await();
//			lock.unlock();
//		}
		for (User user : users) {
			if (!user.getReady()) {
				return false;
			}
		}
		return true;
	}
	
	public void resetReady() {
		for(User user : users) {
			user.unReady();
		}
	}
	
	public void setReady(String username, String teamname) {
		for (User user : users) {
			if(user.getUsername().equals(username)) {
				user.setReady();
				user.setCompanyName(teamname);
			}
		}
		System.out.println("set ready lock");
//		lock.lock();
//		condition.signalAll();
//		lock.unlock();
		semaphore.release();
	}
	
	private synchronized void initializeGame() { 
		// TODO arschroc and alancoon implement logic from Company class
		// to disseminate uniform data about Companies
//		seedGame.seed(users, this);
		seedGame = new Game(users, this);
	}
	
	public void run() {
		while (sccVector.size() < numPlayers);
		// TODO: Send signal that the lobby has enough players and start game
		server.removeServerLobby(this);
		System.out.println("full");
		
		try {
			semaphore.acquire(this.numPlayers);
			while(!checkReady());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("semaphore passed");
		initializeGame();
		this.sendToAll(seedGame);
		this.sendToAll(new ReadyGameMessage());
		
//		while(true) {
		resetReady();
		try {
			semaphore.acquire(this.numPlayers);
			while(!checkReady());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Send timelapse");
		sendToAll(new SwitchPanelMessage());
			
		seedGame.updateCompanies(1);
			// TODO:Send message to move to the timelapse GUI
//		}
	}
	
	public void startTimer(int time) {
		if(timer != null) {
			timer.kill();
		}
		timer = new Timer(this, time);
	}
	
	public void increaseTime(int seconds) { 
		timer.increaseTime(seconds);
	}
	
	public void nullifyTimer() {
		timer = null;
	}
}
