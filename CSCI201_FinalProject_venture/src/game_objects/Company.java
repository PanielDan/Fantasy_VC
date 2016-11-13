package game_objects;

import java.util.List;

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
	private List<Integer> trends; //list of the company's trends for each quarter
	
	public Company(String image, String name, String description, int startingPrice, List<Integer> trends, int tierLevel) {
		
		this.image = image;
		this.name = name;
		this.description = description;
		this.startingPrice = startingPrice;
		askingPrice = startingPrice; //asking price = startingPrice at beginning of auction
		this.trends = trends;
		currentWorth = trends.get(0); //currentWorth starts at the initial trend
		this.tierLevel = tierLevel;
	}
	
	//GETTER functions
	public String getName() {
		return name;
	}
	
	public String getImage() {
		return image;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getStartingPrice() {
		return startingPrice;
	}
	
	public int getAskingPrice() {
		return askingPrice;
	}
	
	public int getCurrentWorth() {
		return currentWorth;
	}
	
	public int getTierLevel() {
		return tierLevel;
	}
	
	//SETTER functions
	public void updateCurrentWorth(int quarterNum) {
		//if acceptable quarter number then update currentWorth
		if(quarterNum < 20 && quarterNum >= 0) {
			currentWorth = trends.get(quarterNum);
		}
	}
	
	public void updateAskingPrice(int askingPrice) {
		this.askingPrice = askingPrice;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setStartingPrice(int startingPrice) {
		this.startingPrice = startingPrice;
	}
	
	public void setTier(int tierLevel) {
		this.tierLevel = tierLevel;
	}
	
	//METHODS
	public void performRandomEvent() {
		//TODO
	}	
}
