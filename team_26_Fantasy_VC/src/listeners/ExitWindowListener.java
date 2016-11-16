package listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.Client;
import messages.ClientExitMessage;
import utility.AppearanceConstants;


//pop-up for when user clicks the red X on a frame
public class ExitWindowListener extends WindowAdapter{

	private JFrame frame;
	private Client client;
	private final boolean isMultiplayer;
	public ExitWindowListener(JFrame frame, Client client) {
		this.frame = frame;
		this.client = client;
		this.isMultiplayer = true;
	}
	
	public ExitWindowListener(JFrame frame) {
		this.frame = frame;
		this.isMultiplayer = false;
	}
	
	 public void windowClosing(WindowEvent e) {
		 int answer = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
	     
		 if (isMultiplayer) {
			 String name = client.getFirmName();
			 ClientExitMessage message = new ClientExitMessage("tempusername");
			 client.sendMessage(message);
		 }
		 
		 if (answer == JOptionPane.YES_OPTION){
			 System.exit(0);
		 }
	 }
}
