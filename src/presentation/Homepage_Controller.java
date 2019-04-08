package presentation;

import entity.Utente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Homepage_Controller{

	@SuppressWarnings("unused")
	private Utente user;
	@FXML
	private Button userSignUp;
	@FXML
	private Button userLogin;
	@FXML
	private Button adminLogin;
	@FXML
	private ImageView imageView;
	@FXML
	private AnchorPane rootLayout;
	@FXML
	private Label title;
	@FXML
	private Button infoButton;
	@FXML
	private Accordion accordion1;
	@FXML
	private Accordion accordion2;
	@FXML
	private Accordion accordion3;
	@FXML
	private Accordion accordion4;
	@FXML
	private Accordion accordion5;
	@FXML
	private TitledPane titledpane1;
	@FXML
	private TitledPane titledpane2;
	@FXML
	private TitledPane titledpane3;
	@FXML
	private TitledPane titledpane4;
	@FXML
	private TitledPane titledpane5;

	
	public void initialize() {
		accordion1.setExpandedPane(titledpane1);
		accordion2.setExpandedPane(titledpane2);
		accordion3.setExpandedPane(titledpane3);
		accordion4.setExpandedPane(titledpane4);
		accordion5.setExpandedPane(titledpane5);
	}
	
	@FXML
	public void handleInfo(ActionEvent event) {

		Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
		alert.setTitle("Informazioni");
		alert.setHeaderText("Informazioni sul software: Versione: 1.0");
		alert.setContentText("Questo software è stato realizzato da Gaetano Panarosa come caso di studio nell'anno "
				+ "accademico 2017-2018 per l'esame di Ingegneria del Software tenuto dal docente Pasquale Ardimento.");

		alert.showAndWait();

	}

	@FXML
	public void Login(ActionEvent event) throws Exception {
		Login_Controller login = new Login_Controller();
		login.handleLogin(event);
	}

	@FXML
	public void RegistrationUser(ActionEvent event) throws Exception {
		Utente user = new Utente();
		user.setTipo(1);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/ui/RegistrazioneUtente.fxml"));
		Parent root = loader.load();
		Scene rootScene = new Scene(root);
		rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
		RegistrazioneUtente_Controller controller = loader.getController();
		controller.initialize(user);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(rootScene);
		window.getIcons().add(new Image(this.getClass().getResource("/resources/reg.png").toString()));
		window.show();
		window.setTitle("Registrazione Utente");
		window.setResizable(false);
		window.centerOnScreen();
		rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
		
	}
	
}
