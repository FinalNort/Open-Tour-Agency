
package presentation;

import entity.Login;
import entity.Utente;
import entity.Admin;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller Login.
 *
 * @author Panarosa Gaetano Matricola 609416
 */

public class Login_Controller {

	private FrontController FC = FrontController.getInstance();

	private Login login;
	private Utente utente;
	private Admin admin;

	@FXML
	private Button loginButton;
	@FXML
	private Button close;

	@FXML
	public void handleLogin(ActionEvent event) throws IOException {

		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login");
		dialog.setHeaderText("Inserire le credenziali d'accesso");
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/resources/faviconLogin.png").toString()));

		dialog.setGraphic(new ImageView(this.getClass().getResource("/resources/login.png").toString()));

		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		Platform.runLater(() -> username.requestFocus());

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			}

			return null;

		});

		@SuppressWarnings("unused")
		Optional<Pair<String, String>> result = dialog.showAndWait();

		try  {
			login = new Login();

			login.setUsername(username.getText());
			login.setPassword(password.getText());
			login = (Login) FC.processRequest(login, 0, 0);
			
			if (login.getStatus() == 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Attenzione");
				alert.setHeaderText(null);
				alert.setContentText("Le credenziali di accesso inserite sono errate o inesistenti!");
				Optional<ButtonType> error = alert.showAndWait();
				if (error.get() == ButtonType.OK) {
					FC = FrontController.getInstance();
				} else {
					alert.close();
					Login_Controller login = new Login_Controller();
					login.handleLogin(event);
				}

			}
			else {
				
			// TIPO 1: UTENTE GENERICO
			if (login.getTipo() == 1) {

				utente = new Utente();

				Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
				alert.setTitle("Credenziali confermate");
				alert.setHeaderText(null);
				alert.setContentText(
						"Il login è avvenuto con successo. Verrai reindirizzato alla tua pagina personale.");
				java.util.Optional<ButtonType> action = alert.showAndWait();
				if (action.get() == ButtonType.OK) {

					utente.setUsername(login.getUsername());
					utente.setID(login.getIDLogin());
					utente.setTipo(login.getTipo());
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/ui/HomepageUser.fxml"));
					Parent root = loader.load();
					HomepageUser_Controller controller = loader.getController();
					controller.initialize(this.utente);
					Scene rootScene = new Scene(root);
					rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(rootScene);
					window.setTitle("Pannello login");
					window.show();
					window.setResizable(false);
					window.centerOnScreen();
					rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
				}

			}

			// TIPO 2 = AMMINISTRATORE DI SISTEMA
			if (login.getTipo() == 2) {
				admin = new Admin();
				admin.setUsername(login.getUsername());
				admin.setIdAdmin(login.getIDLogin());
				admin.setTipo(login.getTipo());
				Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
				alert.setTitle("Credenziali confermate");
				alert.setHeaderText(null);
				alert.setContentText(
						"Il login è avvenuto con successo. Verrai reindirizzato alla pagina di amministrazione.");
				java.util.Optional<ButtonType> action = alert.showAndWait();
				if (action.get() == ButtonType.OK) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/ui/HomepageAdmin.fxml"));
					Parent root = loader.load();
					HomepageAdmin_Controller controller = loader.getController();
					controller.initialize(this.admin);
					Scene rootScene = new Scene(root);
					rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
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
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	@FXML
	public void handleClose(ActionEvent event) throws Exception {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
	}

}
