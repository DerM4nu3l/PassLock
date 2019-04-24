package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import crypto.DeEnCryption;
import crypto.PasswordGenerator;
import crypto.Sha256;
import dataManagement.Manager;

/**
 * 
 * @author Manuel Mueller
 *
 */
public class Gui extends JFrame {

	DeEnCryption crypto = new DeEnCryption();
	Manager manager = Manager.getInstance();
	String password;

	public Gui(String title) {
		super(title);

		Container pane = getContentPane();
		pane.setLayout(new GridLayout(3, 3));

		pane.add(new Container());
		pane.add(new JLabel("Masterpasswort:"));
		pane.add(new JLabel("Dienst:"));

		JFrame newPass = new JFrame("Neues Passwort speichern");
		newPass.setSize(530, 200);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		newPass.setLocation(dim.width / 2 - newPass.getSize().width / 2, dim.height / 2 - newPass.getSize().height / 2);
		newPass.setVisible(false);

		Container paneNewPass = newPass.getContentPane();
		paneNewPass.setLayout(new GridLayout(4, 2));

		paneNewPass.add(new JLabel("Dienst:"));

		JTextField textFieldNewPass = new JTextField();
		paneNewPass.add(textFieldNewPass);

		paneNewPass.add(new JLabel("Passwort:"));

		JPasswordField passwordFieldNewPass = new JPasswordField();
		paneNewPass.add(passwordFieldNewPass);

		paneNewPass.add(new JLabel("Masterpasswort:"));

		JPasswordField masterPasswordFieldNewPass = new JPasswordField();
		paneNewPass.add(masterPasswordFieldNewPass);

		JButton gener = new JButton("Zufälliges Passwort generieren");

		gener.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				passwordFieldNewPass.setText(PasswordGenerator.generatePassword());
			}
		});

		paneNewPass.add(gener);

		JComboBox<String> menu = new JComboBox<String>();
		
		JButton speich = new JButton("Passwort speichern");

		speich.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String name = textFieldNewPass.getText();
					String pass = new String(passwordFieldNewPass.getPassword());
					String master = new String(masterPasswordFieldNewPass.getPassword());
					
					Manager.getInstance().addPassword(name, DeEnCryption.encrypt(pass, master, name));
					menu.addItem(name);
				} catch (Exception except) {

				}

			}
		});

		paneNewPass.add(speich);

		JButton neues = new JButton("Neues Passwort speichern");

		neues.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newPass.setVisible(true);
			}
		});

		pane.add(neues);

		JPasswordField passwordField = new JPasswordField();
		pane.add(passwordField);

		
		for (String nam : manager.getPasswords().keySet()) {
			menu.addItem(nam);
		}
		pane.add(menu);

		JTextField textField = new JTextField();
		textField.setEditable(false);

		JButton anzeigen = new JButton("Passwort anzeigen");
		anzeigen.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				String nam = menu.getItemAt(menu.getSelectedIndex());
				try {
					password = new String(crypto.checkDecrypt(manager.getPasswords().get(nam),
							new String(passwordField.getPassword()), nam));
					textField.setForeground(Color.black);
				} catch (Exception except) {
					password = "Wrong password";
					textField.setForeground(Color.red);
				}

				textField.setText(password);
			}
		});
		pane.add(anzeigen);
		pane.add(textField);

		JButton zwischen = new JButton("In Zwischenspeicher kopieren");
		zwischen.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				String nam = menu.getItemAt(menu.getSelectedIndex());
				try {
					password = new String(crypto.checkDecrypt(manager.getPasswords().get(nam),
							new String(passwordField.getPassword()), nam));
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(password), null);
				} catch (Exception except) {
					password = "Wrong password";
					textField.setForeground(Color.red);
					textField.setText(password);
				}

			}
		});

		pane.add(zwischen);
	}

	/**
	 * Startet das Programm.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Gui theWindow = new Gui("PassLock");
		theWindow.setSize(800, 150);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		theWindow.setLocation(dim.width / 2 - theWindow.getSize().width / 2,
				dim.height / 2 - theWindow.getSize().height / 2);
		theWindow.setVisible(true);
	}
}
