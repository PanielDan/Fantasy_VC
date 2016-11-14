package game_objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;

/**
 * The {@code TopPanel} is a {@code JPanel} that borders the top of the screen 
 * @author alancoon
 *
 */
public class TopPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private JLabel title;
	private JLabel username;
	private BufferedImage avatar;
	private JLabel avatarLabel;
	
	/**
	 * Create the panel.
	 */
	public TopPanel(User user) {
		initializeComponents(user);
		createGUI();
	}
	
	private void initializeComponents(User user) {
		
		/* Attempt to grab the user's avatar filepath and create a BufferedImage using that.  Then use the Buffered Image
		 * to create an ImageIcon, which can be placed in a JLabel, which we can use in our GUI. */
		try {
		    avatar = ImageIO.read(this.getClass().getResource(user.getImage()));
			avatarLabel = new JLabel(new ImageIcon(avatar));
		} catch (IOException ioe) {
			System.out.println("IOException in TopPanel.initializeComponents(): " + ioe.getLocalizedMessage());
			ioe.printStackTrace();
		}
		
		/* Grab username from the user class, title is hardcoded. */
		// TODO Remember to change fonts in this document.
		username = new JLabel(user.getUsername());
		title = new JLabel("Fantasy Venture Capital");
		title.setFont(new Font("Lucida Grande", Font.ITALIC, 36));
	}

	private void createGUI() {
		
		// TODO Remember to change to AppearanceConstants colors instead of hardcoding here,
		// 		in case we decide to change our colors down the road.
		Color darkblue = new Color(49, 59, 71);
		Color offwhite = new Color(221, 221, 221);
		
		setPreferredSize(new Dimension(1280, 72));
		setMinimumSize(new Dimension(0, 72));  // May cause problems
	
		setBackground(darkblue);
		title.setForeground(offwhite);
		setLayout(new BorderLayout());
		add(title, BorderLayout.CENTER);
		
		JPanel subPane = new JPanel();
		subPane.setBackground(darkblue);
		subPane.setLayout(new MigLayout("", "[][28px][1px]", "[16px][][]"));
		subPane.add(avatarLabel, "cell 1 1,alignx left,aligny center");
		
		
		this.add(subPane, BorderLayout.EAST);
						username.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
						username.setHorizontalAlignment(SwingConstants.CENTER);
						username.setForeground(offwhite);
						subPane.add(username, "cell 0 1,alignx left,aligny top");
	}
}
