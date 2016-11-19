package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import gameplay.Company;
import gameplay.User;

public class CompanyFiller {
	
	public static void main(String [] args) {
		SQLDriver driver = new SQLDriver();
		driver.connect();
		try {
			FileReader file = new FileReader("resources/PopulateCompanies");
			Scanner scanner = new Scanner(file);
			
			scanner.nextLine(); // Clear the description line
			
			while(scanner.hasNextLine()) {
				StringTokenizer st = new StringTokenizer(scanner.nextLine(), "||");
				String companyName = st.nextToken();
				String imagePath = st.nextToken();
				String description = st.nextToken();
				String startingPrice = st.nextToken();
				String tier = st.nextToken();
				driver.insertCompany(imagePath, companyName, description, Double.parseDouble(startingPrice), Integer.parseInt(tier));
			}
			
			file.close();
			scanner.close();
			
			driver.getCompanies();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//driver.stop();
		}
		
		Vector<Company> companies = driver.getCompanies();
		for(Company c : companies) {
			System.out.println(c.getName());
		}
		
		driver.insertUser("Timmy Lewd", "passcode", "intern");
		System.out.println(driver.userExists("Timmy Lewd"));
		System.out.println(driver.checkPassword("Timmy Lewd", "passcode"));
		
		
		User user = driver.getUser("Timmy Lewd");
		
		driver.updateUserInfo(user.getID(), "New Name", "new biography");
		
		user = driver.getUser("New Name");
		System.out.println(user.getUserBio());
		
		driver.stop();
	}
}


