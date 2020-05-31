package grit.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	static Connection connection = null;
	static String tableNames[] = {"bearbase", "ikea_names", "masterscplist"};

	
	static boolean connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("All good");
		} catch (Exception ex) {
			System.out.println("Exception Driver: " + ex);
			return false;
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dumble?serverTimezoneUTC", "root", "");
			return true;
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
			return false;
		}
	}
	
	static String searchDB(String searchString) {
		try {
			
			
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM masterscplist WHERE Name LIKE '%" + searchString + "%';");
			int maxColumns = result.getMetaData().getColumnCount();
			
			StringBuilder resultString = new StringBuilder();
			
			while (result.next()) {
				for (int column = 1; column < maxColumns; ++column) {
					if (!isNull(result.getString(column))) {
						System.out.print(result.getString(column));
					}
				}				
			}
			
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			
		};
		
		return "";
	}
	
	static private boolean isNull(String str) {
		if (str == null)
			return true;
		boolean ret=str.equals("null") || str.equals("[N/A]"); 
		return ret;
	}
}