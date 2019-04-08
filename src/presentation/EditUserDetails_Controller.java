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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditUserDetails_Controller {

    private FrontController FC = FrontController.getInstance();
    private Utente user;
    @FXML
    private TextField inputUsername;
    @FXML
    private TextField inputPassword;
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
    @FXML
    private GridPane tableUser;

    private int type;

    public void initialize(Utente user) throws Exception {
        this.user = user;
        assert inputState != null : "fx:id=\"inputState\" was not injected: check your FXML file 'ModificaUtente.fxml'.";
        showUserDetails(user);
        type = user.getTipo();
        System.out.println("tipo utente" + user.getTipo());
    }

    public void showUserDetails(Utente user) {
        user = (Utente) FC.processRequest(user, 1, 1);
        if (user != null) {
            inputFirstName.setText(user.getNome());
            inputLastName.setText(user.getCognome());
            inputDate.setPromptText(user.getData());
            inputCF.setText(user.getCodiceFiscale());
            inputMail.setText(user.getEmail());
            inputPhone.setText(user.getTelefono());
            inputCity.setText(user.getTown());
            inputAddress.setText(user.getIndirizzo());
            inputState.setValue(user.getPaese());
            inputUsername.setText(user.getUsername());
            inputPassword.setText(user.getPassword());

            if (user.getSex().contentEquals("M")) {
                inputSexM.setSelected(true);
            }
            if (user.getSex().contentEquals("F")) {
                inputSexF.setSelected(true);
            }

        }

    }

    public void updateUserDetails(ActionEvent event) throws Exception {

        user.setNome(inputFirstName.getText());
        user.setCognome(inputLastName.getText());
        user.setData(inputDate.getPromptText().toString());
        user.setCodiceFiscale(inputCF.getText());
        user.setIndirizzo(inputAddress.getText());
        user.setTelefono(inputPhone.getText());
        user.setEmail(inputMail.getText());
        user.setTown(inputCity.getText());
        user.setPaese(inputState.getValue());

        if (inputSexM.isSelected()) {
            user.setSex(inputSexM.getText());
        }
        if (inputSexF.isSelected()) {

            user.setSex(inputSexF.getText());
        }

        user.setUsername(inputUsername.getText());
        user.setPassword(inputPassword.getText());

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText("Sei sicuro di voler confermare i dati immessi?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (type == 1) {
                FC.processRequest(user, 2, 1);
                Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
                confirm.setTitle("Modifica Effettuata");
                confirm.setHeaderText(null);
                confirm.setContentText("I tuoi dati sono stati modificati correttamente!");
                confirm.showAndWait();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/ui/HomepageUser.fxml"));
                Parent root = loader.load();
                Scene rootScene = new Scene(root);
                HomepageUser_Controller controller = loader.getController();
                controller.initialize(this.user);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(rootScene);
                window.show();
                window.setTitle("Pannello Utente");
                window.setResizable(false);
                window.centerOnScreen();
                rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
            } else {
                FC.processRequest(user, 2, 1);
                Alert confirm2 = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
                confirm2.setTitle("Stato Modifica");
                confirm2.setHeaderText(null);
                confirm2.setContentText("I dati dell'utente sono stati modificati con successo!");
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
	public void handleClose(ActionEvent event) throws Exception {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
	if (type == 2) {
		Admin admin = new Admin();
        admin.setIdAdmin(user.getID());
        admin.setTipo(user.getTipo());
        admin.setUsername(user.getUsername());
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/ui/HomepageAdmin.fxml"));
		Parent root = loader.load();
		Scene rootScene = new Scene(root);
		HomepageAdmin_Controller controller = loader.getController();
		controller.initialize(admin);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(rootScene);
		window.show();
		window.setTitle("Pannello Amministratore");
		window.setResizable(false);
		window.centerOnScreen();
		rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
	}else {
	      FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("/ui/HomepageUser.fxml"));
          Parent root = loader.load();
          Scene rootScene = new Scene(root);
          HomepageUser_Controller controller = loader.getController();
          controller.initialize(this.user);
          Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
          window.setScene(rootScene);
          window.show();
          window.setTitle("Pannello Utente");
          window.setResizable(false);
          window.centerOnScreen();
          rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
	}
    }
}
