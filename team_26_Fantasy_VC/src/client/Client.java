package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import gameplay.Company;
import gameplay.GameFrame;
import gameplay.User;
import guis.AuctionTeamList;
import guis.IntroPanel;
import guis.LobbyPanel;
import messages.ChatMessage;
import messages.ClientExitMessage;
import messages.LobbyListMessage;
import messages.LobbyPlayerReadyMessage;
import messages.Message;
import messages.ReadyGameMessage;
import messages.UserListMessage;
import utility.LobbyUserPanel;

/**
 * The {@code Client} class is a {@code Thread} that represents a 
 * player in a game.
 * @author alancoon
 *
 */
public class Client extends Thread {
	private User user;
	private Vector<User> users;
	private String lobbyName;
	private long bankAccount;
	private Vector<Company> portfolio;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Socket s;
	private GameFrame gameFrame;
	private boolean host;
	//Running boolean used to determine when to break the run while loop.
	private boolean running;
	
	public Client(User user) { 
		running = true;
		this.s = null;
		this.user = user;
		try {
			s = new Socket("jeffreychen.space", 8008);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		} catch (IOException ioe) { 
			ioe.printStackTrace();
		}
		gameFrame = new GameFrame(this);
		gameFrame.setVisible(true);
		System.out.println(gameFrame.getCurrentPanel().getClass());
	}
	
	public Vector<User> getUsers() {
		return users;
	}
	
	public void run() {
		Object m = null;
		try{
			while(true){
				m = ois.readObject();
				if (m instanceof LobbyListMessage) {
					if(gameFrame.getCurrentPanel() instanceof IntroPanel) {
						LobbyListMessage llm = (LobbyListMessage)m;
						((IntroPanel)gameFrame.getCurrentPanel()).setLobbies(llm.lobbies);
					}
				}
				else if(m instanceof UserListMessage) {
					UserListMessage ulm = (UserListMessage)m;
					this.users = ulm.user;
					if (gameFrame.getCurrentPanel() instanceof LobbyPanel) {
						((LobbyPanel)gameFrame.getCurrentPanel()).setUsers(ulm.user);
						((LobbyPanel)gameFrame.getCurrentPanel()).setWaitingText(ulm.waitingOn);
					}
					else if (gameFrame.getCurrentPanel() instanceof IntroPanel) {
						((IntroPanel)gameFrame.getCurrentPanel()).switchToLobby(ulm.waitingOn, ulm.user);
					}
				}
				else if(m instanceof ChatMessage) {
					ChatMessage cm = (ChatMessage)m;
					gameFrame.getChatPanel().addChat(cm.getUsername(), cm.getMessage());
				}
				else if (m instanceof LobbyPlayerReadyMessage) {
					LobbyPlayerReadyMessage lprm = (LobbyPlayerReadyMessage)m;
					Vector<LobbyUserPanel> lup = ((LobbyPanel)gameFrame.getCurrentPanel()).getLobbyPanels();
					for (LobbyUserPanel user : lup){
						if (user.getUsername().equals(lprm.getUsername())){
							System.out.println("set user");
							user.setFirmName(lprm.getTeamName());
							user.setReady();
						}
					}
					for (User user : users) {
						if(user.getUsername().equals(lprm.getUsername())) {
							user.setCompanyName(lprm.getTeamName());
						}
						user.createIcon();
					}
				}
				else if (m instanceof ReadyGameMessage) {
					gameFrame.setGame(users);
					gameFrame.changePanel(new AuctionTeamList(this, gameFrame));
					
				}
				else if (m instanceof ClientExitMessage) {
					System.out.println("exit");
					ClientExitMessage cem = (ClientExitMessage)m;
					System.out.println(cem.getUsername());
					System.out.println(user.getUsername());
					if(user.getUsername().equals(cem.getUsername())) {
						System.exit(0);
					}
					if(gameFrame.getCurrentPanel() instanceof LobbyPanel) {
						((LobbyPanel)gameFrame.getCurrentPanel()).removeUser(cem.getUsername());
					}
				}
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
