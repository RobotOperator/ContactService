package ContactProgram;

import java.io.File;
import java.util.ArrayList;

public class ContactService {
	
	//Create new array lists to hold the contacts created and unique ID fields.
	private ArrayList<Contact> ContactList;
	private ArrayList<String> Ids;
	private dbConnector db;
	private String dbFile;
	private String dbDirectory;
	private String databaseKey;
	
	//Default constructor with blank array lists. Requires a key to access encrypted content in the database.
	public ContactService(String dbKey) {
		this.databaseKey = dbKey;
		//Statically configured path to the database file
		dbDirectory = "";
		dbFile = "contact.db";
		File dbPath = new File(dbDirectory + dbFile);
		ContactList = new ArrayList<Contact>();
		Ids = new ArrayList<String>();
		//Test if the database exists already
		boolean fileExists = dbPath.exists();
		//Create a new database connection
		db = new dbConnector(dbDirectory, dbFile);
		//If the file didn't exist then create the table to hold contacts else load contacts from the database
		if (fileExists) {
			try {
			    db.retrieveContacts(ContactList, Ids, dbKey);
			    System.out.println("++Loaded " + Ids.size() + " contacts from the database++");
			}
			catch (IllegalStateException e) {
				System.out.println();
				System.out.println(e.getMessage());
				System.out.println("New contacts can be added. Existing contacts will not show in the service.");
			}
		}
		else {
			db.createContactTable();
		}
	}
	
	//Method to add a new contact, checks if a valid unique id has been provided. Class will throw an error if the arguments are bad.
	public void addContact(String id, String firstName, String lastName, String phoneNumber, String address) {
		if (Ids.contains(id) || (id == null)) {
			throw new IllegalArgumentException("The id given is not unique and is in use for a contact, or is null.");
		}
		else {
		Contact newone = new Contact(id, firstName, lastName, phoneNumber, address);
		Ids.add(id);
		ContactList.add(newone);
		this.db.insertContact(id, firstName, lastName, phoneNumber, address, this.databaseKey);
		}
	}
	
	//Method to delete an existing contact object and the associated ID in the list of IDs. Throws an error if the id doesn't exist.
	public void deleteContact(String id) {
		if (Ids.contains(id)) {
		ContactList.remove(Ids.indexOf(id));
		Ids.remove(id);
		this.db.deleteContact(id);
		}
		else {
			throw new IllegalArgumentException("The id given does not exist as a contact.");
		}
	}
	
	//Method to update contact info. for a unique contact id, throws an error if the id doesn't exist, field doesn't exist, or class will throw an error if the value is bad.
	public void updateContact(String id, String field, String value) {
		if (Ids.contains(id) && field != null) {
			switch (field.toLowerCase()) {
			    case "firstname": ContactList.get(Ids.indexOf(id)).setFirstName(value);
			    this.db.updateContact(id, "first_name", value, this.databaseKey);
			    break;
			    case "lastname": ContactList.get(Ids.indexOf(id)).setLastName(value);
			    this.db.updateContact(id, "last_name", value, this.databaseKey);
			    break;
			    case "phonenumber": ContactList.get(Ids.indexOf(id)).setNumber(value);
			    this.db.updateContact(id, "phone_number", value, this.databaseKey);
			    break;
			    case "address": ContactList.get(Ids.indexOf(id)).setAddress(value);
			    this.db.updateContact(id, "address", value, this.databaseKey);
			    break;
			    default: throw new IllegalArgumentException("The field specified is not recognized. Fields are: FirstName, LastName, PhoneNumber, Address.");
			}
		}
		else {
			throw new IllegalArgumentException("The id given does not exist as a contact or the field is null.");
		}
	}
	
	//Method to retrieve contacts stored in the service
	public ArrayList<Contact> getContacts() {
		return ContactList;
	}
	
	//Method to retrieve an instance of a single Contact
	public Contact findContact(String id) {
		int index = Ids.indexOf(id);
		//If index is of id is not found
		if (index < 0 ) {
			throw new IllegalArgumentException("X--Contact not found--X");
		}
		else {
			return ContactList.get(Ids.indexOf(id));
		}
	}
	
	//Method to delete all contacts stored in the list and database
	public void deleteContacts() {
		try {
		    this.db.deleteAllContacts();
			Ids.clear();
			ContactList.clear();
		}
		//If deleting contacts fails via SQL query, delete the database file and recreate it with the contacts table
		catch (IllegalStateException e) {
			this.db.closeConnection();
			this.db = null;
			File deleteObj = new File(dbDirectory + dbFile);
			deleteObj.delete();
			this.db = new dbConnector(dbDirectory, dbFile);
			this.db.createContactTable();
		}
	}
}
