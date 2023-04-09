package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ContactProgram.dataEncrypt;

/**
 * @author robot
 * The dataEncryptTest performs simple testing to verify that
 * XOR encryption occurs and the resulting string is different from 
 * the original string supplied to the function as well as the original
 * string can be recovered.
 */

class dataEncryptTest {
	
	
    //Test to ensure supplied data is output as XOR encrypted and returned as orignal string
	@Test
	void encryptTest() {
		String input = "This string";
		String output = dataEncrypt.xorEncrypt(input, "xorkey1234567890kdkd");
		System.out.println(output);
		assertNotEquals(input, output);
		assertEquals(input, dataEncrypt.xorEncrypt(output, "xorkey1234567890kdkd"));
	}

}
