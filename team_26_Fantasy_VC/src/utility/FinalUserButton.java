package utility;

import javax.swing.JButton;

import gameplay.User;

public class FinalUserButton extends JButton {
	private User user;
	
	public FinalUserButton(User user){
		//Has to be replaced with firm name;
		super(user.getUsername());
		this.user = user;
		setOpaque(true);
		setBorderPainted(false);
		setBackground(AppearanceConstants.darkBlue);
		setFont(AppearanceConstants.fontButtonMedium);
	}
	
	public User getUser(){
		return user;
	}
}
