
package gameplay;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import utility.CompanyPopulator;

/**
 * Maintains all of the current players inside of the instance of the game
 * Has a list of all the companies that are still available.
 * Also keeps track of the game state.
 * 
 * We decided to use the {@code Serializable} interface so we can 
 * transmit the {@code Game} over object streams from the server to 
 * the player clients.  This way we can do computations that affect 
 * the {@code Game} state and then package and ship it to every 
 * player so a uniform state is available for all of the players.
 *
 * @author arschroc
 * @author alancoon
 */
public class Game implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private List<User> users;
	private List<Company> companies;
	int currentQuarter;
	
	//Constructor for not networked game
	public Game() {
		currentQuarter = 0;
		users = new Vector<User>();
		companies = new Vector<Company>();
		initializeCompanies();
	}
	
	//constructor for networked game
	public Game(Vector<User> users) {
		currentQuarter = 0;
		this.users = users;
		companies = new Vector<Company>();
	}
	
	/**
	 * To be done only on the SERVER side at the beginning
	 * of the game, initializes all of the companies from the server
	 */
	public void initializeCompanies() {
		//TODO UNCOMMENT THESE WHEN NETWORKING IS READY
		//CompanyPopulator compPop = new CompanyPopulator();
		//companies = compPop.populate();
		
		//DUMMY COMPANIES
		for(int i = 0; i < 10; i++) {
			Company tmpCompany = new Company("resources/img/profile.png", "Company " + i, "this is company " + i, 1000, 0);
			companies.add(tmpCompany);
		}
		for(int i = 10; i < 20; i++) {
			Company tmpCompany = new Company("resources/img/profile.png", "Company " + i, "this is company " + i, 10000, 1);
			companies.add(tmpCompany);
		}
		for(int i = 20; i < 30; i++) {
			Company tmpCompany = new Company("resources/img/profile.png", "Company " + i, "this is company " + i, 100000, 2);
			companies.add(tmpCompany);
		}
		for(int i = 30; i < 40; i++) {
			Company tmpCompany = new Company("resources/img/profile.png", "Company " + i, "this is company " + i, 1000000, 3);
			companies.add(tmpCompany);
		}
		for(int i = 40; i < 50; i++) {
			Company tmpCompany = new Company("resources/img/profile.png", "Company " + i, "this is company " + i, 10000000, 4);
			companies.add(tmpCompany);
		}
		
		
		System.out.println("Companies populated!");
		System.out.println("Ready to play.");
		
	}
	
	public Vector<String> updateNonNetworkedCompanies() {
		Vector<String> output = new Vector<String>();
		
		for(Company company : companies) {
			//update every company
			String updateText = company.updateCurrentWorth();
			
			
			//update a user's version of the company (if owned)
			for(User user : users) {
				for(Company userCompany : user.getCompanies()) {
					if(userCompany.getName().equals(company.getName())) {
						userCompany = company;
					}
				}
			}
			
			if(updateText != null) {
				output.add(updateText);
			}
		}
		
		return output;
	}
	
	/**
	 * To be done ONLY IN SERVER
	 * updates all of the companies and sends a message to 
	 * the clients with the new companies
	 */
	public void updateCompanies() {
		for(Company company : companies) {
			//update every company
			String updateText = company.updateCurrentWorth();
			
			
			//update a user's version of the company (if owned)
			for(User user : users) {
				for(Company userCompany : user.getCompanies()) {
					if(userCompany.getName().equals(company.getName())) {
						userCompany = company;
					}
				}
			}
			
			if(updateText != null) {
				//TODO create and send a message to all clients 
				//telling them to display the updateText on the TimeLapseGUI
			}
		}
		
		//TODO send message to all clients containing the new game and User updates
	}
	
	/**
	 * 
	 * To be done FOR ALL CLIENTS
	 * updates all of the companies by making them
	 * the same as the server's already updated companies
	 */
	public void updateCompanies(Vector<Company> companies, Vector<User> users) {
		this.companies = companies;
		this.users = users;
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