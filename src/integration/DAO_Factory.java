package integration;

import java.sql.*;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DAO_Factory extends DAO_Abstract_Factory{

    @SuppressWarnings("unused")
	private static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DBURL = "jdbc:mysql://localhost:3306/mydb";
    public static final String USER = "opentouragency";
    public static final String PASS = "opentouragency";

	@Override
	public DAO_Utente getUtenteDAO() {
		createConnection();
		return new DAO_Utente();
	}

	@Override
	public DAO_Admin getAdminDAO() {
		createConnection();
		return new DAO_Admin();
	}

	@Override
	public DAO_Login getLoginDAO(){
		createConnection();
		return new DAO_Login();
	}
	
	public static Connection createConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DBURL, USER, PASS);
            System.out.println ("Database connection established");
        } catch (SQLException e) {
        	e.printStackTrace();
        	  Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setTitle("Attenzione");
              alert.setHeaderText(null);
              alert.setContentText("CONNESSIONE AL DATABASE FALLITA. "
                      + "SI PREGA DI CONTROLLARE LO STATO DELLA CONNESSIONE");
              Optional<ButtonType> error = alert.showAndWait();
          	if (error.get() == ButtonType.OK) {
				alert.close();
			}
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        	 Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setTitle("Attenzione");
             alert.setHeaderText(null);
             alert.setContentText("CONNESSIONE AL DATABASE FALLITA. "
                     + "SI PREGA DI CONTROLLARE LO STATO DELLA CONNESSIONE");
             Optional<ButtonType> error = alert.showAndWait();
         	if (error.get() == ButtonType.OK) {
				alert.close();
			}
        }
        return conn;
    }
}



   