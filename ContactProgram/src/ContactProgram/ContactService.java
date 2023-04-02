package ContactProgram;

import java.io.File;
import java.util.ArrayList;

public class ContactService {
	
	//Create new array lists to hold the contacts created and unique ID fields.
	private ArrayList<Contact> ContactList;
	private ArrayList<String> Ids;
	private dbConnector db;
	
	//Default constructor with blank array lists.
	public ContactService() {
		//Statically configured path to the database file
		String dbDirectory = "";
		String dbFile = "contact.db";
		File dbPath = new File(dbDirectory + dbFile);
		ContactList = new ArrayList<Contact>();
		Ids = new ArrayList<String>();
		//Test if the database exists already
		boolean fileExists = dbPath.exists();
		//Create a new database connection
		db = new dbConnector(dbDirectory, dbFile);
		//If the file didn't exist then create the table to hold contacts else load contacts from the database
		if (fileExists) {
			db.retrieveContacts(ContactList, Ids);
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
		this.db.insertContact(id, firstName, lastName, phoneNumber, address);
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
			    this.db.updateContact(id, "first_name", value);
			    break;
			    case "lastname": ContactList.get(Ids.indexOf(id)).setLastName(value);
			    this.db.updateContact(id, "last_name", value);
			    break;
			    case "phonenumber": ContactList.get(Ids.indexOf(id)).setNumber(value);
			    this.db.updateContact(id, "phone_number", value);
			    break;
			    case "address": ContactList.get(Ids.indexOf(id)).setAddress(value);
			    this.db.updateContact(id, "address", value);
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
	
	//Method to delete all contacts stored in the list and database
	public void deleteContacts() {
		this.db.deleteAllContacts();
		Ids.clear();
		ContactList.clear();
	}
}
