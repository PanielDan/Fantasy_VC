package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import util.AppearanceConstants;
import util.AppearanceSettings;

public class LoginGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton loginButton;
	private JButton createAccount;
	private JTextField username;
	private JTextField password;
	private JLabel alertLabel;


	// New stuff:
	private Connection conn = null;
	private ResultSet rs = null;

	public LoginGUI() {
		initializeConnection();
		initializeComponents();
		createGUI();
		addListeners();

		this.setMinimumSize(new Dimension(300, 300));
	}

	private void initializeConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/JeopardyUsers?user=root&password=Fuck you MySQL.&useSSL=false");
		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException: " + cnfe.getMessage());
		}
	}
	
	private void initializeComponents(){

		loginButton = new JButton("Login");
		createAccount = new JButton("Create Account");
		username = new JTextField("username");
		password = new JTextField("password");
		alertLabel = new JLabel();
		alertLabel.setForeground(Color.red);
	}

	private void createGUI(){

		JPanel mainPanel = new JPanel();
		JPanel textFieldOnePanel = new JPanel();
		JPanel textFieldTwoPanel = new JPanel();
		JLabel welcome = new JLabel("Login or create an account to play.", JLabel.CENTER);
		JLabel jeopardyLabel = new JLabel("Jeopardy!", JLabel.CENTER);
		JPanel alertPanel = new JPanel();
		JPanel textFieldsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		JPanel welcomePanel = new JPanel(new GridLayout(2,1));

		//set mass component appearances
		AppearanceSettings.setForeground(Color.lightGray, createAccount, loginButton, password, username);
		AppearanceSettings.setSize(300, 60, password, username);

		AppearanceSettings.setSize(200, 80, loginButton, createAccount);
		AppearanceSettings.setBackground(Color.darkGray, loginButton, createAccount);

		AppearanceSettings.setOpaque(loginButton, createAccount);
		AppearanceSettings.unSetBorderOnButtons(loginButton, createAccount);

		AppearanceSettings.setTextAlignment(welcome, alertLabel, jeopardyLabel);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, password, alertLabel, username, loginButton, createAccount);

		AppearanceSettings.setBackground(AppearanceConstants.lightBlue, mainPanel, welcome, alertLabel, jeopardyLabel, alertPanel, textFieldsPanel, 
				buttonsPanel, welcomePanel, textFieldOnePanel, textFieldTwoPanel);

		//other appearance settings
		welcome.setFont(AppearanceConstants.fontMedium);
		jeopardyLabel.setFont(AppearanceConstants.fontLarge);

		loginButton.setEnabled(false);
		loginButton.setBackground(AppearanceConstants.mediumGray);
		createAccount.setBackground(AppearanceConstants.mediumGray);
		createAccount.setEnabled(false);

		//add components to containers
		welcomePanel.add(welcome);
		welcomePanel.add(jeopardyLabel);

		alertPanel.add(alertLabel);
		textFieldOnePanel.add(username);
		textFieldTwoPanel.add(password);

		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		//adds components to the panel with glue in between each
		AppearanceSettings.addGlue(buttonsPanel, BoxLayout.LINE_AXIS, true, loginButton, createAccount);

		AppearanceSettings.addGlue(mainPanel, BoxLayout.PAGE_AXIS, false, welcomePanel);
		//don't want glue in between the following two panels, so they are not passed in to addGlue
		mainPanel.add(alertPanel);
		mainPanel.add(textFieldOnePanel);
		AppearanceSettings.addGlue(mainPanel, BoxLayout.PAGE_AXIS, false, textFieldTwoPanel);
		mainPanel.add(buttonsPanel);

		add(mainPanel, BorderLayout.CENTER);
		setSize(600, 600);
	}

	//returns whether the buttons should be enabled
	private boolean canPressButtons(){
		return (!username.getText().isEmpty() && !username.getText().equals("username") && 
				!password.getText().equals("password") && !password.getText().isEmpty());
	}

	private void addListeners(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//focus listeners
		username.addFocusListener(new TextFieldFocusListener("Username", username));
		password.addFocusListener(new TextFieldFocusListener("Password", password));
		//document listeners
		username.getDocument().addDocumentListener(new MyDocumentListener());
		password.getDocument().addDocumentListener(new MyDocumentListener());
		//action listeners
		loginButton.addActionListener(new LoginActionListener());
		createAccount.addActionListener(new CreateActionListener());
	}

	//sets the buttons enabled or disabled
	private class MyDocumentListener implements DocumentListener{

		@Override
		public void insertUpdate(DocumentEvent e) {
			createAccount.setEnabled(canPressButtons());
			loginButton.setEnabled(canPressButtons());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			createAccount.setEnabled(canPressButtons());
			loginButton.setEnabled(canPressButtons());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			createAccount.setEnabled(canPressButtons());
			loginButton.setEnabled(canPressButtons());
		}
	}

	private class LoginActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String queryCheck = "SELECT * FROM JeopardyUsers.Users WHERE username = ?";
				PreparedStatement ps = conn.prepareStatement(queryCheck);
				ps.setString(1, username.getText().trim());
				rs = ps.executeQuery();
				if (rs.next()) {
					/* User name exists, now check password. */
					if (rs.getString(3).equals(password.getText())) {
						/* Valid login! */
						User currentUser = new User(rs.getString(2), rs.getString(3));
						new StartWindowGUI(currentUser).setVisible(true);
						dispose();
					} else {
						/* Invalid password! */
						alertLabel.setText("Incorrect password.");
						rs = null;
					}
				} else {
					/* User name does not exist. */
					alertLabel.setText("That username does not exist.");
					rs = null;
				}
			} catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
			} 
		}
	}
	
	private class CreateActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String queryCheck = "SELECT * FROM JeopardyUsers.Users WHERE username = ?";
				PreparedStatement ps = conn.prepareStatement(queryCheck);
				ps.setString(1, username.getText().trim());
				rs = ps.executeQuery();
				if (rs.next()) {
					/* User name already in use. */
					alertLabel.setText("Username already exists.");
					rs = null;
				} else {
					/* User name does not exist. */
					String insert = "INSERT INTO JeopardyUsers.Users (username, passcode) VALUES (?, ?);";
					PreparedStatement is = conn.prepareStatement(insert);
					is.setString(1, username.getText().trim());
					is.setString(2, password.getText());
					is.execute();
					
					User newUser = new User(username.getText().trim(), password.getText());
					new StartWindowGUI(newUser).setVisible(true);
					dispose();
				}
			} catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
			} 
		}
	}
}

