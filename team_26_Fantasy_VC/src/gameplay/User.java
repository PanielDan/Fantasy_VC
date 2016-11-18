
package gameplay;

import java.awt.Image;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.ImageIcon;

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
	//User icon saved as an image, all we need to to pass in the string
	private Image userIcon;
	private String userIconString;
	private String username;
	private int gamesPlayed;
	private int gamesWon;
	private double totalProfit;
	private Vector<Company> companies;
	private double currentCapital;
	//For the user Text blurb
	private String userBio;
	private String companyName;
	
	public User(int id, String username, String password, String userBio, int gamesPlayed, int gamesWon, double totalProfit) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.gamesPlayed = gamesPlayed;
		this.userBio = userBio;
		this.gamesWon = gamesWon;
		this.totalProfit = totalProfit;
		companies = new Vector<Company>();
		currentCapital = 100.0;
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
	public synchronized double getTotalProfit() { 
		return totalProfit;
	}
	
	/**
	 * @return The user's currentCapital.
	 */
	public synchronized double getCurrentCapital() {
		return currentCapital;
	}
	
	public Image getUserIcon(){
		 return userIcon;
	}
	
	public void setUserIcon(String input){
		ImageIcon icon = new ImageIcon(input);
		userIcon = icon.getImage();
	}
	/**
	 * @set The user's currentCapital.
	 */
	public synchronized void setCurrentCapital(double amount) {
		currentCapital = amount;
	}
	
	public String getUserBio() {
		return userBio;
	}
	
	public void setUserBio(String input){
		userBio = input;
	}
	
	public void setUsername(String input){
		username = input;
	}
	
	public void setCompanyName(String input){
		companyName = input;
	}
	
	public String getCompanyName(){
		return companyName;
	}
	
	/**
	 * Company manipulation
	 */
	public synchronized Vector<Company> getCompanies() {
		return companies;
	}
	
	public synchronized void setCompanies(Vector<Company> companies) {
		this.companies = companies;
	}
	
	public synchronized void addCompany(Company company) {
		companies.add(company);
	}
	
	public synchronized void deleteCompany(Company company) {
		for(int i = 0; i < companies.size(); i++) {
			if(companies.get(i).getName().equals(company.getName())) {
				companies.remove(i);
				return;
			}
		}
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
			return Double.compare(teamOne.getTotalProfit(), teamTwo.getTotalProfit());
		}
		
	}
	
	public static MoneyComparator getComparator(){
		return new MoneyComparator();
	}
	
}
