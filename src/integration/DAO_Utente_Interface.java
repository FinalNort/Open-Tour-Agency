package integration;

import java.sql.*;
import java.text.ParseException;

import javafx.collections.ObservableList;

public interface DAO_Utente_Interface {
	public Object regUtente(Object userData) throws SQLException;
    public Object userDetails(Object userDetails) throws SQLException;
    public Object updateUserDetails(Object updateDetails) throws SQLException, ParseException;
    public ObservableList<Object> userExcursions(Object dati) throws SQLException, ParseException;
    public ObservableList<Object> avaiableExcursions(Object dati) throws SQLException, ParseException;
    public ObservableList<Object> getOptional(Object dati) throws SQLException;
    public void iscrizioneEscursione(Object iscrizione) throws SQLException;
    public void deleteExcursionUser(Object delete) throws SQLException;
    public Object modExcursion(Object modify) throws SQLException;
}
