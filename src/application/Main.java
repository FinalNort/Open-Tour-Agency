/**
 * @author Panarosa Gaetano Matricola 609416
 */
package application;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.Homepage_Controller;

/**
 * Classe Main per l'avvio del software.
 * Questa classe è responsabile dell'avvio della homepage tramite il file "Homepage.fxml"
 *
 */
public class Main extends Application {
	
	   @Override
		public void start(Stage stage) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/ui/Homepage.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
				Homepage_Controller controller = loader.getController();
				controller.initialize();
				stage.setScene(scene);
				stage.show();
				stage.setTitle("OpenTourAgency - Homepage");
				stage.setResizable(false);
				stage.centerOnScreen();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	public static void main(String[] args) {
		launch(args);
	}
}
