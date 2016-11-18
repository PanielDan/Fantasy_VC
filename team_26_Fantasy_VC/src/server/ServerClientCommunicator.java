package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
}