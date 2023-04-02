package ContactProgram;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		try {
			//this.conn = new_conn;
			this.conn = DriverManager.getConnection(url);
			System.out.println("Connected database : " + directory + filename);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage()); //print error message to console
			throw new IllegalStateException("--Failed to create DB connection--");
		}
	}
	
	public void insertContact(String id, String firstName, String lastName, String phoneNumber, String address) {
		//prepare statement string 
		String sql = "INSERT INTO contacts(id, first_name, last_name, phone_number, address) VALUES(?,?,?,?,?)";
		
		//Assign parameters to values for prepared statement
		try(PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			pstmt.setString(2, firstName);
			pstmt.setString(3, lastName);
			pstmt.setString(4, phoneNumber);
			pstmt.setString(5, address);
			pstmt.executeUpdate(); //execute the insert statement
			pstmt.close(); //close and release resources to be used in subsequent queries
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void deleteContact() {
		//stub
	}
	
	public static void updateContact() {
		//stub
	}
	
	//Method to load contacts from the database
	public void retrieveContacts() {
		//Create new ArrayList of contacts
		//ArrayList<Contact> databaseContacts = new ArrayList<Contact>();
		//Prepare SQL query
		String sql = "SELECT * FROM contacts";
		
		
		try (Statement stmt = this.conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			//loop through the result set
			while (rs.next()) {
				Contact newone = new Contact(rs.getString("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("phone_number"), rs.getString("address"));
				databaseContacts
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new IllegalStateException("Contacts could not be loaded from database");
		}
		return new ArrayList<Contact>(); //default empty array list
	}
	
	//Method to create the Contact table in the database
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
