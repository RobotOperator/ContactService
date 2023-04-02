package ContactProgram;
import java.util.*;

public class Driver {

	public static void main(String[] args) {
		// Create the ContactService instance to hold contacts
		ContactService MainService = new ContactService();
		
		//Create input scanner
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\r\n");
		
		// User selection integer value, default 0 to start loop
		int selection = 0;
		
		// User interface printed selections
		while (selection != 9) {
			//TODO: SQLite check for database to load or print message if no database found and create one.
			System.out.println("Welcome to the Contact Service!");
			System.out.println("Please make your selection from the below options.");
			System.out.println("1 - View stored contacts");
			System.out.println("2 - Add Contact");
			System.out.println("3 - Update Contact");
			System.out.println("4 - Delete Contact");
			System.out.println("9 - Exit Service");
			System.out.print("Choice >");
			try {
			    selection = sc.nextInt(); //ensure valid integer is provided
			    if (selection <= 0 || selection > 4 && selection != 9) {
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
			  } 
			}
			// handle any non-specific exceptions
			catch (Exception e) {
				System.out.println(e);
				sc.nextLine();
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
		System.out.print("Enter Id of contact to update > ");
		String targetId = in.next();
		System.out.print("Enter the field to be updated > ");
		String targetField = in.next();
		System.out.print("Enter new value > ");
		String newValue;
		if (targetField.toLowerCase().equals("address")) {
			newValue = in.next() + in.nextLine();
		}
		else {
			newValue = in.next();
		}
		try {
		updateOne.updateContact(targetId, targetField.toLowerCase(), newValue); //cast target field to lower to make it easier for users to update without knowing exact capitalization
		}
		catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}

}
