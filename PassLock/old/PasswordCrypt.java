package dataManagement;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Klasse, die das verschlüsselte sowie das gehashte Passwort enthält.
 * 
 * @author Manuel Mueller
 *
 */
public class PasswordCrypt implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] cipherPassword;
	private byte[] hash;

	public PasswordCrypt(byte[] cipherPassword, byte[] hash) {
		this.cipherPassword = cipherPassword;
		this.hash = hash;
	}

	/**
	 * getter-Mehtode
	 * 
	 * @return cipherPassword.
	 */
	public byte[] getPassword() {
		return cipherPassword;
	}

	/**
	 * getter-Mehtode
	 * 
	 * @return hash;
	 */
	public byte[] getHash() {
		return hash;
	}

	/**
	 * Überschreibt die equals-Methode. Liefert true genau dann, wenn
	 * cipherPassword und hash gleich sind.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PasswordCrypt) {
			PasswordCrypt passObj = (PasswordCrypt) obj;
			return (Arrays.equals(cipherPassword, passObj.getPassword()) && Arrays.equals(hash, passObj.getHash()));
		}
		return false;
	}

	/**
	 * Überschreibt die hashCode-Methode.
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(cipherPassword) + Arrays.hashCode(hash);
	}
}
