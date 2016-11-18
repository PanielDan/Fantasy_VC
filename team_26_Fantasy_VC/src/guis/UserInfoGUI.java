package guis;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import gameplay.GameFrame;
import utility.AppearanceConstants;
import utility.AppearanceSettings;

public class UserInfoGUI extends JFrame {
	private JButton userIcon, cancel, save;
	private JTextField username;
	private JTextArea userBio;
	private String imageLocation;
	private GameFrame gameFrame;
	
	
	public UserInfoGUI(GameFrame gameFrame){
		super("User Information");
		this.gameFrame = gameFrame;
		intializeVariables();
		createGUI();
		addActionListeners();
		setVisible(true);
		toFront();
	}
	
	private void intializeVariables(){		
		//Commented out portion is the real code
		//userIcon = new JButton(new ImageIcon(gameFrame.user.getUserIcon().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
		userIcon = new JButton();
		userIcon.setOpaque(true);
		AppearanceSettings.unSetBorderOnButtons(userIcon);
		
		//testCode
		ImageIcon profile = new ImageIcon("resources/img/profile.png");
		Image profileImage = profile.getImage();
		userIcon.setIcon(new ImageIcon(profileImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
		
		
		username = new JTextField(gameFrame.user.getUsername());
		userBio = new JTextArea(gameFrame.user.getUserBio());
		
		cancel = new JButton("Cancel");
		cancel.setOpaque(true);

		save = new JButton("Save");
		save.setOpaque(true);

	}
	
	private void createGUI(){
		setSize(400,600);
		setLocationRelativeTo(null);
		
		JPanel framePanel = new JPanel();
		JPanel userNameTextPanel = new JPanel();
		JPanel userBioTextPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JLabel userNameLabel = new JLabel("Username");
		JLabel userBioLabel = new JLabel("Bio");

		//Layouts
		framePanel.setLayout(new BoxLayout(framePanel, BoxLayout.PAGE_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		userNameTextPanel.setLayout(new BorderLayout());
		userBioTextPanel.setLayout(new BorderLayout());

		//BorderPadding
		userBio.setBorder(new EmptyBorder(5,5,5,5));
		userNameLabel.setBorder(new EmptyBorder(5,5,5,5));
		userBioLabel.setBorder(new EmptyBorder(5,5,5,5));

		//Sizes
		AppearanceSettings.setSize(300, 60, userNameTextPanel);
		userNameTextPanel.setMaximumSize(new Dimension(300,60));
		AppearanceSettings.setSize(300, 150, userBioTextPanel);
		userBioTextPanel.setMaximumSize(new Dimension(300,150));

		//appearance settings
		AppearanceSettings.setBackground(AppearanceConstants.lightBlue, userNameTextPanel,userNameTextPanel,
				buttonPanel, framePanel, userIcon);
		AppearanceSettings.setBackground(AppearanceConstants.offWhite, username, userBio);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, cancel, save);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, cancel, save, userNameLabel,
				userBioLabel);
		AppearanceSettings.setForeground(AppearanceConstants.darkBlue, username, userBio);
		AppearanceSettings.setFont(AppearanceConstants.fontSmall, username, userNameLabel,userBioLabel);
		AppearanceSettings.setFont(AppearanceConstants.fontSmallest, userBio);
		AppearanceSettings.setFont(AppearanceConstants.fontButtonBig, cancel, save);
		AppearanceSettings.unSetBorderOnButtons(userIcon, cancel, save);
		AppearanceSettings.setCenterAlignment(userNameLabel, userBioLabel);
		userIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		userNameTextPanel.add(username);
		userBioTextPanel.add(userBio);
		
		buttonPanel.add(cancel);
		buttonPanel.add(Box.createHorizontalStrut(20));
		buttonPanel.add(save);

		AppearanceSettings.addGlue(framePanel, BoxLayout.PAGE_AXIS, true,
				userIcon);
		framePanel.add(userNameLabel);
		AppearanceSettings.addGlue(framePanel, BoxLayout.PAGE_AXIS, false,
				userNameTextPanel);		
		framePanel.add(userBioLabel);
		AppearanceSettings.addGlue(framePanel, BoxLayout.PAGE_AXIS, false,userBioTextPanel,
				buttonPanel);

		add(framePanel);
	}
	
	private void addActionListeners(){
		userIcon.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChoose = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images Files", 
						"png","jpg","gif","bmp");
				fileChoose.setFileFilter(filter);
				int check = fileChoose.showOpenDialog(getParent());
				if (check == JFileChooser.APPROVE_OPTION){
					imageLocation = fileChoose.getSelectedFile().getAbsolutePath();
					updateIcon(imageLocation);
				}
			}
			
		});
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameFrame.user.setUsername(username.getText());
				gameFrame.user.setUserBio(userBio.getText());
				if (imageLocation != null){
					gameFrame.user.setUserIcon(imageLocation);
					gameFrame.header.updateIcon();
				}
				//Need to write function that updates SQL server and and the panels
				dispose();
			}
			
		});
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	private void updateIcon(String input){
		ImageIcon profile = new ImageIcon(input);
		Image profileImage = profile.getImage();
		userIcon.setIcon(new ImageIcon(profileImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)));
	}
}
