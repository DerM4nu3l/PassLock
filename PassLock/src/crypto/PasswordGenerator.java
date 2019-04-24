package crypto;
/**
 * 
 * @author Manuel Mueller
 *
 */
public class PasswordGenerator {

	// Verfügbare Buchstaben
	private static final char[] LETTERS = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83,
			84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112,
			113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };

	// Verfügbare Zahlen
	private static char[] NUMBERS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57 };

	// Verfügbare Spezialzeichen
	private static char[] SPECIAL = { 35, 36, 37, 38, 64 };

	/**
	 * Generiert ein Zufälliges Passwort mit Buchstaben (groß und klein), Zahlen
	 * und Sonderzeichen. Dabei tauchen Zahlen und Sonderzeichen etwas
	 * wahrscheiner auf um auszugleichen, dass es mehr Buchstaben gibt. Dies
	 * soll verhindern, dass Passwörter ohne Sonderzeichen oder Zahlen
	 * entstehen, was die Sicherheit beeinträchtigt.
	 * 
	 * @return Das generierte Passwort als String.
	 */
	public static String generatePassword() {
		char[] password = new char[16];
		for (int i = 0; i < 16; i++) {
			double choice = Math.random();
			if (choice < 0.1875) {
				password[i] = SPECIAL[(int) (Math.random() * SPECIAL.length)];
			} else if (choice < 0.5) {
				password[i] = NUMBERS[(int) (Math.random() * NUMBERS.length)];
			} else {
				password[i] = LETTERS[(int) (Math.random() * LETTERS.length)];
			}
		}
		return new String(password);
	}
}
