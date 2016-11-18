package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.mysql.jdbc.Driver;

import gameplay.Company;
import gameplay.User;

public class SQLDriver {
	
	private Connection con;
	private static final String addCompany = "INSERT INTO Companies(imagePath, companyName, description, startingPrice, tierLevel) values (?,?,?,?,?)";
	private static final String getCompany = "SELECT * FROM Companies";
	private static final String selectUser1 = "SELECT * FROM Users where username=?";
	private static final String selectUser2 = "SELECT * FROM Users where userID=?";
	private static final String addUser = "INSERT INTO Users(username, passcode, biography) values(?,?,?)";
	private static final String updateRecords = "UPDATE Users SET gamesPlayed=?, gamesWon=?, totalProfit=? WHERE username=?";
	private static final String updateInfo = "UPDATE Users Set username=?, biography=? WHERE userID=?";
	
	public SQLDriver() {
		try {
			new Driver();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void connect() {
		try {
			String url = "jdbc:mysql://mydbinstance.csuhgm0eeh0r.us-west-2.rds.amazonaws.com:3306/";
			String userName = "user";
			String password = "rootroot";
			String dbName  = "Venture";
			con = DriverManager.getConnection(url + dbName, userName, password);
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			con.close();
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	// Inserts companies into the DB. Only used by Company Filler.
	public void insertCompany(String imagePath, String companyName, String description, double startingPrice, int tier) {
		try {
			PreparedStatement ps = con.prepareStatement(addCompany);
			ps.setString(1, imagePath);
			ps.setString(2, companyName);
			ps.setString(3, description);
			ps.setDouble(4, startingPrice);
			ps.setInt(5, tier);
			ps.executeUpdate();
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	// Returns a vector of all the companies
	public Vector<Company> getCompanies() {
		Vector<Company> companies = new Vector<Company>();
		Statement st = null;
		ResultSet rs = null;
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(getCompany);
			while (rs.next()) {
				String imagePath = rs.getString("imagePath");
				String companyName = rs.getString("companyName");
				String description = rs.getString("description");
				double starting = rs.getDouble("startingPrice");
				int tier = rs.getInt("tierLevel");
				Company tempComp = new Company(imagePath, companyName, description, starting, tier);
				companies.add(tempComp);
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}		
		return companies;
	}
	
	// Returns boolean of whether or not username already exists
	public boolean userExists(String username) {
		try {
			PreparedStatement ps = con.prepareStatement(selectUser1);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				return true;
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		return false;
	}
	
	// Checks to see if the password matches for that user
	public boolean checkPassword(String username, String password) {
		try {
			PreparedStatement ps = con.prepareStatement(selectUser1);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				if (password.equals(result.getString(3))) {
					return true;
				}
				return false;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return false;
	}
	
	// Inserts user into the DB
	public void insertUser(String username, String password, String biography) {
		try {
			PreparedStatement ps = con.prepareStatement(addUser);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, biography);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	// Updates the user's number of games played, won, and total profit
	public void updateUserRecords(String username, int gamesPlayed, int gamesWon, double totalProfit) {
		try {
			PreparedStatement ps = con.prepareStatement(updateRecords);
			ps.setInt(1, gamesPlayed);
			ps.setInt(gamesWon, 2);
			ps.setDouble(3, totalProfit);
			ps.setString(4, username);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	// Used to update the user's username or biography
	public void updateUserInfo(int userID, String username, String biography) {
		try {
			PreparedStatement ps = con.prepareStatement(updateInfo);
			ps.setString(1, username);
			ps.setString(2, biography);
			ps.setInt(3, userID);
			
			ps.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	// Overloaded method of getting user by username
	public User getUser(String username) {
		User user = null;
		try {
			PreparedStatement ps = con.prepareStatement(selectUser1);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int userID = rs.getInt("userID");
				String biography = rs.getString("biography");
				String password = rs.getString("passcode");
				int gamesPlayed = rs.getInt("gamesPlayed");
				int gamesWon = rs.getInt("gamesWon");
				double totalProfit = rs.getDouble("totalProfit");
				System.out.println(userID);
				user = new User(userID, username, password, biography, gamesPlayed, gamesWon, totalProfit);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return user;
	}
	
	// Overloaded method of getting user by userID.
	public User getUser(int userID) {
		User user = null;
		try {
			PreparedStatement ps = con.prepareStatement(selectUser2);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String username = rs.getString("username");
				String biography = rs.getString("biography");
				String password = rs.getString("passcode");
				int gamesPlayed = rs.getInt("gamesPlayed");
				int gamesWon = rs.getInt("gamesWon");
				double totalProfit = rs.getDouble("totalProfit");
				System.out.println(userID);
				user = new User(userID, username, password, biography, gamesPlayed, gamesWon, totalProfit);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return user;
	}
}
