package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;
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
import crypto.Sha256;
import dataManagement.Manager;

/**
 * 
 * @author Manuel Mueller
 *
 */
public class Gui extends JFrame {
	
	DeEnCryption crypto = new DeEnCryption(Sha256.getInstance());
	Manager manager = Manager.getInstance();
	String password;
	
	public Gui(String title) {
		super(title);
		
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(3,3));
		
		
		pane.add(new Container());
		pane.add(new JLabel("Masterpasswort:"));
		pane.add(new JLabel("Dienst:"));
		
		
		JButton neues = new JButton("Neues Passwort speichern");
		pane.add(neues);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		pane.add(passwordField);
		
		JComboBox<char[]> menu = new JComboBox<char[]>();
		for (char[] nam : manager.getPasswords().keySet()) {
			menu.addItem(nam);
		}
		pane.add(menu);
		
		
		JTextField textField = new JTextField();
		textField.setEditable(false);
		
		JButton anzeigen = new JButton("Passwort anzeigen");
		anzeigen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				char[] nam = menu.getItemAt(menu.getSelectedIndex());
				password = new String(crypto.decrypt(manager.getPasswords().get(nam), passwordField.getPassword(), nam));
				
				textField.setText(password);
			}
		});
		pane.add(anzeigen);
		
		pane.add(textField);
		
		JButton zwischen = new JButton("In Zwischenspeicher kopieren");
		pane.add(zwischen);
	}
	
	/**
	 * Startet das Programm.
	 * @param args
	 */
	public static void main(String[] args) {
		
		Gui theWindow = new Gui("PassLock");
		theWindow.setSize(800, 150);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		theWindow.setLocation(dim.width/2-theWindow.getSize().width/2, dim.height/2-theWindow.getSize().height/2);
		theWindow.setVisible(true);
	}
}
