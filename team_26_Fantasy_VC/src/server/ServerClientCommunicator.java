package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import messages.BeginAuctionBidMessage;
import messages.ClientExitMessage;
import messages.CreateGameMessage;
import messages.JoinGameMessage;
import messages.LobbyPlayerReadyMessage;
import messages.Message;
import messages.StartTimerMessage;
import messages.UserUpdate;

public class ServerClientCommunicator extends Thread {
	private Socket socket;
	private Server server;
	private ServerLobby serverLobby;
	private Lock lock;
	private Condition condition;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	
	public ServerClientCommunicator(Socket socket, Server server) throws IOException {
		this.socket = socket;
		this.server = server;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
		this.lock = new ReentrantLock();
		condition = lock.newCondition();
	}
	
	public synchronized void sendMessage(Object msg) {
		try {
			oos.writeObject(msg);
			oos.flush();
			oos.reset();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void setLobby(ServerLobby sl) {
		lock.lock();
		server = null;
		serverLobby = sl;
		lock.unlock();
	}
	
	public void run() {
		try {
			while(server != null) {
				Object obj = ois.readObject();
				
				if (obj != null) {
					Message msg = (Message) obj;

					if (msg instanceof CreateGameMessage) {
						CreateGameMessage cgm = (CreateGameMessage)msg;
						server.createLobby(this, cgm.gamename, cgm.hostUser, cgm.numUsers, cgm.game);
					}
					else if (msg instanceof JoinGameMessage) {
						JoinGameMessage jgm = (JoinGameMessage)msg;
						server.addToLobby(this, jgm.lobbyName, jgm.user);
					}
					else if (msg instanceof ClientExitMessage) {
						server.sendToAll(msg);
						server.removeServerClientCommunicator(this);
					}
					else {
						if (server == null) {
							serverLobby.sendToAll(msg);
						} else {
							server.sendToAll(msg);
						}
					}
				}
			}
			System.out.println("Beginning main game" );
			while (true) {
				Object obj = ois.readObject();
				System.out.println("SCC read a message");
				if(obj != null) {
					System.out.println("It isn't null");
					if (obj instanceof LobbyPlayerReadyMessage) {
						LobbyPlayerReadyMessage lprm = (LobbyPlayerReadyMessage)obj;
						System.out.println("lprm");
						serverLobby.setReady(lprm.getUsername(), lprm.getTeamName());
						serverLobby.sendToAll(lprm);
					}
					else if (obj instanceof ClientExitMessage) {
						serverLobby.sendToAll(obj);
						ClientExitMessage cle = (ClientExitMessage)obj;
						if (serverLobby.sccVector.size() == 1) {
							serverLobby.removeServerClientCommunicator(this);
						}
						else {
							serverLobby.removeUser(cle.getUsername());
							serverLobby.removeServerClientCommunicator(this);
						}
					} 
					else if (obj instanceof BeginAuctionBidMessage) {
						serverLobby.startTimer(10);
						serverLobby.sendToAll(obj);
					}
					else if (obj instanceof StartTimerMessage) {
						serverLobby.startTimer(45);
					}
					else if (obj instanceof UserUpdate) {
						System.out.println("update user");
						UserUpdate ucl = (UserUpdate)obj;
						serverLobby.setUserCompanies(ucl.getUser());
					}
					else {
						System.out.println("command this is scc");
						serverLobby.sendToAll(obj);
					}
				}
			}
		} catch (IOException ioe) {
			System.out.println("disconnect");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} 
	}
}