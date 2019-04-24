package crypto;

/**
 * 
 * @author Manuel Mueller
 *
 */
public abstract class HashFunction {
	
	/**
	 * Wendet die Hash-Funktion auf data an.
	 * @param data Die Daten, die gehasht werden sollen.
	 * @return Der Hash-Wert.
	 */
	public abstract byte[] digest(byte[] data);
	
	/**
     * Gibt zu einem Übergebenen Char-Array das gehashte Byte-Array aus.
     * @param master Der übergebene String, der gehasht wird.
     * @return Der gehashte String.
     */
	public byte[] hash(char[] input) {
		return digest(new String(input).getBytes());
	}
}
