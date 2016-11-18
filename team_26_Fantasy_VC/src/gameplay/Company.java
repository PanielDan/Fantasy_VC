
package gameplay;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

import utility.Constants;
import utility.ImageLibrary;

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
	private double startingPrice, askingPrice, currentWorth;
	private int delta, tierLevel;
	private Image companyLogo;
	private double imageAspectRatio;
	
	public Company(String image, String name, String description, double startingPrice, int delta, int tierLevel) {
		this.image = image;
		this.name = name;
		this.description = description;
		this.startingPrice = startingPrice;
		askingPrice = startingPrice; //asking price = startingPrice at beginning of auction
		currentWorth = startingPrice; //the current worth starts at startingPrice
		this.delta = delta;
		this.tierLevel = tierLevel;
		createIcon();
	}
	


	public Company(String imagePath, String companyName, String description, double startingPrice, int tierLevel) {
		this.image = imagePath;
		this.name = companyName;
		this.description = description;
		this.startingPrice = startingPrice;
		this.tierLevel = tierLevel;
		askingPrice = startingPrice; //asking price = startingPrice at beginning of auction
		currentWorth = startingPrice; //the current worth starts at startingPrice
		createIcon();

		
		switch (tierLevel) { 
			case 0:
				this.delta = 100;
				break;
			case 1:
				this.delta = 200;
				break;
			case 2:
				this.delta = 350;
				break;
			case 3:
				this.delta = 500;
				break;
			case 4:
				this.delta = 750;
				break;
		}
	}



	/* Getters. */
	public String getName() {
		return name;
	}
	
	public String getImage() {
		return image;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getStartingPrice() {
		return startingPrice;
	}
	
	public double getAskingPrice() {
		return askingPrice;
	}
	
	public double getCurrentWorth() {
		return currentWorth;
	}
	
	public int getTierLevel() {
		return tierLevel;
	}
	
	public Image getCompanyLogo(){
		return companyLogo;
	}
	
	
	/**
	 * 
	 * @return A {@code String} of text that we can add to the notifications
	 * box on every {@code Client}'s {@code TimelapseGUI}.  A random
	 * outcome will determine if a special event occurs.  If there is 
	 * no special event, the returned text will be an empty {@code String}.
	 * Otherwise it will describe a good or bad event, also determined
	 * by random outcome.
	 */
	public synchronized String updateCurrentWorth() {
		Random rand = new Random();
		int change = Math.abs(rand.nextInt(delta));
		boolean positive = rand.nextBoolean();
		boolean specialEvent = rand.nextBoolean();
		String text = "";

		// First we check if a special event has occurred. 
		if (specialEvent) { 
			// We will either boost or decrease this turn's delta by
			// a factor of between 3 and 6.
			int modifier = Math.abs(rand.nextInt(3)) + 3;
			int index;
			if (positive) { 
				change *= modifier;  // Boost the change by the modifier.
				index = rand.nextInt(Constants.positiveEvents.length - 1); // Randomly pick an event text.
				text = Constants.positiveEvents[index];  // Grab the event text.
			} else {
				change *= -modifier;
				index = rand.nextInt(Constants.negativeEvents.length - 1);
				text = Constants.negativeEvents[index];
			}
		} else {
			// If no special event has occurred, we just modify the 
			// change based on whether positive came out true or not.
			change = positive ? change : -change;
		}
		
		// At the end we just add the change to the currentWorth,
		// and we return the text.  
		currentWorth += change;
		return text;
	}
	
	public void updateAskingPrice(int askingPrice) {
		this.askingPrice = askingPrice;
	}
	
	
	/* Setters. */
	public void setName(String name) {
		this.name = name;
	}
	
	public void setImage(String image) {
		this.image = image;
		createIcon();
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
	
	public double getAspectRatio(){
		return imageAspectRatio;
	}
	
	private void createIcon(){
		companyLogo = ImageLibrary.getImage(image);
		imageAspectRatio = (double)companyLogo.getWidth(null) / companyLogo.getHeight(null);
	}
}
