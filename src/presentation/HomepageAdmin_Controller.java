package presentation;

import java.util.ArrayList;
import java.util.Optional;

import entity.Admin;
import entity.Escursione;
import entity.Login;
import entity.Utente;
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
import javafx.stage.Stage;

/**
 * Controller per la gestione del pannello amministratore.
 *
 * @author panar
 */
public class HomepageAdmin_Controller {

    /**
     * @param user;
     * @param admin;
     */
    private Utente user;
    private Admin admin;
    @SuppressWarnings("unused")
    private Login login;
    /**
     * @param tableUsers
     *                       variabile per la dichiarazione della tabella per la
     *                       visualizzazione degli utenti registrati.
     * @param table
     *                       Admin variabile per la dichiarazione della tabella per la
     *                       visualizzazione degli amministratori registrati..
     */
    @FXML
    private TableView<Utente> tableUsers;
    @FXML
    private TableView<Admin> tableAdmin;
    @FXML
    private TableView<Escursione> showEscursioni;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;

    @FXML
    private Button registraUser;
    @FXML
    private Button modifica;
    @FXML
    private Button cancella;
    @FXML
    private Button contatta;
    @FXML
    private Button iscrivi;
    @FXML
    private Button registraAdmin;
    @FXML
    private Button modificaAdmin;
    @FXML
    private Button cancellaAdmin;
    @FXML
    private Button contattaAdmin;
    @FXML
    private Button refresh;
    @FXML
    private Button nuovaEscursione;
    @FXML
    private Button modificaEscursione;
    @FXML
    private Button cancellaEscursione;
    @FXML
    private Button attivaEscursione;
    @FXML
    private Button disattivaEscursione;

    private FrontController FC = FrontController.getInstance();
    private Escursione escursione;

    public void initialize(Admin admin) {
        this.admin = admin;
        admin.setIdAdmin(admin.getIdAdmin());
        showAdminName(admin);
        refreshTables();
        selectUser();
        selectAdmin();
        selectExcursions();
    }

    public void showAdminName(Admin admin) {
        admin.setIdAdmin(admin.getIdAdmin());
        admin = (Admin) FC.processRequest(admin, 4, 2);
        if (admin != null) {
            firstNameLabel.setText(admin.getNome());
            lastNameLabel.setText(admin.getCognome());
        }
    }

    @SuppressWarnings("unchecked")
    public void refreshTables() {
        tableUsers.getColumns().setAll(showUsers());
        tableUsers.setItems((ObservableList<Utente>) FC.processRequest(null, 1, 2));
        tableAdmin.getColumns().setAll(showAdmins());
        tableAdmin.setItems((ObservableList<Admin>) FC.processRequest(null, 2, 2));
        showEscursioni.getColumns().setAll(showEscursioni());
        showEscursioni.setItems((ObservableList<Escursione>) FC.processRequest(null, 5, 2));
    }
    public ArrayList<TableColumn<Utente, String>> showUsers() {
        String[] arrCols = { "Username", "Password", "Nome", "Cognome", "Data", "CodiceFiscale", "Telefono", "Email",
                "Sex", "Indirizzo", "Town", "Paese" };
        ArrayList<TableColumn<Utente, String>> user = new ArrayList<>();
        for (int i = 0; i < arrCols.length; i++) {
            TableColumn<Utente, String> tc = new TableColumn<Utente, String>(arrCols[i]);
            tc.setCellValueFactory(new PropertyValueFactory<Utente, String>(arrCols[i]));
            user.add(tc);
        }
        return user;

    }

    public ArrayList<TableColumn<Admin, String>> showAdmins() {
        String[] arrCols = { "idAdmin", "Username", "Password", "Nome", "Cognome", "Email" };
        ArrayList<TableColumn<Admin, String>> admin = new ArrayList<>();
        for (int i = 0; i < arrCols.length; i++) {
            TableColumn<Admin, String> tc = new TableColumn<Admin, String>(arrCols[i]);
            tc.setCellValueFactory(new PropertyValueFactory<Admin, String>(arrCols[i]));
            admin.add(tc);
        }
        return admin;

    }

    public ArrayList<TableColumn<Escursione, String>> showEscursioni() {
        String[] escaCols = { "codiceEscursione", "dataEscursione", "tipoEscursione", "minPartecipanti",
                "maxPartecipanti", "postiDisponibili", "prezzo", "Optional", "stato" };
        ArrayList<TableColumn<Escursione, String>> escursione = new ArrayList<>();
        for (int i = 0; i < escaCols.length; i++) {
            TableColumn<Escursione, String> tablecolumn = new TableColumn<Escursione, String>(
                    escaCols[i].toUpperCase());
            tablecolumn.setCellValueFactory(new PropertyValueFactory<Escursione, String>(escaCols[i]));
            escursione.add(tablecolumn);
        }
        return escursione;
    }

    public void selectUser() {
        tableUsers.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                user = tableUsers.getItems().get(tableUsers.getSelectionModel().getSelectedIndex());
                user.setUsername(user.getUsername());
                user.setID(user.getID());
            }
        });
    }

    public void selectAdmin() {
        tableAdmin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                admin = tableAdmin.getItems().get(tableAdmin.getSelectionModel().getSelectedIndex());
                admin.setUsername(admin.getUsername());
                admin.setIdAdmin(admin.getIdAdmin());
            }
        });
    }

    public void selectExcursions() {
        showEscursioni.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {
                escursione = showEscursioni.getItems().get(showEscursioni.getSelectionModel().getSelectedIndex());
                admin.setUsername(admin.getUsername());
                admin.setIdAdmin(admin.getIdAdmin());
                // attivaEscursione.setDisable(true);
                // disattivaEscursione.setDisable(false);
                escursione.setIDEscursione(escursione.getIDEscursione());
                escursione.setCodiceEscursione(escursione.getCodiceEscursione());
            }
        });
    }

    @FXML
    public void registerUser(ActionEvent event) throws Exception {
        Utente user = new Utente();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/RegistrazioneUtente.fxml"));
        Parent root = loader.load();
        Scene rootScene = new Scene(root);
        user.setTipo(2);
        user.setUsername(admin.getUsername());
        user.setPassword(admin.getPassword());
        RegistrazioneUtente_Controller controller = loader.getController();
        controller.initialize(user);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
        window.setTitle("Registrazione nuovo utente");
        window.setResizable(false);
        window.centerOnScreen();
        rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
    }

    @FXML
    public void registerAdmin(ActionEvent event) throws Exception {
        Utente user = new Utente();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/RegistrazioneAdmin.fxml"));
        Parent root = loader.load();
        Scene rootScene = new Scene(root);
        user.setTipo(2);
        user.setUsername(admin.getUsername());
        user.setPassword(admin.getPassword());
        RegistrazioneAdmin_Controller controller = loader.getController();
        controller.initialize(this.admin);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
        window.setTitle("Registrazione nuovo amministratore");
        window.setResizable(false);
        window.centerOnScreen();
        rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
    }

    @FXML
    public void handleEditUser(ActionEvent event) throws Exception {
        user.setUsername(user.getUsername());
        user.setTipo(2);
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
        refreshTables();
    }

    @FXML
    public void handleEditExcursion(ActionEvent event) throws Exception {
        escursione.setIDEscursione(escursione.getIDEscursione());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/ModificaEscursioneAdmin.fxml"));
        Parent root = loader.load();
        Scene rootScene = new Scene(root);
        ModificaEscursioneAdmin_Controller controller = loader.getController();
        controller.initialize(this.escursione, admin);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
        window.setTitle("Modifica Dati");
        window.setResizable(false);
        window.centerOnScreen();
        rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
        refreshTables();
    }

    @FXML
    public void newExcursion(ActionEvent event) throws Exception {
        admin.setIdAdmin(admin.getIdAdmin());
        FXMLLoader loaderEdit = new FXMLLoader();
        loaderEdit.setLocation(getClass().getResource("/ui/NuovaEscursione.fxml"));
        Parent rootEdit = (Parent) loaderEdit.load();
        Scene rootScene = new Scene(rootEdit);
        InserisciEscursione_Controller controller = loaderEdit.getController();
        controller.initialize(this.admin, escursione);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.setTitle("Crea Escursione");
        window.show();
        rootScene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
        refreshTables();
    }

   

    @FXML
    public void deleteUser(ActionEvent event) throws Exception {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Sicuri di voler cancellare questo utente?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            try {
                FC.processRequest(user, 3, 2);
                Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
                confirm.setTitle("Cancellazione Utente!");
                confirm.setHeaderText(null);
                confirm.setContentText("L'utente è stato correttamente cancellato");
                Optional<ButtonType> ok_ins = confirm.showAndWait();
                if (ok_ins.get() == ButtonType.OK) {
                    alert.close();
                    refreshTables();
                }
            } catch (Exception e) {
                alert.setTitle("Attenzione");
                alert.setHeaderText(null);
                alert.setContentText("Si è verificato un errore durante la cancellazione dell'utente.");
            }
        }
    }

    @FXML
    public void deleteAdmin(ActionEvent event) throws Exception {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Sicuri di voler cancellare questo amministratore?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            try {
                FC.processRequest(admin, 8, 2);
                Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
                confirm.setTitle("Cancellazione Amministratore!");
                confirm.setHeaderText(null);
                confirm.setContentText("L'amministratore è stato correttamente cancellato");
                Optional<ButtonType> ok_ins = confirm.showAndWait();
                if (ok_ins.get() == ButtonType.OK) {
                    alert.close();
                    refreshTables();
                }
            } catch (Exception e) {
                alert.setTitle("Attenzione");
                alert.setHeaderText(null);
                alert.setContentText("Si è verificato un errore durante la cancellazione dell'utente.");
            }
        }
    }

    public void deleteExcursion(ActionEvent event) throws Exception {
        System.out.println("ID escursione = " + escursione.getIDEscursione());
        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Annulla Escursione");
        alert.setHeaderText(null);
        alert.setContentText("Sei sicuro di voler eliminare questa escursione?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            try {
                FC.processRequest(escursione, 9, 2);
                Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
                confirm.setTitle("Cancellazione Escursione!");
                confirm.setHeaderText(null);
                confirm.setContentText("Escursione eliminata con successo.");
                Optional<ButtonType> ok_ins = confirm.showAndWait();
                if (ok_ins.get() == ButtonType.OK) {
                    alert.close();
                    refreshTables();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert confirm = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
                confirm.setTitle("Attenzione");
                confirm.setHeaderText(null);
                confirm.setContentText("Si è verificato un errore durante l'eliminazione della escursione.");
            }
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

    @FXML
    private void handleClose() {
        System.exit(0);
    }
}