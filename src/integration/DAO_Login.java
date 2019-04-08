package integration;

import java.sql.Connection;
import java.sql.SQLException;

import entity.Admin;
import entity.Utente;
import entity.Login;
import java.sql.ResultSet;

/**
 *
 * @author Panarosa Gaetano Matricola 609416
 */
public class DAO_Login implements DAO_Login_Interface {

	DAO_Abstract_Factory CustomerDAO = DAO_Factory.getDAOFactory(DAO_Factory.DAO_Login);

	private Utente utente;
	@SuppressWarnings("unused")
	private Admin admin;
	@SuppressWarnings("unused")
	private Login login;

	public Object Login(Object dati) {

		Login login = (Login) dati;

		try (Connection conn = DAO_Factory.createConnection()) {
			utente = new Utente();
			admin = new Admin();
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users WHERE username='"
					+ login.getUsername() + "' and password='" + login.getPassword() + "';");
			
			if(rs.wasNull()) {
				login.setStatus(0);
			}else {
				login.setStatus(1);
			}
			
			while (rs.next()) {
			
				
				if (rs.getInt("tipo") == 1) {
					ResultSet rs1 = conn.createStatement().executeQuery(
							"SELECT idUtente FROM utente WHERE users_idUsers='" + rs.getInt("idUsers") + "';");
					// utente.setTipo(rs.getInt("tipo"));
					while (rs1.next()) {
						login.setIDLogin(rs1.getInt("idUtente"));
						login.setUsername(rs.getString("username"));
						login.setPassword(rs.getString("password"));
						login.setTipo(rs.getInt("tipo"));
					}
					if (rs1.wasNull()) {
						System.out.println("Errore");
						return null;
					}
					

				} else {
					if (rs.getInt("tipo") == 2) {
						ResultSet rs2 = conn.createStatement().executeQuery(
								"SELECT idAdmin FROM admin WHERE users_idUsers='" + rs.getInt("idUsers") + "';");
						while (rs2.next()) {
							login.setIDLogin(rs2.getInt("idAdmin"));
							login.setUsername(rs.getString("username"));
							login.setPassword(rs.getString("password"));
							login.setTipo(rs.getInt("tipo"));
						}
					}
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return login;

	}

	public Object statusName(Object dati) {
		this.Login(utente.getNome());
		return dati;
	}

}
