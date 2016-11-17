package guis;

import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import gameplay.GameFrame;
import gameplay.User;
import utility.AppearanceConstants;
import utility.AppearanceSettings;
import utility.FinalUserButton;

public class FinalGUI extends JPanel {

	private GameFrame gameFrame;
	private Vector<FinalUserButton> userButtons;
	private JLabel winner, userIcon, userFirmName, totalEquity,
	percentGain, numCompanies, bestInvestment;
	private JButton done;
	private JTable portfolio;
	
	public FinalGUI(GameFrame gameFrame){
		this.gameFrame = gameFrame;
		initializeVariables();
		CreateGUI();
		addActionListeners();
	}
	
	private void initializeVariables(){
		userButtons = new Vector<FinalUserButton>();
		for (User user : gameFrame.game.getWinners()){
			userButtons.add(new FinalUserButton(user));
		}
		
		done = new JButton("Done");
		done.setOpaque(true);
		AppearanceSettings.unSetBorderOnButtons(done);
		AppearanceSettings.setBackground(AppearanceConstants.darkBlue, done);
		AppearanceSettings.setForeground(AppearanceConstants.offWhite, done);
		
		//TODO All these labels need logic
		userIcon = new JLabel(new ImageIcon(gameFrame.getIconImage().getScaledInstance(200, 200,java.awt.Image.SCALE_SMOOTH)));
		winner = new JLabel("Winner: JMoney Capital");
		userFirmName = new JLabel("JMoney Capital");
		totalEquity = new JLabel("Total Equity: 308 Million");
		percentGain = new JLabel("Percentage Gain: 1250%");
		bestInvestment = new JLabel("Best investment: Umbrella Corp");
		
		portfolio = new JTable();
	}
	
	private void CreateGUI(){
		
	}
	
	private void addActionListeners(){
		
	}
}
