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
		//Prepare statement string 
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
			throw new IllegalStateException("--Failed to insert contact into database--");
		}
	}
	
	//Remove contact from the database table by specified id
	public void deleteContact(String id) {
		// Prepare statement
		String sql = "DELETE FROM contacts WHERE id = ?";
		
		//Assign parameter to value for prepared statement
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			pstmt.executeUpdate(); //execute the DELETE
			pstmt.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new IllegalStateException("--Failed to delete contact from database--");
		}
	}
	
	//Method to update a contact within the database
	public void updateContact(String id, String field, String value) {
		//Prepare statement
		String sql = "UPDATE contacts SET " + field
				+ " = ? "
				+ "WHERE id = ?";
		
		//Assign values to the prepared statement
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql) ) {
			pstmt.setString(1, value);
			pstmt.setString(2, id);
			pstmt.executeUpdate(); //Execute the update query
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new IllegalStateException("--Failed to update contact in database--");
		}
	}
	
	//Method to load contacts from the database
	public void retrieveContacts(ArrayList<Contact> databaseContacts, ArrayList<String> dbIds) {
		//Prepare SQL query
		String sql = "SELECT * FROM contacts";
		
		//Load contacts stored in the database as new contact objects after executing select query
		try (Statement stmt = this.conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			//loop through the result set
			while (rs.next()) {
				Contact newone = new Contact(rs.getString("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("phone_number"), rs.getString("address"));
				dbIds.add(rs.getString("id"));
				databaseContacts.add(newone);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new IllegalStateException("Contacts could not be loaded from database");
		}
	}
	
	//Method to create the Contact table in the database
	public void createContactTable() {
		//create the contacts table
		String sql = "CREATE TABLE IF NOT EXISTS contacts (\n"
				+ "    id text PRIMARY KEY,\n"
				+ "    first_name text NOT NULL, \n"
				+ "    last_name text NOT NULL, \n"
				+ "    phone_number text NOT NULL, \n"
				+ "    address text NOT NULL\n"
				+ ");";
		
		//Try to execute the statement creating the table, handle error if it fails
		try ( Statement stmt = this.conn.createStatement()) {
			stmt.execute(sql);
			stmt.close();
		}
		catch ( SQLException e ) {
			System.out.println(e.getMessage());
			throw new IllegalStateException("--Failed to create Contact table in the database--");
		}	
	}
	
	//Method to delete all contacts from the database
	public void deleteAllContacts() {
		String sql = "DELETE FROM contacts";
		
		try ( Statement stmt = this.conn.createStatement()) {
			stmt.execute(sql);
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new IllegalStateException("--Failed to delete contacts in the database--");
		}
	}
	
	//Method to close connection to database
	public void closeConnection() {
		try {
			this.conn.close();
		}
		catch (SQLException e) {
			throw new IllegalStateException("--Database connection could not be closed as needed--");
		}
	}
}
