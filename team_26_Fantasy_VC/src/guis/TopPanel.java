package guis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.Client;
import gameplay.User;
import messages.UserInfoPopupMessage;
import utility.AppearanceConstants;
import utility.AppearanceSettings;

/**
 * The {@code TopPanel} is a {@code JPanel} that borders the top of the screen 
 * @author alancoon
 *	TODO: Needs to port images from FTP server to the user Icon
 *	
 *	Danny's Changes
 *		- Cleaned up GUI Code
 *		- Added JButton as User Icon
 *		- Added addedAction listener functions 
 */
public class TopPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private JLabel title;
	private JLabel username;
	private BufferedImage avatar;
	private JButton userIcon;
	private Client client;
	
	/**
	 * Create the panel.
	 */
	public TopPanel(Client client, User user) {
		this.client = client;
		initializeComponents(user);
		createGUI();
		addActionListeners();
	}
	
	private void initializeComponents(User user) {
		
		/*	TODO
		 *  Attempt to grab the user's avatar filepath and create a BufferedImage using that.  Then use the Buffered Image
		 * to create an ImageIcon, which can be placed in a JLabel, which we can use in our GUI. */
		/*
		 * We'll do this later when we have the server setup
		try {
		    avatar = ImageIO.read(this.getClass().getResource(user.getImage()));
			avatarLabel = new JLabel(new ImageIcon(avatar));
		} catch (IOException ioe) {
			System.out.println("IOException in TopPanel.initializeComponents(): " + ioe.getLocalizedMessage());
			ioe.printStackTrace();
		}
		*/		
		//userIcon here
		userIcon = new JButton();
		userIcon.setOpaque(true);
		AppearanceSettings.unSetBorderOnButtons(userIcon);
		userIcon.setBackground(AppearanceConstants.darkBlue);
		userIcon.setVerticalAlignment(SwingConstants.CENTER);
		
		//Test Code
		ImageIcon profile = new ImageIcon("resources/img/profile.png");
		Image profileImage = profile.getImage();
		userIcon.setIcon(new ImageIcon(profileImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
		
		//Username formatting
		username = new JLabel(user.getUsername());
		username.setBorder(new EmptyBorder(5,5,5,5));
		username.setFont(AppearanceConstants.fontHeaderUser);
		//username.setHorizontalAlignment(SwingConstants.CENTER);
		username.setForeground(AppearanceConstants.offWhite);
		
		//Banner Formatting
		title = new JLabel("Fantasy VC");
		title.setFont(AppearanceConstants.fontHeader);
		title.setBorder(new EmptyBorder(5,5,5,5));
		title.setForeground(AppearanceConstants.offWhite);
		title.setVerticalAlignment(SwingConstants.CENTER);
	}

	private void createGUI() {
		
		//Looks good
		setPreferredSize(new Dimension(1280, 72));
		//Just have to upper bounded
		setMaximumSize(new Dimension(1280, 72));  // May cause problems
		setBackground(AppearanceConstants.darkBlue);
		setLayout(new BorderLayout());
		
		//Sub pane just need flow layout
		JPanel subPane = new JPanel();
		subPane.setBackground(AppearanceConstants.darkBlue);
		subPane.add(username);
		subPane.add(userIcon);

		//Adding everything at once
		add(title, BorderLayout.WEST);
		add(subPane, BorderLayout.EAST);
	}
	
	private void addActionListeners(){
		userIcon.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				if(networked){
					client.sendMessage(new UserInfoPopupMessage());
				} else {
					
				}
				*/
				//add User info pop up here
			}
		});
	}
}
