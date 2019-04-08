/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import entity.Admin;
import entity.Escursione;
import entity.Optional;
import entity.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.text.ParseException;

/**
 *
 * @author panar
 */
public class DAO_Admin implements DAO_Admin_Interface {

	@Override
	public void regAdmin(Object userData) throws SQLException {

		try (Connection conn = DAO_Factory.createConnection()) {
			Admin admin = (Admin) userData;
			Statement stmn = conn.createStatement();
			
			stmn.executeUpdate("INSERT INTO users (username,password,tipo) " + "VALUES ('" + admin.getUsername() + "','"
					+ admin.getPassword() + "','2');");
			
			ResultSet rs = stmn.executeQuery("SELECT users.idUsers FROM users WHERE username='" + admin.getUsername()
					+ "' and password='" + admin.getPassword() + "';");
			
			while(rs.next()) {
				conn.createStatement().executeUpdate("INSERT INTO admin (nome,cognome,email,users_idUsers) "
					+ "VALUES ('" + admin.getNome() + "','" + admin.getCognome() + "'," + "'" + admin.getEmail() + "','"
					+ rs.getString("idUsers") + "');");
			}
			
		}

	}

	@Override
	public ObservableList<Object> showUsers() throws SQLException {

		Connection conn = DAO_Factory.createConnection();

		ObservableList<Object> userlist = FXCollections.observableArrayList();

		try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM utente AS ut"
				+ " INNER JOIN users AS us ON ut.users_idUsers = us.idUsers AND us.tipo = '1';")) {
	
			while (rs.next()) {
				
				/*SimpleDateFormat dfIn = new SimpleDateFormat("yyyy-MM-dd");

	        	 String input = rs.getString("dataNascita");
	        	 
	             Date date = dfIn.parse(input);

	             SimpleDateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy");*/
	             
				Utente utente = new Utente();
				utente.setID(rs.getInt("idUtente"));
				utente.setUsername(rs.getString("username"));
				utente.setPassword(rs.getString("password"));
				utente.setNome(rs.getString("nome"));
				utente.setCognome(rs.getString("cognome"));
				utente.setData(/*dfOut.format(date)*/rs.getString("dataNascita"));
				utente.setCodiceFiscale(rs.getString("codiceFiscale"));
				utente.setTelefono(rs.getString("telefono"));
				utente.setEmail(rs.getString("email"));
				utente.setSex(rs.getString("sesso"));
				utente.setIndirizzo(rs.getString("indirizzo"));
				utente.setTown(rs.getString("città"));
				utente.setPaese(rs.getString("paese"));
				userlist.add(utente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userlist;
	}

	@Override
	public ObservableList<Object> showAdmins() throws SQLException {

		Connection conn = DAO_Factory.createConnection();

		ObservableList<Object> adminlist = FXCollections.observableArrayList();

		try (ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM admin AS ad "
				+ "INNER JOIN users AS us ON us.idUsers = ad.users_idUsers AND us.tipo = '2';")) {

			while (rs.next()) {
				Admin admin = new Admin();
				admin.setIdAdmin(rs.getInt("idadmin"));
				admin.setUsername(rs.getString("username"));
				admin.setPassword(rs.getString("password"));
				admin.setNome(rs.getString("nome"));
				admin.setCognome(rs.getString("cognome"));
				admin.setEmail(rs.getString("email"));
				adminlist.add(admin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adminlist;
	}

	@Override
	public Object deleteUser(Object delete) throws SQLException {

		Connection conn = DAO_Factory.createConnection();
		Utente user = (Utente) delete;
		conn.createStatement().executeUpdate("DELETE us, ut FROM users us INNER JOIN utente ut ON us.username = '"
				+ user.getUsername() + "' WHERE us.idUsers = ut.users_idUsers;");
		return delete;
	}

	@Override
	public Object showAdminDetails(Object adminDetails) throws SQLException {
		Admin admin = (Admin) adminDetails;
		try (Connection conn = DAO_Factory.createConnection()) {
			ResultSet rs = conn.createStatement()
					.executeQuery("SELECT * FROM users WHERE username='" + admin.getUsername() + "';");
			while (rs.next()) {
				ResultSet rs1 = conn.createStatement()
						.executeQuery("SELECT * FROM admin WHERE users_idUsers ='" + rs.getString("idUsers") + "';");
				while (rs1.next()) {
					admin.setIdAdmin(rs1.getInt("idAdmin"));
					admin.setUsername(rs.getString("username"));
					admin.setPassword(rs.getString("password"));
					admin.setNome(rs1.getString("nome"));
					admin.setCognome(rs1.getString("cognome"));
					admin.setEmail(rs1.getString("email"));
				}
				if (rs1.wasNull()) {
					System.out.println("Errore");
					return null;
				}
			}
			if (rs.wasNull()) {
				System.out.println("Errore");
				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return adminDetails;
	}

	@Override
	public ObservableList<Object> getOptional(Object dati) throws SQLException {
		Connection conn = DAO_Factory.createConnection();
		ObservableList<Object> obl = FXCollections.observableArrayList();
		try {
			Statement stmn = conn.createStatement();
			ResultSet rs = stmn.executeQuery("SELECT * FROM optional;");
			while (rs.next()) {
				Optional optional = new Optional();
				optional.setIDOptional(rs.getString("idOptional"));
				optional.setOptional(rs.getString("nomeOptional"));
				optional.setPrezzo(rs.getString("prezzoOptional"));

				obl.add(optional);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obl;
	}

	@Override
	public ObservableList<Object> showEscursioni(Object escursioni) throws SQLException, ParseException {

		Connection conn = DAO_Factory.createConnection();

		ObservableList<Object> list2 = FXCollections.observableArrayList();
		ResultSet rs = conn.createStatement()
				.executeQuery("SELECT * FROM escursione AS es " + "INNER JOIN escursione_stato as es_stato "
						+ "ON es.idEscursione = es_stato.escursione "
						+ "INNER JOIN stato AS stato " 
						+ "ON es_stato.stato = stato.idStato;");

		while (rs.next()) {
			
			ObservableList<Object> optionalList = FXCollections.observableArrayList();
			ResultSet rs1 = conn.createStatement()
					.executeQuery("SELECT DISTINCT nomeOptional, prezzoOptional FROM optional as opt "
							+ "INNER JOIN escursione_optional as es_opt " + "ON es_opt.escursione = "
							+ rs.getString("idEscursione") + " AND es_opt.optional = opt.idOptional;");
			while (rs1.next()) {

				Optional optional = new Optional();
				optional.setOptional(rs1.getString("nomeOptional"));
				optional.setPrezzo(rs1.getString("prezzoOptional"));
				optionalList.add(optional);
			}

			ResultSet rs2 = conn.createStatement().executeQuery("SELECT tipo FROM tipoescursione "
					+ "WHERE idtipoEscursione =" + rs.getInt("tipoescursione_idtipoEscursione") + ";");
			rs2.next();
			
			SimpleDateFormat dfIn = new SimpleDateFormat("yyyy-MM-dd");
			
			String input = rs.getString("dataEscursione");

			Date date = dfIn.parse(input);

			SimpleDateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy");
			 
			Escursione escursione = new Escursione();

			escursione.setIDEscursione(rs.getInt("idEscursione"));
			escursione.setCodiceEscursione(rs.getString("codiceEscursione"));
			escursione.setDataEscursione(dfOut.format(date));
			escursione.setTipoEscursione(rs2.getString("tipo"));
			escursione.setMinPartecipanti(rs.getString("minPartecipanti"));
			escursione.setMaxPartecipanti(rs.getString("maxPartecipanti"));
			escursione.setPostiDisponibili(rs.getString("postiDisponibili"));
			escursione.setPrezzo(rs.getString("prezzo"));
			escursione.setOptional(optionalList);
			escursione.setStato(rs.getString("stato.stato"));

			list2.add(escursione);
		}

		return list2;

	}


	@Override
	public Object insertExcursion(Object insert) throws SQLException {

		Connection conn = DAO_Factory.createConnection();

		Escursione escursione = (Escursione) insert;

		try {

			ResultSet rs = conn.createStatement()
					.executeQuery("SELECT * FROM tipoescursione AS te " + "INNER JOIN stato as s " + "ON te.idtipoEscursione = '"
							+ escursione.getIDTipoEscursione() + "' AND s.idStato = '" + escursione.getIDStato() + "';");

			while (rs.next()) {

				conn.createStatement()
						.executeUpdate("INSERT INTO escursione "
								+ "(codiceEscursione,dataEscursione,maxPartecipanti,minPartecipanti,prezzo,"
								+ " postiDisponibili,admin,tipoescursione_idtipoEscursione) " + "VALUES ('"
								+ escursione.getCodiceEscursione() + "','" + escursione.getDataEscursione() + "'," + "'"
								+ escursione.getMaxPartecipanti() + "','" + escursione.getMinPartecipanti() + "'," + "'"
								+ escursione.getPrezzo() + "','" + escursione.getPostiDisponibili() + "'," + "'"
								+ escursione.getIDAdmin() + "','" + rs.getInt("idtipoEscursione") + "');");

				ResultSet rs2 = conn.createStatement().executeQuery("SELECT idEscursione FROM escursione "
						+ "WHERE codiceEscursione = '" + escursione.getCodiceEscursione() + "';");

				while (rs2.next()) {
					conn.createStatement().executeUpdate("INSERT INTO escursione_stato (escursione,stato)" + "VALUES ('"
							+ rs2.getInt("idEscursione") + "','" + rs.getInt("idStato") + "');");

					Iterator<Object> it = escursione.getOptional(1).iterator();

					for (int i = 0; it.hasNext(); it.next(), i++) {
						Optional optional = (Optional) escursione.getOptional(1).get(i);
						ResultSet rs3 = conn.createStatement().executeQuery("SELECT idOptional FROM optional "
								+ "WHERE nomeOptional ='" + optional.getOptional() + "';");
						while (rs3.next()) {
							if (optional.getStato())
								conn.createStatement()
										.executeUpdate("INSERT INTO escursione_optional (escursione,optional) "
												+ "VALUES ('" + rs2.getInt("idEscursione") + "','"
												+ rs3.getInt("idOptional") + "');");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getLocalizedMessage();
			e.getCause();
		}

		return insert;
	}

	
	@Override
	public Object deleteAdmin(Object delete) throws SQLException{
		try(Connection conn = DAO_Factory.createConnection()){
		Admin admin = (Admin) delete;
		conn.createStatement().executeUpdate("DELETE us, admin FROM users us INNER JOIN admin admin ON us.username = '"
				+ admin.getUsername() + "' WHERE us.idUsers = admin.users_idUsers;");
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return delete;
	}
	
	@Override
	public void deleteExcursion(Object delete) throws SQLException{
		Connection conn = DAO_Factory.createConnection();
		Escursione escursione = (Escursione) delete;
		
		try {
			conn.createStatement().executeUpdate("DELETE FROM escursione WHERE idEscursione = '"+escursione.getIDEscursione()+"' "
					+ "AND codiceEscursione = '"+escursione.getCodiceEscursione()+"';");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Object showExcursionDetails(Object exDetails) throws SQLException, ParseException {
		Connection conn = DAO_Factory.createConnection();
		Escursione es = (Escursione) exDetails;
		ResultSet rs = conn.createStatement()
				.executeQuery("SELECT * FROM escursione AS es INNER JOIN escursione_stato as es_stato "
						+ "ON es.idEscursione = '"+es.getIDEscursione()+"' AND  es.idEscursione = es_stato.escursione  "
						+ "INNER JOIN stato AS stato " 
						+ "ON es_stato.stato = stato.idStato;");
		
		while (rs.next()) {

			ResultSet rs2 = conn.createStatement().executeQuery("SELECT tipo FROM tipoescursione "
					+ "WHERE idtipoEscursione =" + rs.getInt("tipoescursione_idtipoEscursione") + ";");
			rs2.next();
			SimpleDateFormat dfIn = new SimpleDateFormat("yyyy-MM-dd");

			String input = rs.getString("dataEscursione");

			Date date = dfIn.parse(input);

			SimpleDateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy");

			Escursione escursione = new Escursione();

			escursione.setIDEscursione(rs.getInt("idEscursione"));
			escursione.setCodiceEscursione(rs.getString("codiceEscursione"));
			escursione.setDataEscursione(dfOut.format(date));
			escursione.setTipoEscursione(rs2.getString("tipo"));
			escursione.setMinPartecipanti(rs.getString("minPartecipanti"));
			escursione.setMaxPartecipanti(rs.getString("maxPartecipanti"));
			escursione.setPostiDisponibili(rs.getString("postiDisponibili"));
			escursione.setPrezzo(rs.getString("prezzo"));
			escursione.setIDStato(rs.getInt("idStato"));
			escursione.setStato(rs.getString("stato"));
		}
		return exDetails;
	}

	@Override
	public Object modifyExcursion(Object modify) throws SQLException {
		
		Connection conn = DAO_Factory.createConnection();

		Escursione escursione = (Escursione) modify;

		try {
			
			ResultSet rs = conn.createStatement()
					.executeQuery("SELECT * FROM escursione AS es INNER JOIN escursione_stato as es_stato "
							+ "ON es.codiceEscursione = '"+escursione.getCodiceEscursione()+"' AND  es.idEscursione = es_stato.escursione  "
							+ "INNER JOIN stato AS stato " 
							+ "ON es_stato.stato = stato.idStato;");
						
			while (rs.next()) {
				/*ResultSet rs1 = conn.createStatement().executeQuery("SELECT * FROM tipoescursione "
						+ "WHERE idtipoEscursione = '" + escursione.getIDTipoEscursione()+ "';");
				
				while(rs1.next()) {*/
				conn.createStatement().executeUpdate("DELETE FROM escursione_optional " + "WHERE escursione= "
						+ rs.getInt("idEscursione") + "  ;");
				
				conn.createStatement().executeUpdate("DELETE FROM escursione_stato " + "WHERE escursione = '"
						+ rs.getInt("idEscursione") +"';");
		
				
				Iterator<Object> it = escursione.getOptional(1).iterator();

				for (int i = 0; it.hasNext(); it.next(), i++) {
					Optional optional = (Optional) escursione.getOptional(1).get(i);
					ResultSet rs2 = conn.createStatement().executeQuery("SELECT idOptional FROM optional "
							+ "WHERE nomeOptional ='" + optional.getOptional() + "';");
					while (rs2.next()) {
						if (optional.getStato())
							conn.createStatement()
									.executeUpdate("INSERT INTO escursione_optional (optional, escursione) "
											+ "VALUES ('" + rs2.getInt("idOptional") + "', " + "'"
											+ rs.getInt("idEscursione") + "');");
					}
				}
				
				SimpleDateFormat dfIn = new SimpleDateFormat("yyyy-MM-dd");

				String input = escursione.getDataEscursione();

				Date date = dfIn.parse(input);

				SimpleDateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy");
				
					conn.createStatement().executeUpdate("UPDATE escursione "
						+ "SET codiceEscursione = '"+escursione.getCodiceEscursione()+"',"
								+ "dataEscursione = '"+dfOut.format(date) +"',"
								+ "maxPartecipanti = '"+escursione.getMaxPartecipanti()+"',"
								+ "minPartecipanti = '"+escursione.getMinPartecipanti()+"',"
								+ "prezzo = '"+escursione.getPrezzo()+"',"
								+ "postiDisponibili = '"+escursione.getPostiDisponibili()+"',"
								+ "admin = '"+escursione.getIDAdmin()+"',"
								+ "tipoescursione_idtipoEscursione = '"+escursione.getIDTipoEscursione()+/*rs1.getInt("idtipoEscursione")+*/"' " 
								+ "WHERE idEscursione= '" +rs.getInt("idEscursione") + "';");

				
				conn.createStatement().executeUpdate("INSERT INTO escursione_stato (escursione, stato)" + "VALUES ('"
						+ rs.getInt("idEscursione") + "','" + escursione.getIDStato() + "');");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getLocalizedMessage();
			e.getCause();
		}

		
		return modify;
	}

}
