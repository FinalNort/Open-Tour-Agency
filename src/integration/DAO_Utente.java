package integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import entity.Escursione;
import entity.Optional;
import entity.Partecipazione;
import entity.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DAO_Utente implements DAO_Utente_Interface {

    @Override
    public Object regUtente(Object userData) throws SQLException {

        try (Connection conn = DAO_Factory.createConnection()) {
            Utente user = (Utente) userData;
            Statement stmn = conn.createStatement();
            stmn.executeUpdate("INSERT INTO users (username,password,tipo) " + "VALUES ('" + user.getUsername() + "','"
                    + user.getPassword() + "','1');");
            ResultSet rs = stmn.executeQuery("SELECT users.idUsers FROM users WHERE username='" + user.getUsername()
                    + "' and password='" + user.getPassword() + "';");
            rs.next();
            PreparedStatement ps2 = conn.prepareStatement(
                    "INSERT INTO utente (users_idUsers,nome,cognome,codiceFiscale,email,dataNascita,telefono,indirizzo,sesso,città,paese) "
                            + "VALUES ('" + rs.getInt("idUsers") + "','" + user.getNome() + "','" + user.getCognome()
                            + "','" + user.getCodiceFiscale() + "'," + "'" + user.getEmail() + "','" + user.getData()
                            + "','" + user.getTelefono() + "','" + user.getIndirizzo() + "','" + user.getSex() + "','"
                            + user.getTown() + "','" + user.getPaese() + "');");
            user.setID(rs.getInt("idUsers"));
            ps2.executeUpdate();
        }
        return userData;
    }

    @Override
    public Object userDetails(Object userDetails) throws SQLException {
        Utente user = (Utente) userDetails;

        try (Connection conn = DAO_Factory.createConnection();) {
            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT * FROM users WHERE username='" + user.getUsername() + "';");
            while (rs.next()) {
                ResultSet rs1 = conn.createStatement()
                        .executeQuery("SELECT * FROM utente WHERE users_idUsers ='" + rs.getString("idUsers") + "';");
                while (rs1.next()) {
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    user.setID(rs.getInt("idUsers"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setNome(rs1.getString("nome"));
                    user.setCognome(rs1.getString("cognome"));
                    user.setIndirizzo(rs1.getString("indirizzo"));
                    user.setCodiceFiscale(rs1.getString("codiceFiscale"));
                    user.setData(df.format(rs1.getDate("dataNascita")).toString());
                    user.setEmail(rs1.getString("email"));
                    user.setTelefono(rs1.getString("telefono"));
                    user.setPaese(rs1.getString("paese"));
                    user.setSex(rs1.getString("sesso"));
                    user.setTown(rs1.getString("città"));
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

        return userDetails;
    }

    @Override
    public Object updateUserDetails(Object updateDetails) throws SQLException, ParseException {

        Utente user = (Utente) updateDetails;

        try (Connection conn = DAO_Factory.createConnection();) {
        	
            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT * FROM users WHERE username='" + user.getUsername() + "';");
            while (rs.next()) {
            	 SimpleDateFormat dfIn = new SimpleDateFormat("dd-MM-yyyy");

            	 String input = user.getData();
            	 
                 Date date = dfIn.parse(input);

                 SimpleDateFormat dfOut = new SimpleDateFormat("yyyy-MM-dd");
                 
                conn.createStatement()
                        .executeUpdate("UPDATE utente SET nome ='" + user.getNome() + "'," + "cognome='"
                                + user.getCognome() + "', codiceFiscale ='" + user.getCodiceFiscale() + "', email ='"
                                + user.getEmail() + "'," + "dataNascita ='" + dfOut.format(date) + "', telefono ='"
                                + user.getTelefono() + "', indirizzo='" + user.getIndirizzo() + "'," + "sesso='"
                                + user.getSex() + "',città='" + user.getTown() + "',paese='" + user.getPaese() + "' "
                                + "WHERE idUtente ='" + rs.getString("idUsers") + "';");
                conn.createStatement()
                		.executeUpdate("UPDATE users SET username ='" + user.getUsername() + "',"
                				+ "password='" + user.getPassword() + "' WHERE idUsers='" + rs.getString("idUsers") + "';");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return updateDetails;
    }

    @Override
    public ObservableList<Object> userExcursions(Object dati) throws SQLException, ParseException {

        Connection conn = DAO_Factory.createConnection();
        
        ObservableList<Object> list1 = FXCollections.observableArrayList();
        Utente user = (Utente) dati;
        ResultSet rs = conn.createStatement()// stato 0 = non attivo, stato 1 =
                                             // attivo, stato 2 = in corso
                .executeQuery("SELECT * FROM partecipazioni AS par " + "INNER JOIN escursione AS es "
                        + "ON par.escursione = es.idEscursione " + "AND par.utente = '" + user.getID() + "' "
                        + "GROUP BY (es.codiceEscursione);");
        while (rs.next()) {
            ObservableList<Object> optionalList = FXCollections.observableArrayList();
            ResultSet rs1 = conn.createStatement()
                    .executeQuery("SELECT DISTINCT nomeOptional, prezzoOptional " + "FROM optional as opt "
                            + "INNER JOIN partecipazioni_optional as par_opt " + "ON par_opt.optional = opt.idOptional "
                            + "INNER JOIN partecipazioni as par " + "ON par.escursione = '"
                            + rs.getString("idEscursione") + "' AND par.idPartecipazioni = par_opt.partecipazione;");
            while (rs1.next()) {

                Optional optional = new Optional();
                optional.setOptional(rs1.getString("nomeOptional"));
                optional.setPrezzo(rs1.getString("prezzoOptional"));
                optionalList.add(optional);
            }

            ResultSet rs2 = conn.createStatement()
                    .executeQuery("SELECT tipo FROM tipoescursione WHERE idtipoEscursione ="
                            + rs.getInt("tipoescursione_idtipoEscursione") + ";");
            while (rs2.next()) {
                SimpleDateFormat dfIn = new SimpleDateFormat("yyyy-MM-dd");

                String input = rs.getString("dataEscursione");

                Date date = dfIn.parse(input);

                SimpleDateFormat dfOut = new SimpleDateFormat("dd-MM-yyyy");

                Escursione escursione = new Escursione();
                escursione.setIDEscursione(rs.getInt("idEscursione"));
                escursione.setCodiceEscursione(rs.getString("codiceEscursione"));
                escursione.setDataEscursione(dfOut.format(date));
                escursione.setTipoEscursione(rs2.getString("tipo"));
                escursione.setPrezzo(rs.getString("prezzoPartecipazione"));
                escursione.setOptional(optionalList);

                list1.add(escursione);
            }
        }
        return list1;

    }

    @Override
    public ObservableList<Object> avaiableExcursions(Object dati) throws SQLException, ParseException {

        Connection conn = DAO_Factory.createConnection();
        ;
        ObservableList<Object> list2 = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement()
                .executeQuery("SELECT * FROM escursione AS es " + "INNER JOIN escursione_stato as es_stato "
                        + "ON es.idEscursione = es_stato.escursione AND es_stato.stato = 2 "
                        + "INNER JOIN stato AS stato " + "ON es_stato.stato = stato.idStato;");

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
            escursione.setPostiDisponibili(rs.getString("postiDisponibili"));
            escursione.setPrezzo(rs.getString("prezzo"));
            escursione.setOptional(optionalList);

            list2.add(escursione);
        }

        return list2;

    }

    @Override
    public ObservableList<Object> getOptional(Object dati) throws SQLException {
        Connection conn = DAO_Factory.createConnection();
        Escursione escursione = (Escursione) dati;
        ObservableList<Object> obl = FXCollections.observableArrayList();
        try {
            Statement stmn = conn.createStatement();
            ResultSet rs = stmn.executeQuery("SELECT * FROM optional as opt,escursione_optional as es_opt "
                    + "WHERE opt.idOptional = es_opt.optional AND es_opt.escursione = '" + escursione.getIDEscursione()
                    + "';");
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
    public void iscrizioneEscursione(Object iscrizione) throws SQLException {

        Partecipazione partecipazione = (Partecipazione) iscrizione;
        Connection conn = DAO_Factory.createConnection();

        conn.createStatement()
                .executeUpdate("INSERT INTO partecipazioni (utente, escursione, prezzoPartecipazione) " + "VALUES ('"
                        + partecipazione.getIdUtente() + "'," + "'" + partecipazione.getIdEscursione() + "'," + "'"
                        + partecipazione.getPrezzo() + "');");

        ResultSet rs = conn.createStatement().executeQuery("SELECT MAX(idPartecipazioni) FROM partecipazioni;");
        while (rs.next()) {

            Iterator<Object> it = partecipazione.getOptional(1).iterator();
            for (int i = 0; it.hasNext(); it.next(), i++) {
                Optional optional = (Optional) partecipazione.getOptional(1).get(i);
                ResultSet rs1 = conn.createStatement().executeQuery(
                        "SELECT idOptional FROM optional " + "WHERE nomeOptional ='" + optional.getOptional() + "';");
                while (rs1.next()) {
                    if (optional.getStato())
                        conn.createStatement()
                                .executeUpdate("INSERT INTO partecipazioni_optional (optional,partecipazione) "
                                        + "VALUES ('" + rs1.getInt("idOptional") + "','"
                                        + rs.getInt("MAX(idPartecipazioni)") + "');");
                }
            }
        }

        conn.close();

    }

    @Override
    public void deleteExcursionUser(Object delete) throws SQLException {

        Connection conn = DAO_Factory.createConnection();
        Escursione escursione = (Escursione) delete;
        try {
            conn.createStatement().executeUpdate("DELETE FROM partecipazioni WHERE utente= " + escursione.getIDUtente()
                    + " " + "AND escursione=" + escursione.getIDEscursione() + ";");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText(null);
            alert.setContentText("Si è verificato un errore durante la cancellazione.");
            alert.showAndWait();
        }

        conn.close();
    }

    @Override
    public Object modExcursion(Object modify) throws SQLException {

        Connection conn = DAO_Factory.createConnection();
        Escursione escursione = (Escursione) modify;
        try {

            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT * FROM partecipazioni AS par "
                            + "INNER JOIN partecipazioni_optional AS par_opt "
                            + "ON par.idPartecipazioni = par_opt.partecipazione " + "INNER JOIN escursione AS es "
                            + "ON es.idEscursione ='" + escursione.getIDEscursione() + "' AND par.utente='"
                            + escursione.getIDUtente() + "';");

            while (rs.next()) {
                conn.createStatement().executeUpdate("DELETE FROM partecipazioni_optional " + "WHERE partecipazione= "
                        + rs.getInt("idPartecipazioni") + "  ;");

                Iterator<Object> it = escursione.getOptional(1).iterator();

                for (int i = 0; it.hasNext(); it.next(), i++) {
                    Optional optional = (Optional) escursione.getOptional(1).get(i);
                    ResultSet rs2 = conn.createStatement().executeQuery("SELECT idOptional FROM optional "
                            + "WHERE nomeOptional ='" + optional.getOptional() + "';");
                    while (rs2.next()) {
                        if (optional.getStato())
                            conn.createStatement()
                                    .executeUpdate("INSERT INTO partecipazioni_optional (optional, partecipazione) "
                                            + "VALUES ('" + rs2.getInt("idOptional") + "', " + "'"
                                            + rs.getInt("idPartecipazioni") + "');");
                    }
                }

                ResultSet rs3 = conn.createStatement()
                        .executeQuery("SELECT idPartecipazioni, escursione,idOptional, sum(prezzoOptional)  "
                                + "FROM  partecipazioni_optional AS par_opt, partecipazioni AS par, optional AS opt "
                                + "WHERE  par_opt.optional = opt.idOptional "
                                + "AND par_opt.partecipazione = par.idPartecipazioni " + "AND par.escursione= "
                                + escursione.getIDEscursione() + " " + "GROUP BY idPartecipazioni ;");

                while (rs3.next()) {
                    int nuovoPrezzo = rs.getInt("prezzo") + rs3.getInt("sum(prezzoOptional)");
                    String nuovo_prezzo = Integer.toString(nuovoPrezzo);
                    conn.createStatement()
                            .executeUpdate("UPDATE partecipazioni SET prezzoPartecipazione ='" + nuovo_prezzo + "' "
                                    + "WHERE partecipazioni.idPartecipazioni='" + rs3.getInt("idPartecipazioni")
                                    + "' ; ");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modify;
    }

    public Object deleteUser(Object deleteUser) throws SQLException {
        Connection conn = DAO_Factory.createConnection();
        Utente user = (Utente) deleteUser;
        conn.createStatement().executeUpdate("DELETE us, ut FROM users us INNER JOIN utente ut ON us.username = '"
                + user.getUsername() + "' WHERE us.idUsers = ut.users_idUsers;");
        return deleteUser;
    }
}
