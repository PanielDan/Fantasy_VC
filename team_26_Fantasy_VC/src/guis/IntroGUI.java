package guis;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import gameplay.User;
import net.miginfocom.swing.MigLayout;
import utility.Constants;

public class IntroGUI extends JFrame {

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private User user;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IntroGUI frame = new IntroGUI(new User(-1, "Guest User", "Guest Password", 0, 0, 0));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IntroGUI(User user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(70, 130, 180));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(70, 130, 180));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(70, 130, 180));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(70, 130, 180));

		Icon lobbiesIcon = new ImageIcon(Constants.images + "lobbies" + Constants.png);
		ImageIcon statisticsIcon = new ImageIcon(Constants.images + "statistics" + Constants.png);
		ImageIcon profileIcon = new ImageIcon(Constants.images + "profile" + Constants.png);
		
		JComponent lobbiesPane = new JPanel();
		lobbiesPane.setBackground(new Color(70, 130, 180));
		JPanel statisticsPane = new JPanel();
		statisticsPane.setBackground(new Color(70, 130, 180));
		JPanel profilePane = new JPanel();
		profilePane.setBackground(new Color(70, 130, 180));
		
		tabbedPane.addTab("Lobbies", lobbiesIcon, lobbiesPane, "See joinable lobbies");
		tabbedPane.addTab("Statistics", statisticsIcon, statisticsPane, "See your statistics");
		statisticsPane.setLayout(new MigLayout("", "[][grow]", "[][grow]"));
		
		JLabel lblNewLabel = new JLabel("Lifetime Statistics");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		statisticsPane.add(lblNewLabel, "cell 0 0");
		
		table = new JTable();
		statisticsPane.add(table, "cell 0 1 2 1,grow");
		tabbedPane.addTab("Your Profile", profilePane);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1258, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(269)
							.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 602, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panel_4.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel titleLabel = new JLabel("Fantasy Venture Capital");
		titleLabel.setForeground(new Color(255, 255, 255));
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 48));
		panel_1.add(titleLabel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblHello = new JLabel("Hello,");
		lblHello.setForeground(new Color(255, 255, 255));
		panel.add(lblHello);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setForeground(new Color(255, 255, 255));
		panel.add(lblUsername);
		contentPane.setLayout(gl_contentPane);
	}
}
