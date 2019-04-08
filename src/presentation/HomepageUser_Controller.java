package presentation;

import java.util.ArrayList;
import java.util.Optional;

import entity.Escursione;
import entity.Utente;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomepageUser_Controller {

    private FrontController FC = FrontController.getInstance();

    @FXML
    private TableView<Escursione> tableEscursioni;
    @FXML
    private TableView<Escursione> tableEscursioniDisponibili;

    @FXML
    private Button modifica;
    @FXML
    private Button cancella;
    @FXML
    private Button userEditButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button iscrizioneEscursione;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label cognomeLabel;
    @FXML
    private Label dataLabel;
    @FXML
    private Label codiceFiscaleLabel;
    @FXML
    private Label telefonoLabel;
    @FXML
    private Label indirizzoLabel;
    @FXML
    private Label mailLabel;
    @FXML
    private Label sexLabel;
    @FXML
    private Label townLabel;
    @FXML
    private Label paeseLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private GridPane tableUser;

    private Utente user;
    private Escursione escursione;
    private int ID;

    @SuppressWarnings("unchecked")
    public void initialize(Utente user) {
        this.user = user;
        ID = user.getID();
        refreshDetails();
        showUserDetails(user);
        selectAvaiableExcursions();
        selectExcursions();
        refreshTables();
        tableEscursioni.getColumns().setAll(showExcursions());
        tableEscursioni.setItems((ObservableList<Escursione>) FC.processRequest(user, 3, 1));
        tableEscursioniDisponibili.getColumns().setAll(showExcursionsAvaiable());
        tableEscursioniDisponibili.setItems((ObservableList<Escursione>) FC.processRequest(user, 4, 1));

        System.out.println("ID utente = " + ID);
    }

    public void showUserDetails(Utente user) {
        user.setID(user.getID());
        user = (Utente) FC.processRequest(user, 1, 1);

        if (user != null) {
            firstNameLabel.setText(user.getNome());
            lastNameLabel.setText(user.getCognome());
            nomeLabel.setText(user.getNome());
            cognomeLabel.setText(user.getCognome());
            dataLabel.setText(user.getData());
            codiceFiscaleLabel.setText(user.getCodiceFiscale().toUpperCase());
            telefonoLabel.setText(user.getTelefono());
            indirizzoLabel.setText(user.getIndirizzo());
            mailLabel.setText(user.getEmail());
            sexLabel.setText(user.getSex());
            townLabel.setText(user.getTown());
            paeseLabel.setText(user.getPaese());
            usernameLabel.setText(user.getUsername());
            passwordLabel.setText(user.getPassword());
        }

    }

    public void selectAvaiableExcursions() {
        tableEscursioniDisponibili.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                escursione = tableEscursioniDisponibili.getItems()
                        .get(tableEscursioniDisponibili.getSelectionModel().getSelectedIndex());
            }
        });
    }

    public void selectExcursions() {
        tableEscursioni.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                escursione = tableEscursioni.getItems().get(tableEscursioni.getSelectionModel().getSelectedIndex());
            }
        });
    }

    @FXML
    public void newExcursion(ActionEvent event) throws Exception {
        escursione.setIDUtente(ID);
        FXMLLoader loaderIscrizione = new FXMLLoader();
        loaderIscrizione.setLocation(getClass().getResource("/ui/IscrizioneEscursione.fxml"));
        Parent root = loaderIscrizione.load();
        Scene rootScene = new Scene(root);
        IscrizioneEscursione_Controller controller = loaderIscrizione.getController();
        controller.initialize(this.user, escursione);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
        window.setTitle("Iscrizione a nuova escursione");
        window.setResizable(false);
        window.centerOnScreen();
        rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
    }

    @SuppressWarnings("unused")
    @FXML
    public void deleteExcursion(ActionEvent event) throws Exception {
        System.out.println("ID escursione = " + escursione.getIDEscursione());
        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Annulla Escursione");
        alert.setHeaderText(null);
        alert.setContentText(
                user.getNome() + " sei sicuro di voler eliminare la tua partecipazione da questa escursione?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            try {
                escursione.setIDUtente(ID);
                FC.processRequest(escursione, 7, 1);
                Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
                confirm.setTitle("Cancellazione Escursione!");
                confirm.setHeaderText(null);
                confirm.setContentText("Sei stato correttamente cancellato da questa escursione.");
                Optional<ButtonType> ok_ins = confirm.showAndWait();
                if (ok_ins.get() == ButtonType.OK) {
                    alert.close();
                    refreshTables();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
                alert.setTitle("Attenzione");
                alert.setHeaderText(null);
                alert.setContentText("Si è verificato un errore durante la cancellazione della partecipazione.");
            }
        }
    }

    @FXML
    public void editExcursion(ActionEvent event) throws Exception {
        escursione.setIDUtente(ID);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/ModificaEscursione.fxml"));
        Parent root = loader.load();
        Scene rootScene = new Scene(root);
        ModificaEscursione_Controller controller = loader.getController();
        controller.initialize(this.user, escursione);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
        window.setTitle("Modifica escursione");
        window.setResizable(false);
        window.centerOnScreen();
        rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
    }

    public void refreshDetails() {
        user.setID(user.getID());
        user = (Utente) FC.processRequest(user, 1, 1);
        if (user != null) {
            firstNameLabel.setText(user.getNome());
            lastNameLabel.setText(user.getCognome());
            nomeLabel.setText(user.getNome());
            cognomeLabel.setText(user.getCognome());
            dataLabel.setText(user.getData());
            telefonoLabel.setText(user.getTelefono());
            indirizzoLabel.setText(user.getIndirizzo());
            mailLabel.setText(user.getEmail());
            sexLabel.setText(user.getSex());
            townLabel.setText(user.getTown());
            paeseLabel.setText(user.getPaese());
            usernameLabel.setText(user.getUsername());
            passwordLabel.setText(user.getPassword());
        }

    }

    @SuppressWarnings("unchecked")
    public void refreshTables() {
        tableEscursioni.getColumns().setAll(showExcursions());
        tableEscursioniDisponibili.getColumns().setAll(showExcursionsAvaiable());
        tableEscursioni.setItems((ObservableList<Escursione>) FC.processRequest(user, 3, 1));
        tableEscursioniDisponibili.setItems((ObservableList<Escursione>) FC.processRequest(user, 4, 1));
    }

    @FXML
    public void handleEditUser(ActionEvent event) throws Exception {
        user.setUsername(usernameLabel.getText());
        user.setTipo(1);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/ModificaUtente.fxml"));
        Parent root = loader.load();
        Scene rootScene = new Scene(root);
        EditUserDetails_Controller controller = loader.getController();
        controller.initialize(this.user);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
        window.setTitle("Modifica Dati");
        window.setResizable(false);
        window.centerOnScreen();
        rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
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

    public ArrayList<TableColumn<Escursione, String>> showExcursions() {
        String[] escCols = { "codiceEscursione", "dataEscursione", "tipoEscursione", "prezzo", "Optional" };
        ArrayList<TableColumn<Escursione, String>> escursione = new ArrayList<>();
        for (int i = 0; i < escCols.length; i++) {
            TableColumn<Escursione, String> tablecolumn = new TableColumn<Escursione, String>(escCols[i].toUpperCase());
            tablecolumn.setCellValueFactory(new PropertyValueFactory<Escursione, String>(escCols[i]));
            escursione.add(tablecolumn);
        }
        return escursione;
    }

    public ArrayList<TableColumn<Escursione, String>> showExcursionsAvaiable() {
        String[] escaCols = { "codiceEscursione", "dataEscursione", "tipoEscursione", "postiDisponibili", "prezzo",
                "Optional" };
        ArrayList<TableColumn<Escursione, String>> escursione = new ArrayList<>();
        for (int i = 0; i < escaCols.length; i++) {
            TableColumn<Escursione, String> tablecolumn = new TableColumn<Escursione, String>(
                    escaCols[i].toUpperCase());
            tablecolumn.setCellValueFactory(new PropertyValueFactory<Escursione, String>(escaCols[i]));
            escursione.add(tablecolumn);
        }
        return escursione;
    }

    @SuppressWarnings("unused")
    public void deleteAccount(ActionEvent event) throws Exception {
        Alert alert = new Alert(AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Cancellazione Account");
        alert.setHeaderText(null);
        alert.setContentText(user.getNome() + " sei sicuro di voler cancellare il tuo account? \n"
                + "I tuoi dati verranno completamente eliminati e dovrai registrarti nuovamente se vuoi partecipare alle escursioni.\n"
                + "L'azione non è reversibile.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            try {
                user.setID(ID);
                user.setUsername(user.getUsername());
                FC.processRequest(user, 9, 1);
                Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
                confirm.setTitle("Cancellazione Escursione!");
                confirm.setHeaderText(null);
                String content = String.format("Sei stato correttamente cancellato da questa piattaforma. \n Il programma verrà chiusto automaticamente. \n Arrivederci!");
                confirm.setContentText(content);
                Optional<ButtonType> ok_ins = confirm.showAndWait();
                if (ok_ins.get() == ButtonType.OK) {
                    alert.close();
                    Platform.exit();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
                alert.setTitle("Attenzione");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Si è verificato un errore durante la cancellazione dell'account. Contatta il supporto.");
            }
        }
    }
}
