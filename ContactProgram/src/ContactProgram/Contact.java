package ContactProgram;

/**
 * 
 * @author robot
 * The contact class performs input validation of supplied 
 * data attributes and stores individual contact information.
 * The Contact class primarily implements get and set methods for the 
 * attributes set for the instance.
 */
public class Contact {

	private final String ID; //final to make ID attribute immutable while instance exists.
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	
	//create Contact constructor that throws errors if incorrect argument values are passed. Assuming the desire for no null values also applies to empty values.
	public Contact(String id, String firstName, String lastName, String phoneNumber, String address) {
		if (id == null || id.length() > 10 || id.length() < 1) {
			throw new IllegalArgumentException("Invalid id.");
		}
		else if (firstName == null || firstName.length() > 10 || firstName.length() < 1) {
			throw new IllegalArgumentException("Invalid first name." + firstName);
		}
		else if (lastName == null || lastName.length() > 10 || lastName.length() < 1) {
			throw new IllegalArgumentException("Invalid last name." + lastName);
		}
		else if (phoneNumber == null || phoneNumber.length() != 10 || testPhone(phoneNumber)) {
			throw new IllegalArgumentException("Invalid phone number. " + phoneNumber);
		}
		else if (address == null || address.length() > 60 || address.length() < 1) {
			throw new IllegalArgumentException("Invalid address : " + address);
		}
		else {
			this.ID = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.phoneNumber = phoneNumber;
			this.address = address;
		}
	}
	
	//Function to determine if the number saved for contact is actually all numerical values.
	private Boolean testPhone(String number) {
		try {
			Long.parseLong(number);
			return false;
		}
		catch ( Exception e) {
			return true;
		}
	}
	
	//method to return the ID value.
	public String getid() {
		return ID;
	}
	
	//method to return the first name.
	public String getFirstName() {
		return firstName;
	}
	
	//method to update the first name.
	public void setFirstName(String newFirst) {
		if (newFirst == null || newFirst.length() > 10 || newFirst.length() < 1) {
			throw new IllegalArgumentException("Invalid first name." + newFirst);
		}
		else {
		firstName = newFirst;
		}
	}
	
	//method to return the last name.
	public String getLastName() {
		return lastName;
	}
	
	//method to update the last name.
	public void setLastName(String newLast) {
		if (newLast == null || newLast.length() > 10 || newLast.length() < 1) {
			throw new IllegalArgumentException("Invalid last name." + newLast);
		}
		else {
		lastName = newLast;
		}
	}
	
	//method to return the phone number.
	public String getNumber() {
		return phoneNumber;
	}
	
	//method to set a new phone number.
	public void setNumber(String newNumber) {
		if (newNumber == null || newNumber.length() != 10 || testPhone(newNumber)) {
			throw new IllegalArgumentException("Invalid phone number." + newNumber);
		}
		else {
		phoneNumber = newNumber;
		}
	}
	
	//method to return the contact address.
	public String getAddress() {
		return address;
	}
	
	//method to set a new contact address.
	public void setAddress(String newAddress) {
		if (newAddress == null || newAddress.length() > 60 || newAddress.length() < 1) {
			throw new IllegalArgumentException("Invalid address :" + newAddress);
		}
		else {
		address = newAddress;
		}
	}
}
