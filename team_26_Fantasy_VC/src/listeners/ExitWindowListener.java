package listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.Client;
import messages.ClientExitMessage;


/**
 * Will allow a {@code Client} to gracefully exit the game by using the
 * {@code JFrame}'s red exit button from the operating system. Informs
 * the other clients in the same game that a person has left.
 * 
 * @author alancoon
 *
 */
public class ExitWindowListener extends WindowAdapter{

	private JFrame frame;
	private Client client;
	private final boolean isMultiplayer;
	
	/**
	 * We have overloaded constructors so if you pass in a {@code Client},
	 * the logic will know that we are currently in a multiplayer game.
	 * Otherwise we will assume that it is singleplayer guest mode.
	 * @param frame The {@code JFrame} that you want to add the 
	 * {@code ExitWindowListener} to.
	 * @param client The {@code Client} that the {@code JFrame} belongs to.
	 */
	public ExitWindowListener(JFrame frame, Client client) {
		this.frame = frame;
		this.client = client;
		this.isMultiplayer = true;
	}
	
	/**
	 * Single player guest mode.
	 * @param frame The frame that you want to add the {@code ExitWindowListener} to.
	 */
	public ExitWindowListener(JFrame frame) {
		this.frame = frame;
		this.isMultiplayer = false;
	}
	
	 public void windowClosing(WindowEvent e) {
		 int answer = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
		 if (answer == JOptionPane.YES_OPTION) {
			 if (isMultiplayer) {
				 String name = client.getUser().getUsername();
				 ClientExitMessage message = new ClientExitMessage(name);
				 client.sendMessage(message);
			 }
			 System.exit(0);
		 }
	 }
}
