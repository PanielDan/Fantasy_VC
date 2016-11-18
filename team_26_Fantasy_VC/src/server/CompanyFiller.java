package server;

import gameplay.User;

public class CompanyFiller {
	
	public static void main(String [] args) {
		SQLDriver driver = new SQLDriver();
		driver.connect();
//		try {
//			FileReader file = new FileReader("resources/PopulateCompanies");
//			Scanner scanner = new Scanner(file);
//			
//			scanner.nextLine(); // Clear the description line
//			
//			while(scanner.hasNextLine()) {
//				StringTokenizer st = new StringTokenizer(scanner.nextLine(), "||");
//				String companyName = st.nextToken();
//				String imagePath = st.nextToken();
//				String description = st.nextToken();
//				String startingPrice = st.nextToken();
//				String tier = st.nextToken();
//				driver.insertCompany(imagePath, companyName, description, Integer.parseInt(startingPrice), Integer.parseInt(tier));
//			}
//			
//			file.close();
//			scanner.close();
//			
//			driver.getCompanies();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			driver.stop();
//		}
//		
//		Vector<Company> companies = driver.getCompanies();
//		for(Company c : companies) {
//			System.out.println(c.getName());
//		}
//		
//		driver.insertUser("Timmy Lewd", "passcode", "intern");
//		System.out.println(driver.userExists("Timmy Lewd"));
//		System.out.println(driver.checkPassword("Timmy Lewd", "passcode"));
		
		driver.updateInfo("Timmy Lewd", 1, 2, 4);
		
		User user = driver.getUser("Timmy Lewd");
		System.out.println(user.getUserBio());
		
		driver.stop();
	}
}


