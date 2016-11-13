package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import game_objects.User;
import listeners.TextFieldFocusListener;
import util.AppearanceConstants;
import util.AppearanceSettings;

/**
 * The {@code LoginGUI} is the first graphical user interface 
 * that the player sees when starting the game. From here,
 * he or she can login using an existing account, create a new
 * account, or continue as a guest. We utilize MySQL databases
 * to store the user data.
 * 
 * @author alancoon
 *
 */
public class LoginGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton loginButton;
	private JButton createAccount;
	private JButton guestButton;
	private JTextField username;
	private JTextField password;
	private JLabel alertLabel;

	private final static String queryStatement = "SELECT * FROM Venture.Users WHERE username = ?";
	private final static String insertStatement = "INSERT INTO Venture.Users (username, passcode) VALUES (?, ?);";

	private Connection conn = null;
	private ResultSet rs = null;

	public LoginGUI() {
		super("Fantasy Venture Capital");
		initializeConnection();
		initializeComponents();
		createGUI();
		addListeners();
	}

	private void initializeConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Venture?user=root&password=Fuck you MySQL.&useSSL=false");
		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException: " + cnfe.getMessage());
		}
	}
	
	private void initializeComponents() {

		loginButton = new JButton("Login");
		createAccount = new JButton("Create Account");
		guestButton = new JButton("Continue as Guest");
		username = new JTextField("Username");
		password = new JTextField("Password");
		alertLabel = new JLabel();
		alertLabel.setForeground(Color.red);
	}

	private void createGUI() {

		JPanel mainPanel = new JPanel();
		JPanel textFieldOnePanel = new JPanel();
		JPanel textFieldTwoPanel = new JPanel();
		JLabel welcome = new JLabel("Login or create an account to play.", JLabel.CENTER);
		JLabel ventureLabel = new JLabel("Venture Capital", JLabel.CENTER);
		JPanel alertPanel = new JPanel();
		JPanel textFieldsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel(new BoxLayout(this, BoxLayout.LINE_AXIS));
		JPanel welcomePanel = new JPanel(new GridLayout(2, 1));

		// Set mass component appearances
		// TODO replace with generalized variables from AppearanceConstants
		Color buttonForeground = Color.lightGray;
		Color buttonBackground = Color.darkGray; 
		Color primaryBackground = AppearanceConstants.lightBlue;

		AppearanceSettings.setForeground(buttonForeground, createAccount, loginButton, guestButton, password, username);
		AppearanceSettings.setSize(300, 60, password, username);

		AppearanceSettings.setSize(200, 80, loginButton, createAccount);
		AppearanceSettings.setSize(200, 80, guestButton);
		AppearanceSettings.setBackground(buttonBackground, loginButton, createAccount, guestButton);

		AppearanceSettings.setOpaque(loginButton, createAccount, guestButton);
		AppearanceSettings.unSetBorderOnButtons(loginButton, createAccount, guestButton);

		AppearanceSettings.setTextAlignment(welcome, alertLabel, ventureLabel);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, password, alertLabel, username, loginButton, createAccount, guestButton);

		AppearanceSettings.setBackground(primaryBackground, mainPanel, welcome, alertLabel, ventureLabel, alertPanel, textFieldsPanel, 
				buttonsPanel, welcomePanel, textFieldOnePanel, textFieldTwoPanel);

		// Other appearance settings
		welcome.setFont(AppearanceConstants.fontMedium);
		ventureLabel.setFont(AppearanceConstants.fontLarge);

		loginButton.setEnabled(false);
		loginButton.setBackground(AppearanceConstants.mediumGray);
		createAccount.setBackground(AppearanceConstants.mediumGray);
		createAccount.setEnabled(false);

		// Add components to containers
		welcomePanel.add(welcome);
		welcomePanel.add(ventureLabel);

		alertPanel.add(alertLabel);
		textFieldOnePanel.add(username);
		textFieldTwoPanel.add(password);

		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		// Adds the three buttons 
		buttonsPanel.add(new JPanel().add(loginButton));
		buttonsPanel.add(Box.createHorizontalStrut(10));
		buttonsPanel.add(new JPanel().add(createAccount));
		buttonsPanel.add(Box.createHorizontalStrut(10));
		buttonsPanel.add(new JPanel().add(guestButton));
		
		AppearanceSettings.addGlue(mainPanel, BoxLayout.PAGE_AXIS, false, welcomePanel);
		// Don't want glue in between the following two panels, so they are not passed in to addGlue
		mainPanel.add(alertPanel);
		mainPanel.add(textFieldOnePanel);
		AppearanceSettings.addGlue(mainPanel, BoxLayout.PAGE_AXIS, false, textFieldTwoPanel);
		mainPanel.add(buttonsPanel);
		

		add(mainPanel, BorderLayout.CENTER);
		setSize(600, 600);
		setMinimumSize(new Dimension(490, 300));

	}

	/**
	 * Returns whether the buttons should be enabled.
	 * @return Whether the buttons should be enabled or not.
	 */
	private boolean canPressButtons() {
		return (!username.getText().isEmpty() && !username.getText().equalsIgnoreCase("Username") && 
				!password.getText().equalsIgnoreCase("Password") && !password.getText().isEmpty());
	}

	/**
	 * Adds action listeners to the GUI components.
	 */
	private void addListeners() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Focus listeners
		username.addFocusListener(new TextFieldFocusListener("Username", username));
		password.addFocusListener(new TextFieldFocusListener("Password", password));
		// Document listeners
		username.getDocument().addDocumentListener(new LoginDocumentListener());
		password.getDocument().addDocumentListener(new LoginDocumentListener());
		// Action listeners
		loginButton.addActionListener(new LoginActionListener());
		createAccount.addActionListener(new CreateActionListener());
		guestButton.addActionListener(new GuestActionListener());
	}

	/**
	 * The {@code LoginDocumentListener} implements the {@code DocumentListener} 
	 * to listen for input in the text fields to set the buttons on the GUI either
	 * enabled or disabled.
	 * @author alancoon
	 * @see javax.swing.event.DocumentListener
	 */
	private class LoginDocumentListener implements DocumentListener {

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

	/**
	 * The {@code LoginActionListener} is the subroutine that executes
	 * when the login button is pressed.
	 * @author alancoon
	 *
	 */
	private class LoginActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				PreparedStatement ps = conn.prepareStatement(queryStatement);
				ps.setString(1, username.getText().trim());
				rs = ps.executeQuery();
				if (rs.next()) {
					/* User name exists, now check password. */
					if (rs.getString(3).equals(password.getText())) {
						/* Valid login! */
						User currentUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
						new IntroGUI(currentUser).setVisible(true);
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
				sqle.printStackTrace();
			} 
		}
	}
	
	/**
	 * The {@code CreateActionListener} is the subroutine that
	 * executes when the create account button is pressed.
	 * @author alancoon
	 *
	 */
	private class CreateActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				PreparedStatement ps = conn.prepareStatement(queryStatement);
				ps.setString(1, username.getText().trim());
				rs = ps.executeQuery();
				if (rs.next()) {
					/* User name already in use. */
					alertLabel.setText("Username already exists.");
					rs = null;
				} else {
					/* User name does not exist. */
					PreparedStatement is = conn.prepareStatement(insertStatement);
					is.setString(1, username.getText().trim());
					is.setString(2, password.getText());
					is.execute();
					
					
					/* Now we must fetch the information we just inserted to get the ID. */
					PreparedStatement ps2 = conn.prepareStatement(queryStatement);
					ps2.setString(1, username.getText().trim());
					rs = ps2.executeQuery();
					if (rs.next()) { 
						
					}
					User newUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
					new IntroGUI(newUser).setVisible(true);
					dispose();
				}
			} catch (SQLException sqle) {
				System.out.println("SQLException: " + sqle.getMessage());
			} 
		}
	}
	
	private class GuestActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/* Guest users will have an ID of -1. */
			new IntroGUI(new User(-1, "Guest User", "Guest Password")).setVisible(true);
			dispose();
		}
	}
}

