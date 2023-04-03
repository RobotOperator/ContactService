package test;

//import static org.junit.jupiter.api.Assertions.*;
import ContactProgram.dbConnector;
import ContactProgram.Contact;
import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class dbConnectorTest {
	
	//Internal function to establish a new database for testing
	dbConnector createTestDB() {
		dbConnector temporaryDB = new dbConnector("", "temp.db");
		temporaryDB.createContactTable();
		return temporaryDB;
	}
	
	//Internal function to delete the database file at the end of testing
	void cleanUp(dbConnector cleanDB) {
		cleanDB.closeConnection();
		File delDB = new File("temp.db");
		delDB.delete();
	}

	//Testing dbConnector to create the back-end database
	@Test
	void databaseCreation() {
		dbConnector temporaryDB = new dbConnector("", "temp.db");
		temporaryDB.createContactTable();
		temporaryDB.closeConnection();
		File delDB = new File("temp.db");
		delDB.delete();
	}
	
	//Testing dbConnector to add a new contact and error when SQL insertion fails
	@Test
	void contactAdd() {
		dbConnector tempDB = createTestDB();
		tempDB.insertContact("1", "Chris", "Coons", "1234567890","123 Address Lane");
		
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//Inserting non-unique key of 1
			tempDB.insertContact("1", "Chris", "Coons", "1234567890", "123Address Lane");
		});
		
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//Close connection to for SQL error, should be caught and throw IllegalStateException
			tempDB.closeConnection();
			tempDB.insertContact("1", "Chris", "Coons", "1234567890", "123Address Lane");
		});
		cleanUp(tempDB);
	}
	
	//Testing dbConnector to retrieve contacts and error when SQL retrieval fails
	@Test
	void contactGet() {
		ArrayList<Contact> tempContacts = new ArrayList<Contact>();
		ArrayList<String> tempIds = new ArrayList<String>();
 		dbConnector tempDB = createTestDB();
		tempDB.insertContact("1", "Chris", "Coons", "1234567890","123 Address Lane");
		tempDB.retrieveContacts(tempContacts, tempIds);
		//Ensure 1 Contact was loaded
		Assertions.assertEquals(tempIds.size(), 1);
		Assertions.assertEquals(tempContacts.size(), 1);
		
		Assertions.assertThrows(IllegalStateException.class, () -> {
			tempDB.closeConnection();
			tempDB.retrieveContacts(tempContacts, tempIds);
		});
		cleanUp(tempDB);
	}
	
	
	//Testing dbConnector to delete contact and error when SQL query fails
	@Test
	void contactDelete() {
		ArrayList<Contact> tempContacts = new ArrayList<Contact>();
		ArrayList<String> tempIds = new ArrayList<String>();
 		dbConnector tempDB = createTestDB();
		tempDB.insertContact("1", "Chris", "Coons", "1234567890","123 Address Lane");
		tempDB.retrieveContacts(tempContacts, tempIds);
		Assertions.assertEquals(tempIds.size(), 1);
		tempDB.deleteContact("1");
		tempContacts.clear();
		tempIds.clear();
		tempDB.retrieveContacts(tempContacts, tempIds);
		Assertions.assertEquals(tempIds.size(), 0);
		
		//Should not throw error since no rows in the database would be affected
		tempDB.deleteContact("3");
	
		Assertions.assertThrows(IllegalStateException.class, () -> {
				tempDB.closeConnection();
				tempDB.deleteContact("1");
		});
		cleanUp(tempDB);
	}
	
	//Testing dbConnector to delete all contacts and error when SQL query fails
	@Test
	void allContactsDelete() {
		ArrayList<Contact> tempContacts = new ArrayList<Contact>();
		ArrayList<String> tempIds = new ArrayList<String>();
 		dbConnector tempDB = createTestDB();
		tempDB.insertContact("1", "Chris", "Coons", "1234567890","123 Address Lane");
		tempDB.insertContact("2", "Chris", "Coons", "1234567890","123 Address Lane");
		tempDB.retrieveContacts(tempContacts, tempIds);
		Assertions.assertEquals(tempIds.size(), 2);
		tempDB.deleteAllContacts();
		tempContacts.clear();
		tempIds.clear();
		tempDB.retrieveContacts(tempContacts, tempIds);
		Assertions.assertEquals(tempIds.size(), 0);
	
		Assertions.assertThrows(IllegalStateException.class, () -> {
				tempDB.closeConnection();
				tempDB.deleteContact("1");
		});
		cleanUp(tempDB);
	}
	
	//Testing dbConnector to updates contacts and error when SQL update fails
	@Test
	void contactUpdate() {
		ArrayList<Contact> tempContacts = new ArrayList<Contact>();
		ArrayList<String> tempIds = new ArrayList<String>();
 		dbConnector tempDB = createTestDB();
		tempDB.insertContact("1", "Chris", "Coons", "1234567890","123 Address Lane");
		tempDB.updateContact("1", "first_name", "Carl");
		tempDB.retrieveContacts(tempContacts, tempIds);
		
		Assertions.assertEquals(tempContacts.get(0).getFirstName(), "Carl");
		
		Assertions.assertThrows(IllegalStateException.class, () -> {
			tempDB.updateContact("1", "wrong", "value");
		});
		cleanUp(tempDB);
	}
	

}
