package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import gameplay.User;
import messages.ChatMessage;
import messages.Message;


public class ServerClientCommunicator extends Thread {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server server;
	private String firmName;
	private String username;
	private User user;

	public ServerClientCommunicator(Socket s, Server server, User user) {
		try {
			this.server = server;
			this.user = user;
			this.username = user.getUsername();
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("IOException in ServerClientCommunicator: " + ioe.getMessage());
			ioe.printStackTrace();
		}
	}

	public void sendMessage(Message message) {
		try {
			oos.writeObject(message);
			oos.flush();
			oos.reset();
		} catch (IOException ioe) {
			System.out.println("IOException in ServerClientCommunicator::sendMessage(): " + ioe.getMessage());
			ioe.printStackTrace();
		}
	}

	public void run() {
		while (true) { 
			try {
				Object o = ois.readObject();
				/* This is a BAD IMPLEMENTATION. TODO CHANGE THIS. */
				if (o instanceof ChatMessage) {
					server.sendMessageToAllClients((ChatMessage) o);
				}
			} catch (ClassNotFoundException cnfe) {
				System.out.println("ClassNotFoundException in ServerClientCommunicator::run(): " + cnfe.getLocalizedMessage());
				cnfe.printStackTrace();
			} catch (IOException ioe) {
				System.out.println("IOException in ServerClientCommunicator::run(): " + ioe.getLocalizedMessage());
				ioe.printStackTrace();
				return;
			}
		}
	}

	public void closeObjectStream() {
		try { 
			if (ois != null) { ois.close(); }
			if (oos != null) { oos.close(); }
		} catch (IOException ioe) {
			System.out.println("IOException in ServerClientCommunicator::closeObjectStream(): " + ioe.getLocalizedMessage());
			return;
		} 
	}

	/* Getters. */
	public String getTeamName() {
		return firmName;
	}
	
	public User getUser() { 
		return user;
	}

	/* Setters. */
	public void setTeamName(String firmName) {
		this.firmName = firmName;
	}
	
	public void setUser(User user) { 
		this.user = user;
	}
}

