package gui;

import java.util.Hashtable;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.FileNotFoundException;
import java.io.IOException;

import crypto.DeEnCryption;
import crypto.PasswordGenerator;
import dataManagement.Manager;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui extends Application {
	Stage window;
	Hashtable<String, byte[]> passwords = Manager.getInstance().getPasswords();
	// Vbox - Hauptlayout
	VBox main = new VBox();
	// HBox top, obere Leiste mit Masterpasswort
	HBox top = new HBox();
	// Masterpasswort:
	Text master = new Text("Masterpasswort:");
	// Masterpasswort sichtbar machen
	CheckBox viewMaster = new CheckBox();
	Button viewMasterButton = new Button();
	// Passortfeld
	PasswordField masterPasswordHidden = new PasswordField();
	TextField masterPasswordVisible = new TextField();
	// buttom enhält alle Elemente unter top
	HBox bottom = new HBox();
	// Liste der Passwörter
	VBox list = new VBox();
	Hashtable<String, HBox> entryList = new Hashtable<String, HBox>();
	// Passwort hinzufügen
	VBox right = new VBox();
	// Name:
	Text nameOfService = new Text("Name:");
	// Textfeld (Name)
	TextField nameText = new TextField();
	// Passwort:
	Text passwordToSave = new Text("Passwort:");
	// Passwort
	HBox newPassword = new HBox();
	// Anzeigen
	CheckBox viewPass = new CheckBox();
	Button viewPassButton = new Button();
	// Textfeld (Passwort)
	PasswordField passwordHidden = new PasswordField();
	TextField passwordVisible = new TextField();
	// Zufall
	Button rand = new Button();
	// Speichern
	Button save = new Button();

	Image viewImage = new Image("file:images/view.png");
	Image hideImage = new Image("file:images/hide.png");
	Image copyImage = new Image("file:images/copy.png");
	Image editImage = new Image("file:images/edit.png");
	Image deleteImage = new Image("file:images/delete.png");
	Image saveImage = new Image("file:images/save.png");
	Image randImage = new Image("file:images/rand.png");
	
	double imageSize = 20;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		

		Hashtable<String, byte[]> passwords = Manager.getInstance().getPasswords();

		window = primaryStage;
		window.setTitle("PassLock");

		// Vbox - Hauptlayout
		main.setSpacing(10);
		main.setPadding(new Insets(8, 8, 8, 8));

		// HBox top, obere Leiste mit Masterpasswort
		top.setSpacing(10);

		// Masterpasswort:
		master.setFont(new Font(20));

		// Masterpasswort sichtbar machen
		
		//viewMaster.setStyle("-fx-background-image : url(/images/view.png); ");

		// Passortfeld
		masterPasswordHidden.setPrefWidth(300);
		masterPasswordVisible.setPrefWidth(300);
		masterPasswordVisible.managedProperty().bind(viewMaster.selectedProperty());
		masterPasswordVisible.visibleProperty().bind(viewMaster.selectedProperty());

		masterPasswordHidden.managedProperty().bind(viewMaster.selectedProperty().not());
		masterPasswordHidden.visibleProperty().bind(viewMaster.selectedProperty().not());

		masterPasswordVisible.textProperty().bindBidirectional(masterPasswordHidden.textProperty());
		
		ImageView viewImageView1 = new ImageView(viewImage);
		viewImageView1.setFitHeight(imageSize);
		viewImageView1.setFitWidth(imageSize);
		ImageView hideImageView1 = new ImageView(hideImage);
		hideImageView1.setFitHeight(imageSize);
		hideImageView1.setFitWidth(imageSize);
		viewMasterButton.setGraphic(hideImageView1);
		viewMasterButton.setOnAction((e) -> {
			if (viewMaster.selectedProperty().getValue()) {
				viewMasterButton.setGraphic(hideImageView1);
			}
			else {
				viewMasterButton.setGraphic(viewImageView1);
			}
			viewMaster.fire();
		});

		// buttom enhält alle Elemente unter top
		bottom.setPadding(new Insets(0, 0, 0, 0));
		bottom.setSpacing(10);

		// Liste der Passwörter
		list.setPadding(new Insets(0, 0, 0, 0));
		list.setSpacing(5);

		for (String name : passwords.keySet()) {
			appendToList(name);
		}

		// Passwort hinzufügen
		right.setPadding(new Insets(0, 0, 0, 0));
		right.setSpacing(5);
		right.setStyle("-fx-background-color: #eeeeee;");
		// Name:
		nameOfService.setFont(new Font(15));
		// Passwort:
		passwordToSave.setFont(new Font(15));
		// Anzeigen
		
		// Textfeld (Passwort)
		passwordHidden.setMinWidth(230);
		passwordVisible.setMinWidth(230);
		passwordHidden.setFont(new Font(15));
		passwordVisible.setFont(new Font(15));
		passwordVisible.managedProperty().bind(viewPass.selectedProperty());
		passwordVisible.visibleProperty().bind(viewPass.selectedProperty());

		passwordHidden.managedProperty().bind(viewPass.selectedProperty().not());
		passwordHidden.visibleProperty().bind(viewPass.selectedProperty().not());

		passwordVisible.textProperty().bindBidirectional(passwordHidden.textProperty());

		ImageView viewImageView2 = new ImageView(viewImage);
		viewImageView2.setFitHeight(imageSize);
		viewImageView2.setFitWidth(imageSize);
		ImageView hideImageView2 = new ImageView(hideImage);
		hideImageView2.setFitHeight(imageSize);
		hideImageView2.setFitWidth(imageSize);
		viewPassButton.setGraphic(hideImageView2);
		viewPassButton.setOnAction((e) -> {
			if (viewPass.selectedProperty().getValue()) {
				viewPassButton.setGraphic(hideImageView2);
			}
			else {
				viewPassButton.setGraphic(viewImageView2);
			}
			viewPass.fire();
		});
		
		// Zufall
		ImageView randImageView = new ImageView(randImage);
		randImageView.setFitHeight(imageSize);
		randImageView.setFitWidth(imageSize);
		rand.setGraphic(randImageView);
		// viewPass.setGraphic(new ImageView(view));
		rand.setOnAction((e) -> {
			passwordHidden.setText(PasswordGenerator.generatePassword());
		});
		// Speichern
		ImageView saveImageView = new ImageView(saveImage);
		saveImageView.setFitHeight(imageSize);
		saveImageView.setFitWidth(imageSize);
		save.setGraphic(saveImageView);
		// viewPass.setGraphic(new ImageView(view));
		save.setOnAction((e) -> {
			String name = nameText.getText();
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Es existiert bereits ein Passwort zu diesem Namen. Wirklich Passwort zu " + name + " ersetzen?",
					ButtonType.YES, ButtonType.NO);
			boolean yes = false;
			if (passwords.containsKey(name)) {
				alert.showAndWait();
				yes = alert.getResult() == ButtonType.YES;
			}
			if (!passwords.containsKey(name) || yes) {
				try {
					String pass = passwordHidden.getText();
					Manager.getInstance().addPassword(name,
							DeEnCryption.encrypt(pass, masterPasswordHidden.getText(), name));
					if (yes) {
						list.getChildren().remove(entryList.get(name));
					}
					appendToList(name);
				} catch (Exception execpt) {

				}
			}

		});

		// Alle Komponenten "zusammenfügen"
		newPassword.getChildren().addAll(passwordHidden, passwordVisible, viewPassButton, rand);
		right.getChildren().addAll(nameOfService, nameText, passwordToSave, newPassword, save);
		bottom.getChildren().addAll(list, right);
		main.getChildren().addAll(top, bottom);
		top.getChildren().addAll(master, masterPasswordHidden, masterPasswordVisible, viewMasterButton);
		
		Scene scene = new Scene(main, 800, 700);
		window.setScene(scene);
		window.setResizable(false);
		window.show();
	}

	/**
	 * Erstellt einen neuen Passwort Eintrag auf dem Interface
	 * 
	 * @param passwords
	 *            Der Hashtable, der die Passwort-Daten enthält
	 * @param name
	 *            Name des Eintrags, der hinzugefügt werden soll.
	 * @return Gibt den Eintrag zurück.
	 * @throws Exception
	 *             wird geworfen, wenn der Name nicht in der Liste auftaucht.
	 */
	private void appendToList(String name) throws Exception {
		if (!passwords.containsKey(name))
			throw new Exception();
		// Ein Passwort-Eintrag
		HBox entry = new HBox();
		entry.setPadding(new Insets(0, 0, 0, 0));
		entry.setSpacing(10);
		entry.setStyle("-fx-background-color: #eeeeee;");
		// NAME:
		Text nameOfService = new Text(name);
		nameOfService.setFont(new Font(15));
		nameOfService.setWrappingWidth(150);
		// Passortfeld
		Text passwordText = new Text("••••••");
		passwordText.setFont(new Font(15));
		passwordText.setWrappingWidth(150);
		// Passwort anzeigen
		CheckBox viewPass = new CheckBox();
		Button viewPassButton = new Button();
		
		ImageView viewImageView = new ImageView(viewImage);
		viewImageView.setFitHeight(imageSize);
		viewImageView.setFitWidth(imageSize);
		ImageView hideImageView = new ImageView(hideImage);
		hideImageView.setFitHeight(imageSize);
		hideImageView.setFitWidth(imageSize);
		viewPassButton.setGraphic(hideImageView);
		viewPassButton.setOnAction((e) -> {
			if (viewPass.selectedProperty().getValue()) {
				viewPassButton.setGraphic(hideImageView);
			}
			else {
				viewPassButton.setGraphic(viewImageView);
			}
			viewPass.fire();
		});
		viewPass.setOnAction((e) -> {
			if (viewPass.isSelected()) {
				String password;
				try {
					password = new String(DeEnCryption.checkDecrypt(Manager.getInstance().getPasswords().get(name),
							masterPasswordHidden.getText(), name));
					passwordText.setStyle("-fx-text-fill: black;");
				} catch (Exception except) {
					password = "Masterpasswort falsch!";
					passwordText.setStyle("-fx-text-fill: red;"); // Noch mal
																	// ran
				}

				passwordText.setText(password);
			} else {
				passwordText.setStyle("-fx-text-fill: black;");
				passwordText.setText("••••••");
			}

		});
		// Passwort in Zwischenspeicher kopieren
		Button copy = new Button();
		ImageView copyImageView = new ImageView(copyImage);
		copyImageView.setFitHeight(imageSize);
		copyImageView.setFitWidth(imageSize);
		copy.setGraphic(copyImageView);
		copy.setOnAction((e) -> {
			String password;
			try {
				password = new String(DeEnCryption.checkDecrypt(Manager.getInstance().getPasswords().get(name),
						masterPasswordHidden.getText(), name));
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(password), null);
			} catch (Exception except) {
				password = "Masterpasswort falsch!";
				passwordText.setStyle("-fx-text-fill: red;"); // Noch mal ran
				passwordText.setText(password);
			}
		});
		// Bearbeiten
		Button edit = new Button();
		ImageView editImageView = new ImageView(editImage);
		editImageView.setFitHeight(imageSize);
		editImageView.setFitWidth(imageSize);
		edit.setGraphic(editImageView);
		edit.setOnAction((e) -> {
			try {
				String password = DeEnCryption.checkDecrypt(Manager.getInstance().getPasswords().get(name),
						masterPasswordHidden.getText(), name);
				nameText.setText(name);
				passwordHidden.setText(password);
			} catch (Exception except) {
				String password = "Masterpasswort falsch!";
				passwordText.setStyle("-fx-text-fill: red;"); // Noch mal ran
				passwordText.setText(password);
			}
		});
		// Löschen
		Button delete = new Button();
		ImageView deleteImageView = new ImageView(deleteImage);
		deleteImageView.setFitHeight(imageSize);
		deleteImageView.setFitWidth(imageSize);
		delete.setGraphic(deleteImageView);
		delete.setOnAction((e) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Wirklich Passwort zu " + name + " löchen?", ButtonType.YES,
					ButtonType.CANCEL);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.YES) {
				try {
					DeEnCryption.checkDecrypt(Manager.getInstance().getPasswords().get(name),
							masterPasswordHidden.getText(), name);
					Manager.getInstance().removePassword(name);
					list.getChildren().remove(entry);
				} catch (Exception except) {
					if (except instanceof FileNotFoundException || except instanceof IOException) {
						Alert warning = new Alert(AlertType.ERROR,
								"Es ist etwas beim löschen des Passworts schief gelaufen.", ButtonType.OK);
						warning.showAndWait();
					} else {
						passwordText.setStyle("-fx-text-fill: red;");
						passwordText.setText("Masterpasswort falsch!");
					}
					passwordText.setStyle("-fx-text-fill: red;");
					passwordText.setText("Masterpasswort falsch!");
				}

			}
		});
		// Element in Eintrag einfügen
		entry.getChildren().addAll(nameOfService, passwordText, viewPassButton, copy, edit, delete);
		list.getChildren().add(entry);
		entryList.put(name, entry);
	}

	// Image speichern = new
	// Image(getClass().getResourceAsStream("save32.png"));
	// buttonSave.setGraphic(new ImageView(speichern));
}