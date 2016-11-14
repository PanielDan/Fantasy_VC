package game_objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import client.Client;
import listeners.TextFieldFocusListener;
import messages.ChatMessage;
import net.miginfocom.swing.MigLayout;
import util.AppearanceConstants;
import util.AppearanceSettings;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * The {@code ChatPanel} is an extension of {@code JPanel} 
 * that can be passed along between graphical interfaces
 * for the user to communicate with other players.
 * @author alancoon
 *
 */
public class ChatPanel extends JPanel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	private Client client;
	private static JTextArea chat;
	private JTextField input;
	private JButton submit;
	
	public ChatPanel(Client client) { 
		this.client = client;
		this.user = client.getUser();
		initializeComponents();
		createGUI();
		addEvents();
		colorizeComponents();
	}


	/**
	 * Initialize the data member GUI components we need for this panel.
	 */
	private void initializeComponents() {
		input = new JTextField(30);
		submit = new JButton("Send");
		chat = new JTextArea(10, 40);

	}
	
	/**
	 * Just to color the data member GUI components.
	 */
	private void colorizeComponents() {
		// TODO change once we get the AppearanceSettings/Constants up and running
		Color lighterBlue = new Color(49, 71, 112);
		Color darkBlue = new Color(49, 59, 71);
		Color offWhite = new Color(221, 221, 221);
		
		submit.setBackground(lighterBlue);
		submit.setForeground(offWhite);
		input.setBackground(offWhite);
		chat.setBackground(offWhite);
		
		this.setBackground(darkBlue);
	}
	
	/**
	 * Assembles the GUI.  Mostly generated.
	 */
	private void createGUI() {
		
		// Look here
		setPreferredSize(new Dimension(720, 144));
		
		
		JScrollPane scroll = new JScrollPane();
		scroll.setMinimumSize(new Dimension(100, 40));
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(input, GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(submit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scroll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(input, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(submit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(13))
		);
		scroll.setViewportView(chat);
		
		
		// Wrap the chat box
		chat.setWrapStyleWord(true);
		chat.setLineWrap(true);
		chat.setEditable(false);
		setLayout(groupLayout);
		
		// Pimp my button
		AppearanceSettings.unSetBorderOnButtons(submit);
		AppearanceSettings.setOpaque(submit);
		
	}

	/**
	 * Add listeners to the components.
	 */
	private void addEvents() {
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChatMessage message = new ChatMessage(client.getUser().getUsername(), input.getText());
				System.out.println(message.getUsername() + " says: " + message.getMessage());
				input.setText("");
				client.sendMessage(message);
			} 
		});
		input.addFocusListener(new TextFieldFocusListener("Chat...", input));
		input.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyPressed(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ChatMessage message = new ChatMessage(client.getUser().getUsername(), input.getText());
					input.setText("");
					client.sendMessage(message);
				}
			}
		});
		input.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				changed();
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changed();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				changed();
			}
			
			/**
			 * Enable the submit button if the field's foreground color is black,
			 * which means that it isn't empty.
			 */
			private void changed() {
				submit.setEnabled(input.getForeground().equals(Color.black));
			}
		});
	}
	

}
