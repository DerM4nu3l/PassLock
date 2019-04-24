package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import crypto.DeEnCryption;
/**
 * 
 * @author Manuel Mueller
 *
 */
public class TestCryptography {
	
	
	@Test
	public void testPasswordDeAndEncryption1() {
		String password = "mypassword";
		String name = "Youtube";
		String masterPassword = "mymasterpassword";

		byte[] cipher = DeEnCryption.encrypt(password, masterPassword, name);
		assertEquals(password, DeEnCryption.decrypt(cipher, masterPassword, name));
	}

	@Test
	public void testPasswordDeAndEncryption2() {
		String password = "123456";
		String name = "Facebook";
		String masterPassword = "Superkalifragelistikexpialigetisch";

		byte[] cipher = DeEnCryption.encrypt(password, masterPassword, name);
		assertEquals(password, DeEnCryption.decrypt(cipher, masterPassword, name));
	}

	@Test
	public void testPasswordDeAndEncryption3() {
		String password = "dasistvielleichtdasdümmsteaberauchdassicherstepasswortEVER";
		String name = "web.de";
		String masterPassword = "WhatTheFN*gga";

		byte[] cipher = DeEnCryption.encrypt(password, masterPassword, name);
		assertEquals(password, DeEnCryption.decrypt(cipher, masterPassword, name));
	}
}
