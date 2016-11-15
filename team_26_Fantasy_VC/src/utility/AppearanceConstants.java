package utility;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class AppearanceConstants {
	
	//colors, fonts, ect that can be statically referenced by other classes	
	public static final Color darkBlue = new Color(49,59,71);
	public static final Color lightBlue = new Color(49,71,112);
	public static final Color offWhite = new Color(221, 221, 221);
	public static final Color mediumGray = new Color(100, 100, 100);
	public static final Color darkGray = new Color(31,31,31);


	
	public static final Font fontSmall = new Font("Avenir", Font.BOLD,18);
	public static final Font fontSmallest = new Font("Avenir", Font.BOLD,14);
	public static final Font fontMedium = new Font("Avenir", Font.BOLD, 22);
	public static final Font fontLarge = new Font("Avenir", Font.BOLD, 30);
	
	//added a blue border variable used in StartWindowGUI
	public static final Border blueLineBorder = BorderFactory.createLineBorder(darkBlue);
}
