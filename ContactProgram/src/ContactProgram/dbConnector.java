package ContactProgram;

import java.util.ArrayList;
import java.sql.Connection;
//import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author robot
 *
 */

public class dbConnector {
	//stub class (creating SQLite database and making the calls to update or retrieve data accessible)
	
	//private variable to hold connection
	private Connection conn;
	
	//Default constructor, requires directory name and filename for connection
	public dbConnector (String directory, String filename) {
		String url = "jdbc:sqlite:" + directory + filename;
		try (Connection new_conn = DriverManager.getConnection(url)) {
			this.conn = new_conn;
			System.out.println("Connected db : " + directory + filename);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage()); //print error message to console
			throw new IllegalStateException("--Failed to create DB connection--");
		}
	}
	
	public static void insertContact() {
		//stub
	}
	
	public static void deleteContact() {
		//stub
	}
	
	public static void updateContact() {
		//stub
	}
	
	public ArrayList<Contact> retrieveContacts() {
		//stub
		return new ArrayList<Contact>(); //default empty array list
	}
	
	//Method to create the Contact table in the db
	public void createContactTable() {
		//pass the connection in 
		Connection connected = this.conn;
		//create the contacts table
		String sql = "CREATE TABLE IF NOT EXISTS contacts (\n"
				+ "    id text PRIMARY KEY,\n"
				+ "    first_name text NOT NULL, \n"
				+ "    last_name text NOT NULL, \n"
				+ "    phone_number text NOT NULL, \n"
				+ "    address text NOT NULL\n"
				+ ");";
		
		//Try to execute the statement creating the table, handle error if it fails
		try ( Statement stmt = connected.createStatement()) {
			stmt.execute(sql);
		}
		catch ( SQLException e ) {
			System.out.println(e.getMessage());
			throw new IllegalStateException("--Failed to create Contact table--");
		}
		
	}
	

}
