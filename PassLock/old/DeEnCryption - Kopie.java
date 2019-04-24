package crypto;

/**
 * 
 * @author Manuel Mueller
 *
 */
public class DeEnCryption {

	private HashFunction hashFunktion;

	/**
	 * Konstruktor.
	 * 
	 * @param myHashFunktion
	 *            Die Hash-Funktion, die zur Generierung der Keys benutzt werden
	 *            soll.
	 */
	public DeEnCryption(HashFunction myHashFunktion) {
		hashFunktion = myHashFunktion;
	}

	/**
	 * One Time Pad mit einfachem XOR.
	 * 
	 * @param data
	 *            Daten, die ver- oder entschlüsselt werden sollen.
	 * @param key
	 *            Der Schlüssel mit dem ver- oder entschlüsselt wird.
	 * @return Den Cipher-Text oder die entschlüsselte Nachricht.
	 */
	private byte[] deencrypt(byte[] data, byte[] key) {
		byte[] cipher = new byte[data.length];
		for (int i = 0; i < data.length; i++) {
			cipher[i] = (byte) (data[i] ^ key[i]);
		}
		return cipher;
	}

	/**
	 * Wandelt das Char-Array in ein Byte-Array um und füllt dieses bis zum
	 * vorletzten char mit 0, und schreibt auf die 32. und letzte Stelle die
	 * Länge des ursprünglichen Arrays.
	 * 
	 * @param password
	 *            Das Char-Array, das gepaddet wird.
	 * @return Das gepaddete Byte-Array.
	 * @throws IllegalArgumentException
	 *             Wirft eine IllegalArgumentException, wenn das Char-Array
	 *             länger als 31 Zeichen ist.
	 */
	private byte[] pad(char[] password) throws IllegalArgumentException {
		if (password.length > 31) {
			throw new IllegalArgumentException();
		}
		byte[] padded = new byte[32];
		for (int i = 0; i < password.length; i++) {
			padded[i] = (byte) password[i];
		}
		for (int i = password.length; i < 31; i++) {
			padded[i] = 0;
		}
		padded[31] = (byte) password.length;
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
	private byte[] generateKey(char[] masterPassword, char[] name) {
		char[] combined = (String.valueOf(masterPassword) + String.valueOf(name)).toCharArray();
		return hashFunktion.hash(combined);
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
	 */
	public byte[] encrypt(char[] password, char[] masterPassword, char[] name) {
		try {
			return deencrypt(pad(password), generateKey(masterPassword, name));
		} catch (IllegalArgumentException e) {
			System.err.println("Passwort länger als 31 Zeichen: " + password.length);
			return pad(new char[0]);
		}
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
	public char[] decrypt(byte[] data, char[] masterPassword, char[] name) {
		byte[] message = deencrypt(data, generateKey(masterPassword, name));
		int length = (message[31] & 0xFF);
		char[] password = new char[length];
		for (int i = 0; i < length; i++) {
			password[i] = (char) message[i];
		}
		return password;
	}
}
