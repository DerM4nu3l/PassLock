package dataManagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Hashtable;

/**
 * 
 * @author Manuel Mueller
 *
 */
public class Manager {

	/**
	 * Ein Hashtable, der die Passw�rter zu deren zug�rigen Namen verschl�sselt
	 * festh�lt.
	 */
	private Hashtable<String, byte[]> passwords = new Hashtable<String, byte[]>();

	/**
	 * Einzige Instanz von Manager
	 */
	private static final Manager manager = new Manager();

	/**
	 * Gibt die Instanz zur�ck.
	 *
	 * @return Die Manager Instanz.
	 */
	public static Manager getInstance() {
		return manager;
	}

	/**
	 * Privater Konstructor um Initialisierung au�erhalb dieser Klasse zu
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
				passwords = (Hashtable<String, byte[]>) obj;
			}

			ois.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * F�gt dem Hashtable passwords das den Eintrag (name, password) hinzu. Und
	 * speichert dann den aktuellen Zustand von Hashtable in data.tmp
	 * 
	 * @param name
	 *            Name des zu speichernden Passworts
	 * @param password
	 *            das zu speichernde Passwort (verschl�sselt.)
	 * @return true genau dann, wenn das Passwort ohne Probleme gespeichert
	 *         wurde
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public boolean addPassword(String name, byte[] password) throws FileNotFoundException, IOException {
		passwords.put(name, password);

		PrintWriter pw = new PrintWriter("data.tmp");
		pw.close();

		FileOutputStream fos = new FileOutputStream("data.tmp");
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(passwords);
		oos.close();

		return true;
	}

	/**
	 * Entfernt den Eintrag (name, [PASSWORT]), falls ein Passwort zu name in
	 * passwords gespeichert ist und speichert dann den aktuellen Zustand von
	 * passwords in data.tmp.
	 * 
	 * @param name
	 *            des Passworts, dass entfernt werden soll.
	 * @return true genau dann, wenn das Passwort erfolgreich entfernt wurde.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public boolean removePassword(String name) throws FileNotFoundException, IOException {
		if (passwords.remove(name, Manager.getInstance().getPasswords().get(name))) {
			PrintWriter pw = new PrintWriter("data.tmp");
			pw.close();

			FileOutputStream fos = new FileOutputStream("data.tmp");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(passwords);
			oos.close();

			return true;
		}
		return false;
	}

	/**
	 * Einfache getter-Methode f�r passwords.
	 * 
	 * @return passwords.
	 */
	public Hashtable<String, byte[]> getPasswords() {
		return passwords;
	}

	/**
	 * Pr�ft, ob ein Passwort zu name gespeichert ist.
	 * 
	 * @param name
	 *            Der Name des Dienstes, der �berpr�ft werden soll.
	 * @return true, wenn es bereits einen Eintrag zu name gibt, sonst false.
	 */
	public boolean containsService(char[] name) {
		return passwords.containsKey(name);
	}

}
