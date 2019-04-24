package test;

import org.junit.Test;

import crypto.BCrypt;

public class TestBCrypt {

	
	@Test
	public void testOutput() {
		System.out.println(BCrypt.hashpw("password", BCrypt.gensalt()));
		System.out.println(BCrypt.hashpw("123456", BCrypt.gensalt()).length());
		System.out.println(BCrypt.hashpw("fuckingpassword", BCrypt.gensalt()).length());
		System.out.println(BCrypt.hashpw("BitchNigger", BCrypt.gensalt()).length());
	}
	
}
