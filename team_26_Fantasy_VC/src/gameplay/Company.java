package gameplay;

import java.util.Random;

import utility.Constants;

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
	private int startingPrice, askingPrice, currentWorth, tierLevel, delta;
	
	public Company(String image, String name, String description, int startingPrice, int delta, int tierLevel) {
		
		this.image = image;
		this.name = name;
		this.description = description;
		this.startingPrice = startingPrice;
		askingPrice = startingPrice; //asking price = startingPrice at beginning of auction
		currentWorth = startingPrice; //the current worth starts at startingPrice
		this.delta = delta;
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
	public synchronized String updateCurrentWorth() {
		Random rand = new Random();
		int change = Math.abs(rand.nextInt(delta));
		boolean positive = rand.nextBoolean();
		boolean specialEvent = rand.nextBoolean();
		
		if(positive) {
			if(specialEvent) {
				change*=3;
				currentWorth += change;
				int index = rand.nextInt(Constants.positiveEvents.length - 1);
				return Constants.positiveEvents[index];
			}
			
			currentWorth += change;
			return null;
		}
		else {
			if(specialEvent) {
				change *= 3;
				currentWorth -= change;
				int index = rand.nextInt(Constants.negativeEvents.length - 1);
				return Constants.negativeEvents[index];
			}
			
			currentWorth -= change;
			return null;
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
}
