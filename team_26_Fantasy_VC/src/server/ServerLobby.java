package server;

import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import gameplay.Game;
import gameplay.User;
import messages.ReadyGameMessage;
import messages.SwitchToTimelapseMessage;
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
	
	public ServerLobby(Vector<ServerClientCommunicator> sccVector, Server server, String lobbyName, User host, int numPlayers, Game game) {
		this.sccVector = sccVector;
		this.server = server;
		this.lobbyName = lobbyName;
		this.hostName = host.getUsername();
		this.numPlayers = numPlayers;
		this.lock = new ReentrantLock();
		this.condition = lock.newCondition();
		this.seedGame = game;
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
	
	public synchronized void setUserCompanies(User user) {
		lock.lock();
		seedGame.updateUser(user);
		for(User u : users) {
			if(u.getUsername().equals(user.getUsername())) {
				u.setReady();
				System.out.println("Unready " + u.getUsername());
			}
		}
		try {
			condition.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock.unlock();
	}
	
	public void sendToAll(Object msg) {
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
	
	public boolean checkReady(boolean cond) throws InterruptedException {
		System.out.println("enter");
		lock.lock();
		if (cond) condition.await();
		for (User user : users) {
			if (!user.getReady()) {
				System.out.println(user.getUsername());
				lock.unlock();
				return false;
			}
		}
		System.out.println("leave");
		lock.unlock();
		return true;
	}
	
	public void resetReady() {
		lock.lock();
		for(User user : users) {
			user.unReady();
		}
		lock.unlock();
	}
	
	public void setReady(String username, String teamname) {
		lock.lock();
		for (User user : users) {
			if(user.getUsername().equals(username)) {
				user.setReady();
				user.setCompanyName(teamname);
			}
		}
		condition.signalAll();
		lock.unlock();
	}
	
	private synchronized void initializeGame() { 
		// TODO arschroc and alancoon implement logic from Company class
		// to disseminate uniform data about Companies
		seedGame.seed(users, this);
	}
	
	public void run() {
		while (sccVector.size() < numPlayers);
		// TODO: Send signal that the lobby has enough players and start game
		server.removeServerLobby(this);
		System.out.println("full");
		
		try {
			while(!checkReady(true));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("stuck 1");
		
		initializeGame();
		System.out.println("stuck 2");
		this.sendToAll(seedGame);
		System.out.println("stuck 3");
		resetReady();
		this.sendToAll(new ReadyGameMessage());
		System.out.println("stuck 4");
		
//		while(true) {
		try {
			while(!checkReady(false));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Send timelapse");
		sendToAll(new SwitchToTimelapseMessage());
			
		seedGame.updateCompanies();
			// TODO:Send message to move to the timelapse GUI
//		}
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
