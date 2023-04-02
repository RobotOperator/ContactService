package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import ContactProgram.Contact;

class ContactTest {

	//Test the constructor of the contact class for correct input to return correct values.
	@Test
	void testContactClass() {
		Contact contact = new Contact("123", "bob", "david", "2033442509", "123 Mulberry Street" );
		assertTrue(contact.getid().equals("123"));
		assertTrue(contact.getFirstName().equals("bob"));
		assertTrue(contact.getLastName().equals("david"));
		assertTrue(contact.getNumber().equals("2033442509"));
		assertTrue(contact.getAddress().equals("123 Mulberry Street"));
	}
	
	//Test to ensure that incorrect constructor values throw an appropriate error.
	@Test
	void testContactClassArgs() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact(null, "bob", "david", "2033442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("", "bob", "david", "2033442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("12345678910", "bob", "david", "2033442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", null, "david", "2033442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "", "david", "2033442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bobby543210", "david", "2033442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bob", null, "2033442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bob", "", "2033442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bob", "david123450", "2033442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bob", "david", "033442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bob", "david", "20334425090", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bob", "david", null, "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bob", "david", "20a3442509", "123 Mulberry Street" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bob", "david", "2033442509", null );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bob", "david", "2033442509", "" );
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact("123", "bob", "david", "2033442509", "123 Mulberry Street Super Long over 30 characters address.123 Mulberry Street Super Long over 30 characters address.123 Mulberry Street Super Long over 30 characters address.123 Mulberry Street Super Long over 30 characters address." );
		});
	}
	
	//Test the contact class update methods to ensure new values are saved and bad values throw an error.
	@Test
	void testContactUpdateMethods() {
		Contact contact = new Contact("123", "bob", "david", "2033442509", "123 Mulberry Street" );
		contact.setFirstName("carl");
		assertTrue(contact.getFirstName().equals("carl"));
		contact.setLastName("barb");
		assertTrue(contact.getLastName().equals("barb"));
		contact.setNumber("1234567890");
		assertTrue(contact.getNumber().equals("1234567890"));
		contact.setAddress("321 Bradley Street");
		assertTrue(contact.getAddress().equals("321 Bradley Street"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setFirstName(null);
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setFirstName("");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setFirstName("SuperLongFirst");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setLastName(null);
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setLastName("");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setLastName("SuperLongLast");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setNumber(null);
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setNumber("1");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setNumber("01234567890");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setNumber("30a2442509");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setAddress(null);
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setAddress("SuperLongAddressExceedingThirtyCharactersInLength123 Mulberry Street Super Long over 30 characters address.123 Mulberry Street Super Long over 30 characters address.123 Mulberry Street Super Long over 30 characters address.");
		});
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setFirstName("");
		});
	}
}

