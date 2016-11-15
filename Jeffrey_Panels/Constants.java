package resources;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Constants {
	//colors, fonts, ect that can be statically referenced by other classes
	private static final ImageIcon exitIconLarge = new ImageIcon("images/question_mark.png");
	private static final Image exitImage = exitIconLarge.getImage();
	private static final Image exitImageScaled = exitImage.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
	
	public static final ImageIcon exitIcon = new ImageIcon(exitImageScaled);
	
	public static final Color offWhite = new Color(221,221,221);
	public static final Color lightBlue = new Color(49,71,112);
	public static final Color darkBlue = new Color(49,59,71);
	public static final Color darkGray = new Color(31,31,31);
	
	public static final Font fontSmall = new Font("Palatino", Font.BOLD,18);
	public static final Font fontSmallest = new Font("Palatino", Font.BOLD,14);
	public static final Font fontMedium = new Font("Palatino", Font.BOLD, 22);
	public static final Font fontLarge = new Font("Palatino", Font.BOLD, 30);
	//added a blue border variable used in StartWindowGUI
}
