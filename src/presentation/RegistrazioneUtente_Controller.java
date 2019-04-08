package presentation;

import java.util.Optional;

import entity.Admin;
import entity.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RegistrazioneUtente_Controller {

	private FrontController FC = FrontController.getInstance();
	private Utente user;
	@FXML
	private TextField inputUsername;
	@FXML
	private PasswordField inputPassword;
	@FXML
	private PasswordField inputRepeatPassword;
	@FXML
	private TextField inputFirstName;
	@FXML
	private TextField inputLastName;
	@FXML
	private DatePicker inputDate;
	@FXML
	private TextField inputCF;
	@FXML
	private TextField inputMail;
	@FXML
	private TextField inputPhone;
	@FXML
	private TextField inputCity;
	@FXML
	private TextField inputAddress;
	@FXML
	private ComboBox<String> inputState;
	@FXML
	private CheckBox inputSexM;
	@FXML
	private CheckBox inputSexF;
	@FXML
	private Button register;
	@FXML
	private Button close;
	@FXML
	private Button back;

	public void initialize(Utente user) {
		this.user = user;
		assert inputState != null : "fx:id=\"inputState\" was not injected: check your FXML file 'RegistrazioneUtente.fxml'.";
	
	}

	public void registrazioneUtente(ActionEvent event) throws Exception {
		try {
		Utente utente = new Utente();
		utente.setUsername(inputUsername.getText());
		utente.setPassword(inputPassword.getText());
		utente.setNome(inputFirstName.getText());
		utente.setCognome(inputLastName.getText());
		utente.setData(inputDate.getValue().toString());
		utente.setCodiceFiscale(inputCF.getText());
		utente.setIndirizzo(inputAddress.getText());
		utente.setTelefono(inputPhone.getText());
		utente.setEmail(inputMail.getText());
		utente.setTown(inputCity.getText());
		utente.setPaese(inputState.getValue());

		if (inputSexM.isSelected()) {
			utente.setSex(inputSexM.getText());
		}
		if (inputSexF.isSelected()) {

			utente.setSex(inputSexF.getText());
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alert");
		alert.setHeaderText(null);
		alert.setContentText("Sei sicuro di voler confermare i dati immessi?");

		Optional<ButtonType> action = alert.showAndWait();
		
		if (action.get() == ButtonType.OK) {
	
			if (user.getTipo() == 1) {
				FC.processRequest(utente, 0, 1);
				Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
				confirm.setTitle("Stato Registrazione!");
				confirm.setHeaderText(null);
				confirm.setContentText(
						"La registrazione è stata effettuata con successo.Verrai automaticamente reindirizzato nell'area utente.");

				Optional<ButtonType> ok_ins = confirm.showAndWait();
				if (ok_ins.get() == ButtonType.OK) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/ui/HomepageUser.fxml"));
					Parent root = loader.load();
					Scene rootScene = new Scene(root);
					HomepageUser_Controller controller = loader.getController();
					controller.initialize(utente);
					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(rootScene);
					window.setTitle("Pannello Utente");
					window.show();
					window.setResizable(false);
					window.centerOnScreen();
				}
			}
				if (user.getTipo() == 2) {
					FC.processRequest(utente, 0, 1);
					Alert confirm2 = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
					confirm2.setTitle("Stato Registrazione!");
					confirm2.setHeaderText(null);
					confirm2.setContentText("La registrazione è stata effettuata con successo!");
					Optional<ButtonType> ok_ins2 = confirm2.showAndWait();
					if (ok_ins2.get() == ButtonType.OK) {
						Admin admin = new Admin();
						admin.setIdAdmin(user.getID());
						admin.setTipo(user.getTipo());
						admin.setUsername(user.getUsername());
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/ui/HomepageAdmin.fxml"));
						Parent root = loader.load();
						HomepageAdmin_Controller controller = loader.getController();
						controller.initialize(admin);
						Scene rootScene = new Scene(root);
						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(rootScene);
						window.show();
						window.setTitle("Pannello Amministratore");
						window.setResizable(false);
						window.centerOnScreen();
						rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
					}
				}

		}
		
		
		
		}catch(Exception e) {
			e.printStackTrace();
			e.getMessage();
			e.getCause();
		}
	}

	@FXML
	public void handleSexM() {
		if (inputSexM.isSelected()) {
			inputSexF.setSelected(false);
		}
	}

	@FXML
	public void handleSexF() {
		if (inputSexF.isSelected()) {
			inputSexM.setSelected(false);
		}
	}

	@FXML
	public void handleBack(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/ui/Homepage.fxml"));
		Parent root = loader.load();
		Scene rootScene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(rootScene);
		window.show();
		window.setTitle("Homepage");
		window.setResizable(false);
		window.centerOnScreen();
		rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
	}

	/**
	 * Closes the application.
	 */
	@FXML
	private void handleExit() {
		System.exit(0);
	}
}
