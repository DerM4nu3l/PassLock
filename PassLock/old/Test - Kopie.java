package test;

import crypto.DeEnCryption;
import crypto.Sha256;
import dataManagement.Manager;

public class Test {

	public static void main(String[] args) {
		/*testCrypto("masterpassword", "password", "youtube");
		System.out.println();
		*/
		
		char[][] firstc = new char[2][];
		firstc[0] = "youtube".toCharArray();
		firstc[1] = "facebook".toCharArray();
		byte[][] firstb = new byte[2][1];
		firstb[0][0] = 2;
		firstb[1][0] = 3;
		char[][] secondc = new char[2][];
		secondc[0] = "email".toCharArray();
		secondc[1] = "spotify".toCharArray();
		byte[][] secondb = new byte[2][1];
		secondb[0][0] = 4;
		secondb[1][0] = 5;
		
		testManager(firstc, firstb);
		System.out.println();
		testManager(secondc, secondb);
		System.out.println();
		
		Manager manager = Manager.getInstance();
		for (char[] name : manager.getPasswords().keySet()) {
			System.out.println(new String(name) + ": " + manager.getPasswords().get(name)[0]);
		}
		
		//testCombined();
	}
	
	private static void testCrypto(String master, String password, String name) {
		DeEnCryption crypto = new DeEnCryption(Sha256.getInstance());
		byte[] cipher = crypto.encrypt(password.toCharArray(), master.toCharArray(), name.toCharArray());
		System.out.println(new String(cipher));
		char[] cryptPass = crypto.decrypt(cipher, master.toCharArray(), name.toCharArray());
		System.out.println(new String(cryptPass));
		System.out.println(password.equals(new String(cryptPass)));
	}
	
	private static void testManager(char[][] names, byte[][] passwords) {
		Manager manager = Manager.getInstance();
		for (int i = 0; i < names.length; i++) {
			manager.addPassword(names[i], passwords[i]);
		}
		for (char[] name : manager.getPasswords().keySet()) {
			System.out.println(new String(name));
		}
	}
	
	private static void testCombined() {
		DeEnCryption crypto = new DeEnCryption(Sha256.getInstance());
		Manager manager = Manager.getInstance();
		manager.addPassword("youtube".toCharArray(), crypto.encrypt("password1".toCharArray(), "masterpassword".toCharArray(), "youtube".toCharArray()));
		manager.addPassword("facebook".toCharArray(), crypto.encrypt("password2".toCharArray(), "masterpassword".toCharArray(), "facebook".toCharArray()));
		manager.addPassword("email".toCharArray(), crypto.encrypt("password3".toCharArray(), "masterpassword".toCharArray(), "email".toCharArray()));
		manager.addPassword("spotify".toCharArray(), crypto.encrypt("password4".toCharArray(), "masterpassword".toCharArray(), "spotify".toCharArray()));
		
		for (char[] name : manager.getPasswords().keySet()) {
			String pass = new String(crypto.decrypt(manager.getPasswords().get(name), "masterpassword".toCharArray(), name));
			System.out.println(new String(name) + ": " + pass);
		}
	}
}
