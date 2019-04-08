package presentation;

import javafx.event.ActionEvent;
import java.util.Optional;
import entity.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RegistrazioneAdmin_Controller {

	private FrontController FC = FrontController.getInstance();

	private Admin admin;
	@FXML
	private TextField inputUsernameAdmin;
	@FXML
	private PasswordField inputPasswordAdmin;
	@FXML
	private TextField inputFirstNameAdmin;
	@FXML
	private TextField inputLastNameAdmin;
	@FXML
	private TextField inputMailAdmin;
	@FXML
	private Button registraAdmin;
	@FXML
	private Button close;

	public void initialize(Admin admin){
		this.admin = admin;
	}

	public void registrazioneAdmin(ActionEvent event) throws Exception{

		Admin admin = new Admin();

		admin.setUsername(inputUsernameAdmin.getText());
		admin.setPassword(inputPasswordAdmin.getText());
		admin.setNome(inputFirstNameAdmin.getText());
		admin.setCognome(inputLastNameAdmin.getText());
		admin.setEmail(inputMailAdmin.getText());

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alert");
		alert.setHeaderText(null);
		alert.setContentText("Sei sicuro di voler confermare i dati immessi?");

		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {
			FC.processRequest(admin, 0 , 2);
			Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
			confirm.setTitle("Stato Registrazione!");
			confirm.setHeaderText(null);
			confirm.setContentText(
			"La registrazione è stata effettuata con successo!");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ui/HomepageAdmin.fxml"));
			Parent root = loader.load();
			Scene rootScene = new Scene(root);
			HomepageAdmin_Controller controller = loader.getController();
			controller.initialize(this.admin);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(rootScene);
			window.show();
			window.setTitle("Registrazione Utente");
			window.setResizable(false);
			window.centerOnScreen();
			rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
		}
	}

	@FXML
	public void handleBack(ActionEvent event) throws Exception {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/ui/HomepageAdmin.fxml"));
		Parent root = loader.load();
		Scene rootScene = new Scene(root);
		HomepageAdmin_Controller controller = loader.getController();
		controller.initialize(this.admin);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(rootScene);
		window.show();
		window.setTitle("Pannello Amministratore");
		window.setResizable(false);
		window.centerOnScreen();
		rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
	}

}
