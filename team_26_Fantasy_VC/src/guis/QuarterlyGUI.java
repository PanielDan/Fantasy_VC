package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import gameplay.GameFrame;
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
	
	/** Used https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuarterlyGUI(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		initializeComponents();
		createGUI();
		addActionListeners();
	}
	
	private void initializeComponents() {
		setSize(1280, 504);
		this.setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		updatesTextArea = new JTextArea();
		notificationsAndReadyPanel = new JPanel();
		ready = new JButton("Ready");
		timer = new JLabel("Timer");
	}
	
	private void createGUI() {
		JScrollPane updatesScrollPane = new JScrollPane(updatesTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		updatesTextArea.setEditable(false);
		
		//TODO temporary icon
		ImageIcon icon = new ImageIcon("src/14705784_10210522380393027_557234648620204411_n.jpg");
		Vector<String> assets = new Vector();
		assets.addElement("Portfolio Contents:");
		panel1 = new PlayerTab("Tim", icon, assets, this);
		tabbedPane.add("Player 1 Name", panel1);
		
		panel2 = new PlayerTab("Danny", icon, assets, this);
		tabbedPane.add("Player 2 Name", panel2);
		
		panel3 = new PlayerTab("Jeff", icon, assets, this);
		tabbedPane.add("Player 3 Name", panel3);
		
		panel4 = new PlayerTab("Alan", icon, assets, this);
		tabbedPane.add("Player 4 Name", panel4);
	
		freeAgents = new JPanel();
		tabbedPane.add("Free Agents", freeAgents);
		
		//Create notificationsAndReadyPanel
		notificationsAndReadyPanel.setLayout(new BorderLayout());
		notificationsAndReadyPanel.add(timer, BorderLayout.NORTH);
		notificationsAndReadyPanel.add(ready, BorderLayout.CENTER);
		notificationsAndReadyPanel.add(updatesTextArea, BorderLayout.SOUTH);
		sendUpdate("Notifications Tray");
		
		notificationsAndReadyPanel.setPreferredSize(new Dimension(300, 1));
		updatesTextArea.setPreferredSize(new Dimension(1, 400));
		
		add(tabbedPane, BorderLayout.CENTER);
		add(notificationsAndReadyPanel, BorderLayout.EAST);

		AppearanceSettings.setFont(AppearanceConstants.fontMedium, timer);
		
		ready.setBackground(Color.GREEN);	
	}
	
	private void addActionListeners() {
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				QuarterlyReadyMessage qrm = new QuarterlyReadyMessage();
			}
		});
	}
	
	public void sendUpdate(String message) {
		updatesTextArea.append("\n" + message);
	}
}