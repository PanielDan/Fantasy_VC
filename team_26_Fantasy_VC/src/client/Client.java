package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import gameplay.Company;
import gameplay.Game;
import gameplay.GameFrame;
import gameplay.User;
import guis.AuctionBidScreen;
import guis.AuctionTeamList;
import guis.IntroPanel;
import guis.LobbyPanel;
import guis.TimelapsePanel;
import messages.AuctionBidUpdateMessage;
import messages.BeginAuctionBidMessage;
import messages.ChatMessage;
import messages.ClientExitMessage;
import messages.CompanyUpdateMessage;
import messages.LobbyListMessage;
import messages.LobbyPlayerReadyMessage;
import messages.ReadyGameMessage;
import messages.StartTimerMessage;
import messages.SwitchToTimelapseMessage;
import messages.TimerTickMessage;
import messages.UserListMessage;
import messages.UserUpdate;
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
	private AuctionTeamList atl;
	
	public Client(User user) { 
		running = true;
		this.s = null;
		this.user = user;
		try {
			s = new Socket("localhost", 8008);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
		} catch (IOException ioe) { 
			ioe.printStackTrace();
		}
		gameFrame = new GameFrame(this, user);
		gameFrame.setVisible(true);
		System.out.println(gameFrame.getCurrentPanel().getClass());
	}
	
	public Vector<User> getUsers() {
		return users;
	}
	
	public void run() {
		try{
			while(true) {
				Object m = ois.readObject();
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
					System.out.println("lprm");
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
					System.out.println("Ready : " + System.currentTimeMillis());
					atl = new AuctionTeamList(this, gameFrame); 
					gameFrame.changePanel(atl);
					if(user.getUsername().equals(users.get(0).getUsername())) sendMessage(new StartTimerMessage());
				}
				else if (m instanceof Game) {
					gameFrame.setGame((Game)m);
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
				else if (m instanceof BeginAuctionBidMessage) { 
					System.out.println("bidding now");
					BeginAuctionBidMessage babm = (BeginAuctionBidMessage) m;
					Company company = babm.getCompany();
					company.createIcon();
					AuctionBidScreen abs = new AuctionBidScreen(gameFrame, company);
					abs.updateBet(babm.getCompanyName(), company.getStartingPrice());
					gameFrame.changePanel(abs);
					atl.removeRow(babm.getSelected());
				}
				else if (m instanceof AuctionBidUpdateMessage) {
					AuctionBidUpdateMessage abum = (AuctionBidUpdateMessage) m;
					((AuctionBidScreen)gameFrame.getCurrentPanel()).updateBet(abum.getCompanyName(), abum.getBidAmount());
				}
				else if (m instanceof TimerTickMessage) { 
					TimerTickMessage ttm = (TimerTickMessage) m;
					if(gameFrame.getCurrentPanel() instanceof LobbyPanel) {
					}
					if (gameFrame.getCurrentPanel() instanceof AuctionBidScreen) {
						AuctionBidScreen auctionBidScreen = (AuctionBidScreen) gameFrame.getCurrentPanel();
						auctionBidScreen.updateTimer(ttm.getDisplay());
						if(ttm.getDisplay().equals("00:00")) {
							auctionBidScreen.company.setCurrentWorth(auctionBidScreen.bidMin);
							for(User u : users) {
								if(u.getCompanyName().equals(auctionBidScreen.currentBidder)) {
									u.addCompany(auctionBidScreen.company);
									if (u.getCompanyName().equals(user.getCompanyName())) {
										user.addCompany(auctionBidScreen.company);
										gameFrame.header.updateCurrentCapital();
									}
								}
							}
							atl.nextPlayer();
							atl.setDraftOrder();
							atl.updateCapital();
							if(atl.getCurrent() == null) {
								// TODO: send message to the server lobby that everybody is done
								System.out.println(user.getCompanies());
								System.out.println(user.getCurrentCapital());
								sendMessage(new UserUpdate(user));
							}
							else{
								gameFrame.changePanel(atl);
								if(user.getUsername().equals(users.get(0).getUsername())) sendMessage(new StartTimerMessage());
							}
						}
					}
					else if (gameFrame.getCurrentPanel() instanceof AuctionTeamList) {
						atl.updateTimer(ttm.getDisplay());
						if(ttm.getDisplay().equals("00:00")){
							if (user.getUsername().equals(atl.getCurrent())) {
								atl.networkBidButtonAction();
							}
						}
					}
				}
				else if (m instanceof SwitchToTimelapseMessage) {
					gameFrame.changePanel(new TimelapsePanel(this, gameFrame));
				}
				else if (m instanceof CompanyUpdateMessage) {
					CompanyUpdateMessage cum = (CompanyUpdateMessage)m; // hehe, cum...
					((TimelapsePanel)gameFrame.getCurrentPanel()).appendNotification(cum.getMessage());
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

	public void sendMessage(Object message) {
		try { 
			System.out.println("Client sending message");
			oos.writeObject(message);
			System.out.println("flushing");
			oos.flush();
//			System.out.println("reset");
			oos.reset();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}


}
