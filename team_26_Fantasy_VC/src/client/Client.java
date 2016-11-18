package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import gameplay.Company;
import gameplay.GameFrame;
import gameplay.User;
import guis.IntroPanel;
import messages.AuctionBidUpdateMessage;
import messages.AuctionDetailsUpdateCompanyMessage;
import messages.AuctionDetailsUpdateUserMessage;
import messages.BeginAuctionBidMessage;
import messages.BeginAuctionMessage;
import messages.ChatMessage;
import messages.LobbyListMessage;
import messages.Message;
import messages.Message.MessageType;
import messages.UserInfoPopupMessage;

/**
 * The {@code Client} class is a {@code Thread} that represents a 
 * player in a game.
 * @author alancoon
 *
 */
public class Client extends Thread {
	private User user;
	private String firmName;
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
	
	public void run() {
		Message m = null;
		try{
			while(running){
				m = (Message)ois.readObject();
				//Add all message if statements here
				
				//Chat Message
				if(m.getType() == MessageType.chatMessage){
					ChatMessage cm = (ChatMessage)m;
				}
				//User info pop up
				else if (m.getType() == MessageType.userInfo){
					UserInfoPopupMessage uipm = (UserInfoPopupMessage)m;
				}
				//Update Auction Bid
				else if (m.getType() == MessageType.AuctionBidUpdate){
					AuctionBidUpdateMessage abum = (AuctionBidUpdateMessage)m;
				}
				//Update Auction Details to display Company Information
				else if (m.getType() == MessageType.AuctionDetailsUpdateCompany){
					AuctionDetailsUpdateCompanyMessage aducm = (AuctionDetailsUpdateCompanyMessage)m;
				}				
				//Update Auction Details to display User Information
				else if (m.getType() == MessageType.AuctionDetailsUpdateUser){
					AuctionDetailsUpdateUserMessage abuum = (AuctionDetailsUpdateUserMessage)m;
				}
				//Transition to Auction List screen
				else if (m.getType() == MessageType.beginAuction){
					BeginAuctionMessage bam = (BeginAuctionMessage)m;
				}
				//Transition to Auction Bid screen
				else if (m.getType() == MessageType.beginBid){
					BeginAuctionBidMessage babm = (BeginAuctionBidMessage)m;
				}
				else if (m.getType() == MessageType.LobbyList) {
					System.err.println(gameFrame == null);
					if(gameFrame.getCurrentPanel() instanceof IntroPanel) {
						LobbyListMessage llm = (LobbyListMessage)m;
						((IntroPanel)gameFrame.getCurrentPanel()).setLobbies(llm.lobbies);
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
