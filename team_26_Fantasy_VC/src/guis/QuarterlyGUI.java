package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import client.Client;
import gameplay.Company;
import gameplay.GameFrame;
import gameplay.User;
import listeners.TableModel;
import messages.QuarterlyReadyMessage;
import utility.AppearanceConstants;
import utility.AppearanceSettings;

public class QuarterlyGUI extends JPanel{
	public JPanel chatPanel;
	public JPanel notificationsAndReadyPanel;
	private JLabel timer;
	public JPanel titlePanel;
	public JTextArea updatesTextArea;
	public JButton ready;
	public JTabbedPane tabbedPane;
	public PlayerTab panel1, panel2, panel3, panel4;
	public JPanel freeAgents;
	public GameFrame gameFrame;
	private Client client;

	private Vector<User> users;
	private Vector<PlayerTab> tabs;

	private JTable freeAgentTable;
	private JButton buy;
	private HashMap<User, PlayerTab> userToTab;

	/** Used https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuarterlyGUI(GameFrame gameFrame, Client client) {
		this.gameFrame = gameFrame;
		this.client = client;
		this.gameFrame.header.updateCurrentCapital();
		initializeComponents();
		createGUI();
		addActionListeners();
	}

	private void initializeComponents() {
		setSize(1280, 504);
		this.setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		updatesTextArea = new JTextArea();
		updatesTextArea.setWrapStyleWord(true);
		notificationsAndReadyPanel = new JPanel();
		ready = new JButton("Ready");
		timer = new JLabel("Timer");

		users = gameFrame.game.getUsers();
		tabs = new Vector<PlayerTab>();
		buy = new JButton("Buy selected company.");
		userToTab = new HashMap<User, PlayerTab>();
	}

	private void createGUI() {
		JScrollPane updatesScrollPane = new JScrollPane(updatesTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		updatesTextArea.setEditable(false);
		updatesTextArea.setForeground(Color.WHITE);

		//TODO temporary icon
		ImageIcon icon = new ImageIcon("src/14705784_10210522380393027_557234648620204411_n.jpg");
		Vector<String> assets = new Vector();
		assets.addElement("Portfolio Contents:");

		for (User user : users) {
			String companyName = user.getCompanyName();
			//			ImageIcon imageIcon = new ImageIcon(user.getUserIcon());
			ImageIcon imageIcon = new ImageIcon("src/14705784_10210522380393027_557234648620204411_n.jpg");
			user.setUserIcon("src/14705784_10210522380393027_557234648620204411_n.jpg");
			Vector<Company> companies = user.getCompanies();

			PlayerTab pt = new PlayerTab(user, this);
			//			PlayerTab pt = new PlayerTab(companyName, imageIcon, companies, this);
			userToTab.put(user, pt);
			tabs.add(pt);
			tabbedPane.add(user.getCompanyName(), pt);
		}

		/*
		panel1 = new PlayerTab("Tim", icon, assets, this);
		tabbedPane.add("Player 1 Name", panel1);

		panel2 = new PlayerTab("Danny", icon, assets, this);
		tabbedPane.add("Player 2 Name", panel2);

		panel3 = new PlayerTab("Jeff", icon, assets, this);
		tabbedPane.add("Player 3 Name", panel3);

		panel4 = new PlayerTab("Alan", icon, assets, this);
		tabbedPane.add("Player 4 Name", panel4);
		 */

		freeAgents = new JPanel();
		//TODO set text label for free agents and add a table of available companies
		tabbedPane.add("Free Agents", freeAgents);



		// Create freeAgents 
		String[] columnNames = {"Name", "Tier Level", "Price", "Trend"};
		TableModel dtm = new TableModel();
		dtm.setColumnIdentifiers(columnNames);
		Vector<Company> companies = gameFrame.game.getFreeAgents();

		for(int i = 0; i < companies.size(); i++) {
			double percentChange = (companies.get(i).getCurrentWorth() - companies.get(i).getStartingPrice())/
					companies.get(i).getStartingPrice() * 100;
			DecimalFormat df = new DecimalFormat ("#.##");

			dtm.addRow(new Object[]{companies.get(i).getName(), Integer.toString(companies.get(i).getTierLevel()),
					Double.toString(companies.get(i).getCurrentWorth()), 
					df.format(percentChange) + "%"});
		}

		freeAgentTable = new JTable(dtm);
		freeAgentTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

		JScrollPane freeAgentScrollPane = new JScrollPane(freeAgentTable);
		freeAgents.setLayout(new BorderLayout());
		freeAgents.add(freeAgentScrollPane, BorderLayout.CENTER);
		JPanel buyPanel = new JPanel();
		buyPanel.add(Box.createHorizontalStrut(5));
		buyPanel.add(buy);

		freeAgents.add(buyPanel, BorderLayout.SOUTH);

		AppearanceSettings.setBackground(AppearanceConstants.offWhite, freeAgentTable);

		// Create notificationsAndReadyPanel
		notificationsAndReadyPanel.setLayout(new BorderLayout());
		notificationsAndReadyPanel.add(timer, BorderLayout.NORTH);
		notificationsAndReadyPanel.add(ready, BorderLayout.CENTER);
		notificationsAndReadyPanel.add(updatesScrollPane, BorderLayout.SOUTH);
		updatesTextArea.setFont(AppearanceConstants.fontSmallest);
		updatesTextArea.setLineWrap(true);
		sendUpdate("Notifications:");

		notificationsAndReadyPanel.setPreferredSize(new Dimension(300, 1));
		updatesScrollPane.setPreferredSize(new Dimension(1, 400));

		add(tabbedPane, BorderLayout.CENTER);
		add(notificationsAndReadyPanel, BorderLayout.EAST);

		AppearanceSettings.setFont(AppearanceConstants.fontMedium, timer);
		AppearanceSettings.setBackground(AppearanceConstants.lightBlue, updatesTextArea, notificationsAndReadyPanel);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue );

		AppearanceSettings.setBackground(AppearanceConstants.green, ready);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, ready, buy);
		AppearanceSettings.setOpaque(ready, buy);
		AppearanceSettings.unSetBorderOnButtons(ready, buy);
		AppearanceSettings.setFont(AppearanceConstants.fontLargeBidButton, ready);
		AppearanceSettings.setFont(AppearanceConstants.fontButtonMedium, buy);
		AppearanceSettings.setBackground(AppearanceConstants.mediumGray, buy);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, freeAgents, buyPanel, this);

	}

	private void addActionListeners() {
		buy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the selected company
				int selectedRow = freeAgentTable.getSelectedRow();
				
				if (selectedRow != -1) {
					TableModel dtm = (TableModel) freeAgentTable.getModel();
					Company selectedCompany = gameFrame.game.returnCompany((String) dtm.getValueAt(selectedRow, 0));
					double price = selectedCompany.getCurrentWorth();
					double currentCapital = gameFrame.user.getCurrentCapital();
					if (price > currentCapital) {
						JOptionPane.showConfirmDialog(null, "You can't afford that!", "Venture Capital", JOptionPane.WARNING_MESSAGE);
					} else {
						// Buy the company
						gameFrame.user.addCompany(selectedCompany);

						// Update our GUI to reflect current capital
						gameFrame.header.updateCurrentCapital();

						// Remove from table
						dtm.removeRow(selectedRow);

						// Make the stuff needed to insert
						double percentChange = (selectedCompany.getCurrentWorth() - selectedCompany.getStartingPrice())/selectedCompany.getStartingPrice() * 100;
						DecimalFormat df = new DecimalFormat("#,##");

						// Get the PlayerTab of the user
						PlayerTab pt = userToTab.get(gameFrame.user);
						JTable userTable = pt.getTable();
						TableModel userDtm = (TableModel) userTable.getModel();
						userDtm.addRow(new Object[]{selectedCompany.getName(), 
								Integer.toString(selectedCompany.getTierLevel()),
								Double.toString(selectedCompany.getCurrentWorth()),
								df.format(percentChange) + "%" });
						
						//update the notifications
						String update = gameFrame.user.getCompanyName() + " bought " + selectedCompany.getName();
						sendUpdate(update);
					}
				}
			}
		});
		ready.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (gameFrame.game.currentQuarter == 20) {
					gameFrame.changePanel(new FinalGUI(gameFrame, null));
				} else {
					gameFrame.changePanel(new TimelapsePanel(null, gameFrame));
				}
				QuarterlyReadyMessage qrm = new QuarterlyReadyMessage();
			}
		});
	}

	public void sendUpdate(String message) {
		updatesTextArea.append("\n" + message);
	}

	public JTable getFreeAgentTable() {
		return freeAgentTable;
	}
}