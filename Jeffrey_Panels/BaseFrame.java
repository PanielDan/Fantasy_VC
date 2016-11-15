import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BaseFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	LobbyPanel LPanel;
	IntroPanel IPanel;

	BaseFrame() {
		initializeComponents();
		createGUI();
		addEvents();
		this.setVisible(true);
	}
	
	private void initializeComponents() {
		LPanel = new LobbyPanel();
		IPanel = new IntroPanel();
	}
	
	private void createGUI() {
		this.setSize(1280, 720);
		JPanel chatPanel = new JPanel();
		chatPanel.setPreferredSize(new Dimension(0, 144));
		chatPanel.setBackground(Color.black);
		this.add(chatPanel, BorderLayout.SOUTH);
		JPanel bannerPanel = new JPanel();
		bannerPanel.setPreferredSize(new Dimension(0, 72));
		bannerPanel.setBackground(Color.black);
		this.add(bannerPanel, BorderLayout.NORTH);
		this.add(LPanel);
	}
	
	private void addEvents() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String [] args) {
		new BaseFrame();
	}

}
