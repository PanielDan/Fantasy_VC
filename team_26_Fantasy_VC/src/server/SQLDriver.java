package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public class SQLDriver {
	
	private Connection con;
	private static final String addCompany = "INSERT INTO Companies(imagePath, companyName, description, startingPrice, tierLevel) values (?,?,?,?,?)";
	
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
	
	public void insertCompany(String imagePath, String companyName, String description, int startingPrice, int tier) {
		try {
			PreparedStatement ps = con.prepareStatement(addCompany);
			ps.setString(1, imagePath);
			ps.setString(2, companyName);
			ps.setString(3, description);
			ps.setInt(4, startingPrice);
			ps.setInt(5, tier);
			ps.executeUpdate();
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
