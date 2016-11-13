package game_objects;

/**
 * The {@code Company} class stores information about a company.
 * Each company is matched with one of the trends form the MySQL database
 * The Company will either belong to the Game or a specific user
 *
 * @author arschroc
 * 
 */

public class Company {
	
	private String image, name, description;
	private int startingPrice, askingPrice, currentWorth, tierLevel;
	
	public Company(String image, String name, String description, int startingPrice, int currentWorth, int tierLevel) {
		this.image = image;
		this.name = name;
		this.description = description;
		this.startingPrice = startingPrice;
		askingPrice = startingPrice;
		this.currentWorth = currentWorth;
		this.tierLevel = tierLevel;
	}
	
	
	
}
