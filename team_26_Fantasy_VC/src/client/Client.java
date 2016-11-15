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
	//Running boolean used to determine when to break the run while loop.
	private boolean running;
	
	public Client(String hostname, int port, User user, boolean host) { 
		synchronized (this) {
			running = true;
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
		Message m = null;
		try{
			while(running){
				m = (Message)ois.readObject();

				//Add all message if statements here
				
			}

		} catch (IOException ioe){
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		} finally{
			try {
				if (oos != null){
					oos.close();
				}
				if (ois != null){
					ois.close();
				}
				if (s != null){
					s.close();
				}
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
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
