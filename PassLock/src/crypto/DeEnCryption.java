package crypto;

import java.util.Random;

/**
 * 
 * @author Manuel Mueller
 *
 */
public class DeEnCryption {
	/**
	 * One Time Pad mit einfachem XOR.
	 * 
	 * @param data
	 *            Daten, die ver- oder entschlüsselt werden sollen.
	 * @param key
	 *            Der Schlüssel mit dem ver- oder entschlüsselt wird.
	 * @return Den Cipher-Text oder die entschlüsselte Nachricht.
	 */
	private static byte[] deencrypt(byte[] data, byte[] key) {
		byte[] cipher = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			cipher[i] = (byte) (data[i] ^ key[i]);
		}
		return cipher;
	}

	/**
	 * Wandelt den String in ein Byte-Array um und füllt dieses bis zum
	 * vorletzten char mit 0, und schreibt auf die 32. und letzte Stelle die
	 * Länge des Strings.
	 * 
	 * @param password
	 *            Das Char-Array, das gepaddet wird.
	 * @return Das gepaddete Byte-Array.
	 * @throws IllegalArgumentException
	 *             Wirft eine IllegalArgumentException, wenn das Char-Array
	 *             länger als 255 oder kürzer als 1 Zeichen ist.
	 */
	private static byte[] pad(String password) throws IllegalArgumentException {
		if (password.length() > 251) {
			throw new IllegalArgumentException("Password too large: " + password.length()
					+ ". Only passwords of length smaller or equal to 251 allowed.");
		}
		if (password.length() == 0) {
			throw new IllegalArgumentException("Password must be atleast one symbol long.");
		}
		int length = password.length();

		// byte[] padded = new byte[32 * ((int) length / 32 + 1)];
		byte[] padded = new byte[256];

		new Random().nextBytes(padded);

		padded[251] = 0;
		padded[252] = 0;
		padded[253] = 0;
		padded[254] = 0;

		for (int i = 0; i < length; i++) {
			padded[i] = password.getBytes()[i];
		}
		padded[255] = (byte) length;
		return padded;
	}

	/**
	 * Generiert aus dem Master-Passwort und dem Namen des Dienstes für den ein
	 * Passwort gespeichert werden soll ein Key mit Hilfe der festgelegten
	 * Hash-Funktion.
	 * 
	 * @param masterPassword
	 *            Das Master-Passwort als Char-Array.
	 * @param name
	 *            Der Name des Dienstes für den ein Passwort gespeichert werden
	 *            soll als Char-Array.
	 * @return Der generierte Schlüssel.
	 */
	private static byte[] generateKey(String masterPassword, String name) {
		byte[] key = new byte[256];
		byte[] hash = (masterPassword + name).getBytes();
		for (int i = 0; i < 8; i++) {
			hash = hash(hash);
			for (int j = 0; j < 32; j++) {
				try {
					key[(i * 32) + j] = hash[j];
				} catch (ArrayIndexOutOfBoundsException e) {
					break;
				}
			}
		}
		return key;
	}

	/**
	 * Verschlüsselt das Passwort unter Verwendung eines Masterpassworts und dem
	 * Namen des Dienstes für den ein Passwort gespeichert werden soll.
	 * 
	 * @param password
	 *            Das zu verschlüsselnde Passwort als Char-Array.
	 * @param masterPassword
	 *            Das Master-Passwort als Char-Array.
	 * @param name
	 *            Der Name des Dienstes für den ein Passwort gespeichert werden
	 *            soll als Char-Array.
	 * @return Der Cipher-Text.
	 * @throws IllegalArgumentException
	 *             Wirft eine IllegalArgumentException, wenn das Char-Array
	 *             länger als 255 oder kürzer als 1 Zeichen ist.
	 */
	public static byte[] encrypt(String password, String masterPassword, String name) throws IllegalArgumentException {
		return deencrypt(pad(password), generateKey(masterPassword, name));
	}

	/**
	 * Entschlüsselt den Cipher-Text unter Verwendung eines Masterpassworts und
	 * dem Namen des Dienstes für den das Passwort gespeichert wurde.
	 * 
	 * @param data
	 *            der Cipher-Text als Byte-Array.
	 * @param masterPassword
	 *            Das Master-Passwort als Char-Array.
	 * @param name
	 *            Der Name des Dienstes für den das Passwort entschlüsselt
	 *            werden soll als Char-Array.
	 * @return Das entschlüsselte Passwort.
	 */
	public static String decrypt(byte[] data, String masterPassword, String name) {
		byte[] message = deencrypt(data, generateKey(masterPassword, name));
		int length = (message[message.length - 1] & 0xFF);
		return new String(message, 0, length);
	}

	/**
	 * Entschlüsselt den Cipher-Text unter Verwendung eines Masterpassworts und
	 * dem Namen des Dienstes für den das Passwort gespeichert wurde genau dann,
	 * wenn Masterpasswort und Name des Dienstes zur Cipher passen.
	 * 
	 * @param data
	 *            der Cipher-Text als Byte-Array.
	 * @param masterPassword
	 *            Das Master-Passwort als Char-Array.
	 * @param name
	 *            Der Name des Dienstes für den das Passwort entschlüsselt
	 *            werden soll als Char-Array.
	 * @return Das entschlüsselte Passwort.
	 * @throws Exception
	 *             Wirft Exception, wenn Masterpasswort und Name nicht zur
	 *             Cipher passen.
	 */
	public static String checkDecrypt(byte[] data, String masterPassword, String name) throws Exception {
		byte[] message = deencrypt(data, generateKey(masterPassword, name));
		for (int i = 251; i < 255; i++) {
			if (message[i] != 0) {
				throw new Exception("Wrong password!");
			}
		}
		int length = (message[message.length - 1] & 0xFF);
		return new String(message, 0, length);
	}

	/**
	 * Liefert den Hash nach dem Sha256-Schema
	 * 
	 * @param password
	 *            Das Passwort, das gehasht wird.
	 * @return Das gehashte Passwort.
	 */

	public static byte[] hash(byte[] data) {
		return Sha256.digest(data);
	}
}
