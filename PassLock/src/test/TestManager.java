package test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import crypto.DeEnCryption;
import dataManagement.Manager;

public class TestManager {

	@BeforeClass
	public static void before() {
		Manager manager = Manager.getInstance();

		String password1 = "mypassword";
		String name1 = "Youtube";
		String masterPassword1 = "mymasterpassword";

		byte[] cipher1 = DeEnCryption.encrypt(password1, masterPassword1, name1);

		try {
			manager.addPassword(name1, cipher1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String password2 = "123456";
		String name2 = "Facebook";
		String masterPassword2 = "Superkalifragelistikexpialigetisch";

		byte[] cipher2 = DeEnCryption.encrypt(password2, masterPassword2, name2);

		try {
			manager.addPassword(name2, cipher2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String password3 = "dasistvielleichtdasdümmsteaberauchdassicherstepasswortEVER";
		String name3 = "web.de";
		String masterPassword3 = "WhatTheFN";

		byte[] cipher3 = DeEnCryption.encrypt(password3, masterPassword3, name3);

		try {
			manager.addPassword(name3, cipher3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String password4 = "HuhFussspuren";
		String name4 = "twitter";
		String masterPassword4 = "DasMasterPasswort";

		byte[] cipher4 = DeEnCryption.encrypt(password4, masterPassword4, name4);

		try {
			manager.addPassword(name4, cipher4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String password5 = "Hey!Quitstealingmymoves";
		String name5 = "reddit";
		String masterPassword5 = "NormMacdonald";

		byte[] cipher5 = DeEnCryption.encrypt(password5, masterPassword5, name5);

		try {
			manager.addPassword(name5, cipher5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void start() {
		assertTrue(true);
	}
}
