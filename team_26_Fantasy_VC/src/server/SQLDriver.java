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

public class SQLDriver {
	
	private Connection con;
	private static final String addCompany = "INSERT INTO Companies(imagePath, companyName, description, startingPrice, tierLevel) values (?,?,?,?,?)";
	private static final String getCompany = "SELECT * FROM Companies";
	private static final String selectUser = "SELECT * FROM Users where username=?";
	private static final String addUser = "INSERT INTO Users(username, passcode, biography) values(?,?,?)";
	
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
			PreparedStatement ps = con.prepareStatement(selectUser);
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
			PreparedStatement ps = con.prepareStatement(selectUser);
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
}
