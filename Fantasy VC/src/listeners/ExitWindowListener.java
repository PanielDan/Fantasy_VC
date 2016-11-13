package listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game_logic.JeopardyClient;
import messages.ClientQuitGameMessage;
import other_gui.AppearanceConstants;
//pop-up for when user clicks the red X on a frame
public class ExitWindowListener extends WindowAdapter{

	private JFrame frame;
	private JeopardyClient jeopardyClient;
	private final boolean isMultiplayer;
	public ExitWindowListener(JFrame frame, JeopardyClient jeopardyClient) {
		this.frame = frame;
		this.jeopardyClient = jeopardyClient;
		this.isMultiplayer = true;
	}
	
	public ExitWindowListener(JFrame frame) {
		this.frame = frame;
		this.isMultiplayer = false;
	}
	
	 public void windowClosing(WindowEvent e) {
		 int answer = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, AppearanceConstants.exitIcon);
	     
		 if (isMultiplayer) {
			 String name = jeopardyClient.getTeamName();
			 ClientQuitGameMessage message = new ClientQuitGameMessage(name + " quit.", name);
			 jeopardyClient.sendMessage(message);
		 }
		 
		 if (answer == JOptionPane.YES_OPTION){
			 System.exit(0);
		 }
	 }
}
