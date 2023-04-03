package ContactProgram;

public class dataEncrypt {
	
	public static String xorEncrypt(String inputString, String xorKey) {
		String output = "";
		
		//calculate the length of the input string and key and verify key is long enough
		while (inputString.length() > xorKey.length()) {
			xorKey = xorKey + xorKey;
		}

		//perform XOR of input against xorKey
		for (int i = 0; i < inputString.length(); i++) {
			output = output + Character.toString((char)(inputString).charAt(i) ^ xorKey.charAt(i));
		}
		
		return output;
		
	}

}
