
package gameplay;

import java.util.Comparator;
import java.util.Vector;

/**
 * The {@code User} class stores information about the logged
 * in user, using data queried from the MySQL database.
 *
 * @author alancoon
 * 
 */
public class User {

	private int id;
	private String password;
	private String username;
	private int gamesPlayed;
	private int gamesWon;
	private long totalProfit;
	private Vector<Company> companies;
	
	public User(int id, String username, String password, int gamesPlayed, int gamesWon, long totalProfit) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.totalProfit = totalProfit;
		companies = new Vector<Company>();
	}

	/**
	 * @return The user's ID from the MySQL data table.
	 */
	public synchronized int getID() {
		return id;
	}

	/**
	 * Well that's dangerous! Let's make that private.
	 * Or better yet let's delete it entirely.
	 * @return The user's unhashed password.
	 */
	private synchronized String getPassword() {
		return password;
	}

	/**
	 * @return The user's username.
	 */
	public synchronized String getUsername() {
		return username;
	}
	
	/**
	 * @return The lifetime number of games the user has finished.
	 */
	public synchronized int getGamesPlayed() { 
		return gamesPlayed;
	}
	
	/**
	 * @return The lifetime number of games the user has won.
	 */
	public synchronized int getGamesWon() {
		return gamesWon;
	}
	
	/**
	 * @return The lifetime profit the user has earned (or lost).
	 */
	public synchronized long getTotalProfit() { 
		return totalProfit;
	}
	
	
	/**
	 * Comparator class for sorting users
	 * @author arschroc
	 *
	 */
	//comparator for sorting users
	//it is passed into the sort method from the Java Collections class as a custom comparator
	//this will sort the users in order of their total profit
	private static class MoneyComparator implements Comparator<User>{

		@Override
		public int compare(User teamOne, User teamTwo) {
			return Long.compare(teamOne.getTotalProfit(), teamTwo.getTotalProfit());
		}
		
	}
	
	public static MoneyComparator getComparator(){
		return new MoneyComparator();
	}
	
}
