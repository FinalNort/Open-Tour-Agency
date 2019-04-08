package presentation;

import java.util.Iterator;

import entity.Escursione;
import entity.Optional;
import entity.Partecipazione;
import entity.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class IscrizioneEscursione_Controller{

	@FXML
	private Button iscrivi;
	@FXML
	private Button annulla;
	@FXML
	private CheckBox Optional1;
	@FXML
	private CheckBox Optional2;
	@FXML
	private CheckBox Optional3;
	@FXML
	private Label prezzo1;
	@FXML
	private Label prezzo2;
	@FXML
	private Label prezzo3;
	@FXML
	private Label prezzoBase;
	@FXML
	private ListView<CheckBox> ViewOptional;

	
	
	private FrontController FC = FrontController.getInstance();

	private Utente user;
	private Escursione escursione;
	@SuppressWarnings("unused")
	private Optional optional;
	private int Totale;
	private String prezzoOptional;
	private String Testo;
	private String Stot;
	private ObservableList<CheckBox> checkboxOptional = FXCollections.observableArrayList();

	private ObservableList<Object> optionalList = FXCollections.observableArrayList();
	private ObservableList<Object> optionalSelect;
	
	
	public void initialize(Utente user, Escursione escursione) {
		this.user = user;
		this.escursione = escursione;
		Totale = Integer.parseInt(escursione.getPrezzo());
		prezzoBase.setText(""+ Totale);
		showOptional();
	}

	@FXML
	public void showOptional() {
		Object obl1 = FC.processRequest(escursione, 5, 1);
		@SuppressWarnings("unchecked")
		ObservableList<Object> obl = (ObservableList<Object>) obl1;
		optionalSelect = obl;
		Iterator<Object> it = obl.iterator();
		for (int i = 0; it.hasNext(); it.next(), i++) {
			entity.Optional opt = (entity.Optional) obl.get(i);
			checkboxOptional.add(new CheckBox(opt.getIDOptional()));
			prezzoOptional = opt.getPrezzo();
			checkboxOptional.get(i).setText(opt.getOptional() + ":" + prezzoOptional + " €");
			checkboxOptional.get(i).setSelected(false);
		}

		ViewOptional.setItems(checkboxOptional);
	}

	
	@FXML
	public void iscrivi(ActionEvent event) throws Exception {

		FC = FrontController.getInstance();

		Iterator<CheckBox> it = ViewOptional.getItems().iterator();

		for (int i = 0; it.hasNext(); it.next(), i++) {

			CheckBox opt = (CheckBox) ViewOptional.getItems().get(i);

			Optional opt1 = (Optional) optionalSelect.get(i);

			if (opt.isSelected() == true) {

				Totale=Totale+Integer.parseInt(opt1.getPrezzo());
			}

			Testo = opt.getText();

			String[] parts = Testo.split(":");

			String part1 = parts[0];

			Optional optional = new Optional();
			optional.setIDOptional(opt.getId());
			optional.setOptional(part1);
			optional.setPrezzo(opt1.getPrezzo());
			optional.setStato(opt.isSelected());

			optionalList.add(optional);

			Testo = "";

		}

		Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Registra Escursione");
		alert.setHeaderText(null);
		alert.setContentText("Confermare l'iscrizione al costo totale di euro " + Totale + "?");

		java.util.Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.YES) {
			Stot=Integer.toString(Totale);
			Partecipazione partecipazione = new Partecipazione();
			partecipazione.setIdUtente(user.getID());
			partecipazione.setIdEscursione(escursione.getIDEscursione());
			partecipazione.setPrezzo(Stot);
			partecipazione.setOptional(optionalList);
			FC.processRequest(partecipazione, 6, 1);
		
		user.setID(user.getID());
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

	@FXML
	public void handleBack(ActionEvent event) throws Exception {
		Stage stage = (Stage) annulla.getScene().getWindow();
		stage.close();
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
