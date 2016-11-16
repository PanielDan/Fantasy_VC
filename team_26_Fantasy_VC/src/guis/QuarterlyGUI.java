package guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import utility.AppearanceConstants;
import utility.AppearanceSettings;
 
public class QuarterlyGUI extends JFrame{
	public JPanel chatPanel;
	public JPanel notificationsAndReadyPanel;
	private JLabel timer;
	public JPanel titlePanel;
	public JTextArea updatesTextArea;
	public JButton ready;
	public JTabbedPane tabbedPane;
	public PlayerTab panel1, panel2, panel3, panel4;
	public JPanel freeAgents;
	
	/** Used https://docs.oracle.com/javase/tutorial/uiswing/components/tabbedpane.html
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuarterlyGUI() {
		super("Fantasy VC");
		initializeComponents();
		createGUI();
		addActionListeners();
	}
	
	public static void main(String[] args) {
		new QuarterlyGUI().setVisible(true);
	}
	
	private void initializeComponents() {
		setSize(1280, 720);
		this.setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();
		updatesTextArea = new JTextArea();
		chatPanel = new JPanel();
		titlePanel = new JPanel();
		notificationsAndReadyPanel = new JPanel();
		ready = new JButton("Ready");
		timer = new JLabel("00:10", SwingConstants.CENTER);
	}
	
	private void createGUI() {
		JScrollPane updatesScrollPane = new JScrollPane(updatesTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//TODO temporary icon
		ImageIcon icon = new ImageIcon("src/14705784_10210522380393027_557234648620204411_n.jpg");
		Vector<String> assets = new Vector();
		assets.addElement("Portfolio Contents:");
		panel1 = new PlayerTab("Tim", icon, assets);
		tabbedPane.add("Player 1 Name", panel1);
		
		panel2 = new PlayerTab("Danny", icon, assets);
		tabbedPane.add("Player 2 Name", panel2);
		
		panel3 = new PlayerTab("Jeff", icon, assets);
		tabbedPane.add("Player 3 Name", panel3);
		
		panel4 = new PlayerTab("Alan", icon, assets);
		tabbedPane.add("Player 4 Name", panel4);
	
		freeAgents = new JPanel();
		tabbedPane.add("Free Agents", freeAgents);
		
	
		
		// Create chat Panel 
		JLabel chat = new JLabel("CHAT PANEL");
		chatPanel.add(chat);
		
		//Create notificationsAndReadyPanel
		notificationsAndReadyPanel.setLayout(new BorderLayout());
		notificationsAndReadyPanel.add(timer, BorderLayout.NORTH);
		notificationsAndReadyPanel.add(ready, BorderLayout.CENTER);
		notificationsAndReadyPanel.add(updatesTextArea, BorderLayout.SOUTH);
		sendUpdate("Notifications Tray");
		
		
		
		//ADD SOMETHING HERE.
		
		
		
		JLabel titleLabel = new JLabel("Fantasy VC");
		titlePanel.add(titleLabel);
		
		titlePanel.setPreferredSize(new Dimension(1, 72));
		chatPanel.setPreferredSize(new Dimension(1, 144));
		notificationsAndReadyPanel.setPreferredSize(new Dimension(300, 1));
		updatesTextArea.setPreferredSize(new Dimension(1, 400));
		
		add(titlePanel, BorderLayout.NORTH);
		add(chatPanel, BorderLayout.SOUTH);
		add(tabbedPane, BorderLayout.CENTER);
		add(notificationsAndReadyPanel, BorderLayout.EAST);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, chatPanel, titlePanel);
		AppearanceSettings.setFont(AppearanceConstants.fontLarge, titleLabel);
		AppearanceSettings.setFont(AppearanceConstants.fontMedium, timer);
		chat.setForeground(Color.WHITE);
		titleLabel.setForeground(Color.WHITE);
		ready.setBackground(Color.GREEN);	
	}
	
	private void addActionListeners() {
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
			}
		});
	}
	
	public void sendUpdate(String message) {
		updatesTextArea.append("\n" + message);
	}
}