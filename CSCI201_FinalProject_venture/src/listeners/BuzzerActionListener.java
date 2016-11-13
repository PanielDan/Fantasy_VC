package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import frames.MainGUI;
import game_objects.QuestionState;
import messages.BuzzMessage;

// Action Listener for the buzzer button
public class BuzzerActionListener implements ActionListener { 
	private MainGUI mainGUI;
	private String teamName;
	private int teamIndex;
	public QuestionState currentQuestionState;
	public static Vector<String> teamNames;
	
	public BuzzerActionListener(MainGUI mainGUI, int teamIndex, String teamName, QuestionState currentQuestionState) {
		this.mainGUI = mainGUI;
		this.teamName = teamName;
		this.teamIndex = teamIndex;
//		this.teamIndex = teamNames.indexOf(teamName);
		this.currentQuestionState = currentQuestionState;
	}
	
	public static void setTeamNames(Vector<String> input) {
		teamNames = input;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) { 
		String name = mainGUI.getJeopardyClient().getTeamName();
		System.out.println("Buzzer::name " + name);
		BuzzMessage message = new BuzzMessage(teamIndex, teamName, currentQuestionState);
		this.mainGUI.getJeopardyClient().sendMessage(message);
		
	}
}