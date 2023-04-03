package ContactProgram;
import java.util.*;

/**
 * 
 * @author robot
 *
 */

public class Driver {

	public static void main(String[] args) {

		//Create input scanner
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\r\n");
		
		//User supplies encryption key
		System.out.println("Enter database key, or new key if starting with a blank database. (key must be 16 characters or greater)");
		System.out.print(">");
		String key =sc.next();
		
		while (key.length() < 16) {
			System.out.println("Enter a longer key.");
			System.out.print(">");
			key = sc.next();
		}
		System.out.println("+Ensure you save this key to be able to decrypt the database contacts in the future.+");
		System.out.println();
		
		// User selection integer value, default 0 to start input loop
		int selection = 0;
		
		//Variable to contain instance of ContactService
		ContactService MainService = null;
				
		// Create the ContactService instance to hold contacts
		try {
			 MainService = new ContactService(key);
		}
		catch (IllegalArgumentException e) {
			System.out.println("Invalid key supplied and the database contacts could not be decrypted.");
			sc.close();
			System.out.println("Exit.");
			System.exit(0);//service will exit and not proceed.
		}
	
		// User interface printed selections
		while (selection != 9) {
			//Present menu of options
			System.out.println();
			System.out.println("Welcome to the Contact Service!");
			System.out.println("Please make your selection from the below options.");
			System.out.println("1 - View stored contacts");
			System.out.println("2 - Add Contact");
			System.out.println("3 - Update Contact");
			System.out.println("4 - Delete Contact");
			System.out.println("5 - Search for Contact");
			System.out.println("6 - Delete All Contacts");
			System.out.println("9 - Exit Service");
			System.out.print("Choice >");
			try {
			    selection = sc.nextInt(); //ensure valid integer is provided
			    if (selection <= 0 || selection > 6 && selection != 9) {
			    	throw new InputMismatchException("Out of range");
			    }
			}
			catch (InputMismatchException e) {
				System.out.println("Invalid choice");
				selection = 0;
				sc.nextLine();
			}
			
			// perform actions based on supplied choice
			try {
			  switch (selection) {
			  case 1:
				  viewContacts(MainService, sc);
				  break;
			  case 2:
				  addContact(MainService, sc);
				  break;
			  case 3:
				  updateContact(MainService, sc);
				  break;
			  case 4:
				  deleteContact(MainService, sc);
				  break;
			  case 5:
				  searchForContact(MainService, sc);
				  break;
			  case 6:
				  deleteAllContacts(MainService, sc);
				  break;
			  } 
			}
			// handle any non-specific exceptions
			catch (Exception e) {
				System.out.println(e);
				selection = 9;
			}		
		}
		//close our scanner to release the resource 
		sc.close();
	}
	
	//Function to print out all existing contacts
	private static void viewContacts(ContactService toIterate, Scanner in) {
		ArrayList<Contact> iterableList = toIterate.getContacts();
		System.out.println();
		if (iterableList.size() < 1) {
			System.out.println("None."); //If no contacts are returned tell the user
			System.out.println();
		}
		else {
		    for (int i = 0; i < iterableList.size(); i++) {
			    Contact temp = iterableList.get(i);
			    System.out.println("Contact Id: " + temp.getid());
			    System.out.println("FirstName : " + temp.getFirstName());
			    System.out.println("LastName : " + temp.getLastName());
			    System.out.println("Phone : " + temp.getNumber());
			    System.out.println("Address : " + temp.getAddress());
			    System.out.println();
			    System.out.println();
		    }
		}
			System.out.println("Press enter to continue.");		
			in.next(); // wait for user to press enter
			in.nextLine(); // dispose of any submitted data since none was requested
	}
	
	//Function to query for information to be added to a new contact
	private static void addContact(ContactService addTo, Scanner in) {
		System.out.print("Enter new Contact Id >");
		String newId = in.next();
		System.out.print("Enter new Contact First Name >");
		String newFName = in.next();
		System.out.print("Enter new Contact Last Name >");
		String newLName = in.next();
		System.out.print("Enter new Contact Phone Number (exp 1234567890) >");
		String newPhone = in.next();
		System.out.print("Enter new Contact Address >");
		String newAddress = in.next() + in.nextLine(); //Account for spaces in address by capturing first element in input and then scanning to next line
		try {
		    addTo.addContact(newId, newFName, newLName, newPhone, newAddress);
		    System.out.println("\n++Success++\n");
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}
		System.out.println();
	}
	
	//Function to delete contact specified by Id
	private static void deleteContact(ContactService deleteFrom, Scanner in) {
		System.out.print("Enter Id to be deleted > ");
		String deleteId = in.next();
		try {
			deleteFrom.deleteContact(deleteId);
			System.out.println();
			System.out.println("Deleted");
			System.out.println();
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}
		finally {
		    in.nextLine();
		}
	}
	
	//Function to update contact by Id
	private static void updateContact(ContactService updateOne, Scanner in) {
		//Capture update options
		System.out.print("Enter Id of contact to update > ");
		String targetId = in.next();
		
		//Print out list of fields that can be updated
		System.out.println("Field Selection: \n\tFirstName \n\tLastName \n\tPhoneNumber \n\tAddress");
		System.out.print("Enter the field to be updated > ");
		String targetField = in.next();
		
		//Accept new value
		System.out.print("Enter new value > ");
		String newValue;
		
		//Capture input for addresses that contain spaces
		if (targetField.toLowerCase().equals("address")) {
			newValue = in.next() + in.nextLine();
		}
		else {
			newValue = in.next();
		}
		try {
		    updateOne.updateContact(targetId, targetField.toLowerCase(), newValue); //cast target field to lower to make it easier for users to update without knowing exact capitalization
		    System.out.println("\n++Updated++\n");
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}
	
	//Function to search for a specific contact
	private static void searchForContact(ContactService searchService, Scanner in) {
		//Retrieve ID
		System.out.print("Enter Id of contact to find > ");
		String targetId = in.next();
		//Retrieve Contact from service
		try {
			Contact targetContact = searchService.findContact(targetId);
			System.out.println();
		    System.out.println("Contact Id: " + targetContact.getid());
		    System.out.println("FirstName : " + targetContact.getFirstName());
		    System.out.println("LastName : " + targetContact.getLastName());
		    System.out.println("Phone : " + targetContact.getNumber());
		    System.out.println("Address : " + targetContact.getAddress());
		    System.out.println();
		}
		//If illegal argument is thrown print out the message
		catch (IllegalArgumentException e) {
			System.out.println();
			System.out.println(e.getMessage());
			System.out.println();
		}
		
	}
	
	//Function to delete all contacts in database and memory
	private static void deleteAllContacts(ContactService deleteService, Scanner in) {
		 System.out.println("!!WARNING!! This will delete all stored contacts.");
		 System.out.println("Are you sure you wish to proceed? Enter (y) for yes and (n) for no");
		 System.out.print(">");
		 String confirm = in.next();
		 if (confirm.toLowerCase().equals("y")) {
		  try {
			  deleteService.deleteContacts();
			  System.out.println("Contacts deleted.");
		  }
		  //Failed to delete database, inform the user to delete the database file
		  catch (Exception e) {
			  System.out.println(e.getMessage());
			  //Throw error that will cause the service to exit, database will be rebuilt on restart
			  throw new IllegalStateException("--Database could not be deleted please restart the service--");
		  }			  
		}
		else {
			  System.out.println("Deletion aborted.");
		}
	}

}
