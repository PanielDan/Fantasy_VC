package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

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
				driver.insertCompany(imagePath, companyName, description, Integer.parseInt(startingPrice), Integer.parseInt(tier));
			}
			
			file.close();
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			driver.stop();
		}
	}
}


