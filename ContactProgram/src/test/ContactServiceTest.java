package test;

//import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ContactProgram.ContactService;

class ContactServiceTest {

	//Testing contact service to add new contact classes and errors when a duplicate ID is submitted as well as bad arguments.
	@Test
	void testContactServiceAddClass() {
		ContactService temporary = new ContactService("tmpkey");
		temporary.addContact("123", "bob", "david", "2033442509", "123 Mulberry Street");
		temporary.addContact("234", "darron", "andrews", "5193442509", "543 Mulberry Street");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			temporary.addContact("123", "bobby", "davey", "3033442509", "322 Mulberry Street");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			temporary.addContact(null, "bobby", "davey", "3033442509", "322 Mulberry Street");
		});
	}
	
	//Deletes newly created contact, throws error on attempt to delete non-existent contact verifying it was in fact removed.
	@Test
	void testContactServiceDeleteContact() {
		ContactService temporary = new ContactService("tmpkey");
		temporary.addContact("123", "bob", "david", "2033442509", "123 Mulberry Street");
		temporary.deleteContact("123");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			temporary.deleteContact("123");
		});
	}
	
	//Tests updating the various attributes of a contact after it is created and verifies errors are thrown correctly for bad arguments.
	@Test
	void testContactServiceUpdateContact() {
		ContactService temporary = new ContactService("tmpkey");
		temporary.addContact("54", "Jared", "Leto", "3448675309", "123 West Philadelphia");
		temporary.updateContact("54", "FirstName", "Jarrod");
		temporary.updateContact("54", "LastName", "Beck");
		temporary.updateContact("54", "PhoneNumber", "5555555555");
		temporary.updateContact("54", "Address", "25 Hot Patch Road");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			temporary.updateContact("2", "temp", "temp");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			temporary.updateContact(null, "temp", "temp");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			temporary.updateContact("54", "temp", "temp");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			temporary.updateContact("54", "FirstName", null);
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			temporary.updateContact("54", null, "temp");
		});
	}
}

