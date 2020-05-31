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
			
			StringBuilder resultString = new StringBuilder();
			
			for (int table = 0; table < tableNames.length; ++table) {
				ResultSet metaResult = connection.getMetaData().getColumns(null, null, tableNames[table], null);
				
				StringBuilder columns = new StringBuilder();
				while (metaResult.next()) {
					columns.append(metaResult.getString("COLUMN_NAME") + " LIKE '%XXX%' OR ");
				}
				
				columns.delete(columns.length() - 3, columns.length());
						
				Statement statement = connection.createStatement();
				String queryString = "SELECT * FROM " + tableNames[table] + " WHERE " + columns.toString().replace("XXX", searchString) + ";"; 
				ResultSet result = statement.executeQuery(queryString);
				int maxColumns = result.getMetaData().getColumnCount();
				
				
				
				while (result.next()) {
					resultString.append("<div><h2>" + result.getString(1) + "</h2>");
					
					for (int column = 2; column <= maxColumns; ++column) {
						if (!isNull(result.getString(column))) {
							resultString.append("<strong>" + result.getMetaData().getColumnName(column).replace("_", " ") + ":</strong> " 
									+ result.getString(column) + "<br>");
						}
					}
					resultString.append("<p></div>");
				}
			}
			return resultString.toString();
			
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