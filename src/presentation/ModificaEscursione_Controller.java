package presentation;

import java.util.Iterator;

import entity.Escursione;
import entity.Optional;
import entity.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ModificaEscursione_Controller {

	private FrontController FC = FrontController.getInstance();

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
	
	private Utente user;
	private Escursione escursione;
	@SuppressWarnings("unused")
	private Optional optional;
	private int Totale;
	private String prezzoOptional;
	@SuppressWarnings("unused")
	private String Testo;
	@SuppressWarnings("unused")
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
	void modExcursion(ActionEvent event) throws Exception {
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



		Alert alert= new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Sei sicuro di voler modificare l'escursione? Il nuovo prezzo è di "+ Totale + " €");
		java.util.Optional<ButtonType>  action = alert.showAndWait();
		if(action.get() == ButtonType.OK) {	
			Stot=Integer.toString(Totale);
			Escursione modEscursione = new Escursione();
			modEscursione.setIDEscursione(escursione.getIDEscursione());		
			modEscursione.setIDUtente(user.getID());
			modEscursione.setOptional(optionalList);
			modEscursione.setPrezzo(Stot);
			FC.processRequest(modEscursione, 8, 1);
			
			Alert alert2= new Alert(AlertType.CONFIRMATION);
			alert2.setTitle("Confirmation Dialog");
			alert2.setHeaderText(null);
			alert2.setContentText("Modifica effettuata.");
			java.util.Optional<ButtonType>  action2 = alert2.showAndWait();
			if(action2.get() == ButtonType.OK) {	
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
		}else {
			if(action.get() ==  ButtonType.CANCEL) {
				Totale = Integer.parseInt(escursione.getPrezzo());
			}
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
