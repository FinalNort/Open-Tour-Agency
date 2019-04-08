package presentation;

import java.util.Iterator;

import entity.Admin;
import entity.Escursione;
import entity.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import presentation.FrontController;


public class InserisciEscursione_Controller {

	@FXML
	private Button crea;
	@FXML
	private Button chiudi;
	@FXML
	private TextField codice;
	@FXML
	private TextField minPartecipanti;
	@FXML
	private TextField maxPartecipanti;
	@FXML
	private TextField prezzoBase;
	@FXML
	private TextField posti;
	@FXML
	private ListView<CheckBox> viewOptional;
	@FXML
	private ComboBox<String> stato;
	@FXML
	private ComboBox<String> tipo;
	@FXML
	private DatePicker data;
	
	private FrontController FC = FrontController.getInstance();
	
	private Escursione escursione;
	private Admin admin;
	@SuppressWarnings("unused")
	private Optional optional;
	private int Totale;
	private String prezzoOptional;
	private String Testo;
	private String Stot;
	private ObservableList<CheckBox> checkboxOptional = FXCollections.observableArrayList();

	private ObservableList<Object> optionalList = FXCollections.observableArrayList();
	private ObservableList<Object> optionalSelect;
	
	public void initialize(Admin admin, Escursione escursione) {
		this.escursione = escursione;
		this.admin = admin;
		assert stato != null : "fx:id=\"stato\" was not injected: check your FXML file 'NuovaEscursione.fxml'.";
		assert tipo != null : "fx:id=\"tipo\" was not injected: check your FXML file 'NuovaEscursione.fxml'.";
		showOptional();
	}
	
	@FXML
	public void showOptional() {
		Object obl1 = FC.processRequest(escursione, 7, 2);
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

		viewOptional.setItems(checkboxOptional);
	}
	
	
	public void insertExcursion(ActionEvent event) throws Exception {
		
		Escursione escursione = new Escursione();
	
		Iterator<CheckBox> it = viewOptional.getItems().iterator();

		for (int i = 0; it.hasNext(); it.next(), i++) {

			CheckBox opt = (CheckBox) viewOptional.getItems().get(i);
			Optional optional = new Optional();
			Optional opt1 = (Optional) optionalSelect.get(i);

			if (opt.isSelected() == true) {

				Totale=Totale+Integer.parseInt(opt1.getPrezzo());
			}

			Testo = opt.getText();

			String[] parts = Testo.split(":");

			String part1 = parts[0];

		
			Stot=Integer.toString(Totale);
			optional.setIDOptional(opt.getId());
			optional.setOptional(part1);
			optional.setPrezzo(Stot);
			optional.setStato(opt.isSelected());

			optionalList.add(optional);

			Testo = "";
	}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Alert");
		alert.setHeaderText(null);
		alert.setContentText("Sei sicuro di voler confermare i dati immessi?");

		java.util.Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {
			
		escursione.setIDAdmin(admin.getIdAdmin());
		escursione.setCodiceEscursione(codice.getText());
		escursione.setDataEscursione(data.getValue().toString());
		escursione.setMinPartecipanti(minPartecipanti.getText());
		escursione.setMaxPartecipanti(maxPartecipanti.getText());
		escursione.setPostiDisponibili(posti.getText());
		escursione.setPrezzo(prezzoBase.getText());
		escursione.setPrezzo(prezzoBase.getText());
		String status = stato.getValue();

		System.out.println("" + status);

		if (status.contentEquals("Non Attiva")) {
			escursione.setIDStato(1);
		} else if (status.contentEquals("Attiva")) {
			escursione.setIDStato(2);
		} else if (status.contentEquals("In Corso")) {
			escursione.setIDStato(3);
		}
		escursione.setOptional(optionalList);

		String tipologia = tipo.getValue();

		System.out.println("tipologia " + tipologia);

		if (tipologia.contentEquals("Gita a Cavallo")) {
			escursione.setIDTipoEscursione(2);
			System.out.println("idtipologia " + escursione.getIDTipoEscursione());
		} else if (tipologia.contentEquals("Gita in Barca")) {
			escursione.setIDTipoEscursione(1);
			System.out.println("idtipologia " + escursione.getIDTipoEscursione());
		}

		
		escursione.setOptional(optionalList);
		
	
			
			FC.processRequest(escursione, 6, 2);
			Alert alert2 = new Alert(AlertType.CONFIRMATION);
			alert2.setTitle("Operazione Eseguita!");
			alert2.setHeaderText(null);
			alert2.setContentText("La nuova escursione è stata creata con successo!");
			java.util.Optional<ButtonType>  action2 = alert2.showAndWait();
			if (action2.get() == ButtonType.OK) {
			admin.setIdAdmin(admin.getIdAdmin());
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
	}
	@FXML
	public void handleBack(ActionEvent event) throws Exception {
		Stage stage = (Stage) chiudi.getScene().getWindow();
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
