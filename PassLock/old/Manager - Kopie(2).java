package dataManagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author Manuel Mueller
 *
 */
public class Manager {

	private Hashtable<String, PasswordCrypt> passwords = new Hashtable<String, PasswordCrypt>();

	/**
	 * Einzige Instanz von Manager
	 */
	private static final Manager manager = new Manager();

	/**
	 * Gibt die Instanz zurück.
	 *
	 * @return Die Manager Instanz.
	 */
	public static Manager getInstance() {
		return manager;
	}

	/**
	 * Privater Konstructor um Initialisierung außerhalb dieser Klasse zu
	 * verhindern.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Manager() {
		try {
			FileInputStream fis = new FileInputStream("data.tmp");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			if (obj instanceof Hashtable<?, ?>) {
				passwords = (Hashtable<String, PasswordCrypt>) obj;
			}

			ois.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean addPassword(String name, byte[] password, byte[] hash) throws FileNotFoundException, IOException {
		passwords.put(name, new PasswordCrypt(password, hash));

		PrintWriter pw = new PrintWriter("data.tmp");
		pw.close();

		FileOutputStream fos = new FileOutputStream("data.tmp");
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(passwords);
		oos.close();

		return true;
	}

	/**
	 * Einfache getter-Methode für passwords.
	 * 
	 * @return passwords.
	 */
	public Hashtable<String, PasswordCrypt> getPasswords() {
		return passwords;
	}

	/**
	 * Prüft, ob ein Passwort zu name gespeichert ist.
	 * 
	 * @param name
	 *            Der Name des Dienstes, der überprüft werden soll.
	 * @return true, wenn es bereits einen Eintrag zu name gibt, sonst false.
	 */
	public boolean containsService(char[] name) {
		return passwords.containsKey(name);
	}

}
