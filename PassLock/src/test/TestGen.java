package test;

import org.junit.Test;

import crypto.PasswordGenerator;

public class TestGen {

	/*@Test
	public void testNUMBERS() {
		for (int i = 0; i < 10; i++) {
			System.out.println(PasswordGenerator.NUMBERS[i]);
		}
	}*/
	
	@Test
	public void generatePasswords() {
		for (int i = 0; i < 10; i++) {
			System.out.println(PasswordGenerator.generatePassword());
		}
	}
}
