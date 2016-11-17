package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import gameplay.Company;
import gameplay.User;
import messages.AuctionBidUpdateMessage;
import messages.AuctionDetailsUpdateCompanyMessage;
import messages.AuctionDetailsUpdateUserMessage;
import messages.BeginAuctionBidMessage;
import messages.BeginAuctionMessage;
import messages.ChatMessage;
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
