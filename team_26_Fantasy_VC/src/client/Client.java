package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import gameplay.Company;
import gameplay.User;
import messages.Message;

/**
 * The {@code Client} class is a {@code Thread} that represents a 
 * player in a game.
 * @author alancoon
 *
 */
public class Client extends Thread {
	private User user;
	private String firmName;
	private long bankAccount;
	private Vector<Company> portfolio;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket s;
	
	private boolean host;
	private boolean gameOver;
	
	public Client(String hostname, int port, User user, boolean host) { 
		synchronized (this) {
			this.s = null;
			this.user = user;
			this.host = host;
			try {
				s = new Socket(hostname, port);
				oos = new ObjectOutputStream(s.getOutputStream());
				ois = new ObjectInputStream(s.getInputStream());
				this.start();
			} catch (IOException ioe) { 
				ioe.printStackTrace();
			}
		}
	}
	
	public void run() {
		
	}
	
	public String getFirmName() { 
		return firmName;
	}
	
	public User getUser() {
		return user;
	}

	public void sendMessage(Message message) {
		try { 
			oos.writeObject(message);
			oos.flush();
			oos.reset();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}


}
