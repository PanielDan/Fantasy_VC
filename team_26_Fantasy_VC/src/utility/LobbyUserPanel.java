package utility;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class LobbyUserPanel extends JPanel {
	private String username;

	private String firmName;

	private JLabel usernameLabel, firmNameLabel, ready;
	
	public LobbyUserPanel(String username){
		super();
		this.username = username;
		initializeVariables();
		createGUI();
	}
	
	public void initializeVariables(){
		usernameLabel = new JLabel(username);
		firmNameLabel = new JLabel("", SwingConstants.CENTER);
		ready = new JLabel("Not Ready");
	}
	
	public void createGUI(){
		this.setSize(new Dimension(700,60));
		this.setLayout(new BorderLayout());
		this.setBackground(AppearanceConstants.darkBlue);
		
		AppearanceSettings.setFont(AppearanceConstants.fontLobby, usernameLabel, firmNameLabel,
				ready);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, usernameLabel, firmNameLabel);
		ready.setForeground(Color.red);
		setBorder(new EmptyBorder(15,20,15,20));
		
		add(usernameLabel, BorderLayout.WEST);
		add(firmNameLabel, BorderLayout.CENTER);
		add(ready, BorderLayout.EAST);

	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
		firmNameLabel.setText(firmName);
	}
	
	public void setReady(){
		ready.setText("Ready");
		ready.setForeground(Color.GREEN);
	}
	
	public void unReady(){
		ready.setText("Not Ready");
		ready.setForeground(Color.red);
	}
	
	public String getUsername() {
		return username;
	}
}
