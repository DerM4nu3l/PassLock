package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Hashtable;

import org.junit.BeforeClass;
import org.junit.Test;

import crypto.DeEnCryption;
import dataManagement.Manager;

public class TestManagerOnlyLoadCorrected {

	@Test
	public void testLoading() {
		Manager manager = Manager.getInstance();


		String password1 = "mypassword";
		final String name1 = "Youtube";
		String masterPassword1 = "mymasterpassword";
		
		String password2 = "123456";
		final String name2 = "Facebook";
		String masterPassword2 = "Superkalifragelistikexpialigetisch";
		
		String password3 = "dasistvielleichtdasdümmsteaberauchdassicherstepasswortEVER";
		final String name3 = "web.de";
		String masterPassword3 = "WhatTheFN";
		
		String password4 = "HuhFussspuren";
		final String name4 = "twitter";
		String masterPassword4 = "DasMasterPasswort";
		
		String password5 = "Hey!Quitstealingmymoves";
		final String name5 = "reddit";
		String masterPassword5 = "NormMacdonald";

		
		Hashtable<String, byte[]> actual = manager.getPasswords();

		
		Hashtable<String, String> expectedClear = new Hashtable<String, String>();
		Hashtable<String, String> actualClear = new Hashtable<String, String>();

		
		expectedClear.put(name1, password1);
		expectedClear.put(name2, password2);
		expectedClear.put(name3, password3);
		expectedClear.put(name4, password4);
		expectedClear.put(name5, password5);
			
		
		for (String s : actual.keySet()) {

			switch (s) {
			case name1:
				actualClear.put(s, DeEnCryption.decrypt(actual.get(s), masterPassword1, name1));
				break;
			case name2:
				actualClear.put(s, DeEnCryption.decrypt(actual.get(s), masterPassword2, name2));
				break;
			case name3:
				actualClear.put(s, DeEnCryption.decrypt(actual.get(s), masterPassword3, name3));
				break;
			case name4:
				actualClear.put(s, DeEnCryption.decrypt(actual.get(s), masterPassword4, name4));
				break;
			case name5:
				actualClear.put(s, DeEnCryption.decrypt(actual.get(s), masterPassword5, name5));
				break;
			}
			
		}

		
		System.out.println("expected:");
		for (String s : expectedClear.keySet()) {
			System.out.println(s + ": " + new String(expectedClear.get(s)));
		}
		
		System.out.println("actual:");
		for (String s : actualClear.keySet()) {
			System.out.println(s + ": " + new String(actualClear.get(s)));
		}

		
		assertEquals(expectedClear, actualClear);
	}
}
