package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import messages.CreateGameMessage;
import messages.JoinGameMessage;
import messages.Message;

public class ServerClientCommunicator extends Thread {
	private Socket socket;
	private Server server;
	private ServerLobby serverLobby;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	
	public ServerClientCommunicator(Socket socket, Server server) throws IOException {
		this.socket = socket;
		this.server = server;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
	}
	
	public void sendMessage(Message msg) {
		try {
			oos.writeObject(msg);
			oos.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void setLobby(ServerLobby sl) {
		server = null;
		serverLobby = sl;
	}
	
	public void run() {
		try {
			while(server != null) {
				Object obj = ois.readObject();
				
				if (obj != null) {
					Message msg = (Message) obj;

					if (msg instanceof CreateGameMessage) {
						CreateGameMessage cgm = (CreateGameMessage)msg;
						server.createLobby(this, cgm.gamename, cgm.hostUser, cgm.numUsers);
					}
					else if (msg instanceof JoinGameMessage) {
						JoinGameMessage jgm = (JoinGameMessage)msg;
						server.addToLobby(this, jgm.lobbyName, jgm.username);
					}
					else {
						server.sendToAll(msg);
					}
				}
			}
			
			while(true) {
				Object obj = ois.readObject();
				
				if(obj != null) {
					serverLobby.sendToAll((Message)obj);
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
}