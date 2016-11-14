package game_objects;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Maintains all of the current players inside of the instance of the game
 * Has a list of all the companies that are still available
 * Also keeps track of the game state
 *
 * @author arschroc
 * 
 */
public class Game {
	
	private List<User> users;
	private List<Company> companies;
	int currentQuarter;
	
	public Game() {
		currentQuarter = 0;
		users = new Vector<User>();
		companies = new Vector<Company>();
		
		//TODO create all of the companies and add them to companies list
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	//returns a list of winning teams
	public Vector<User> getWinners() {
		Vector<User> finalists = (Vector<User>) users;
		Vector<User> winners = new Vector<User>();
		
		//sorts the finalists in order of their total profit
		Collections.sort(finalists, User.getComparator());
		User definiteWinner = finalists.get(finalists.size() - 1);
		long max = definiteWinner.getTotalProfit();
		
		winners.add(definiteWinner);
		
		//check to see if there are other winners
		if(finalists.size() > 1) {
			
			for(int i = finalists.size() - 2; i > -1; i--) {
				//if this team has the same score as the definite winner then their is a tie
				if(finalists.get(i).getTotalProfit() == max) {
					winners.add(finalists.get(i));
				}
			}
		}
		
		return winners;
	}
}
