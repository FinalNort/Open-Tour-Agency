package integration;

import java.sql.*;
import java.text.ParseException;

import javafx.collections.ObservableList;

public interface DAO_Admin_Interface {
	public void regAdmin(Object userData) throws SQLException;
	public ObservableList<Object> showUsers() throws SQLException;
	public ObservableList<Object> showAdmins() throws SQLException;
	public Object deleteUser(Object delete) throws SQLException;
	public Object showAdminDetails(Object adminDetails) throws SQLException;
	public ObservableList<Object> getOptional(Object dati) throws SQLException;
	public ObservableList<Object> showEscursioni(Object escursioni) throws SQLException, ParseException;
	public Object insertExcursion(Object insert) throws SQLException;
	public Object deleteAdmin(Object request) throws SQLException;
	public void deleteExcursion(Object request) throws SQLException;
	public Object showExcursionDetails(Object exDetails) throws SQLException, ParseException;
	public Object modifyExcursion(Object modify) throws SQLException;
}
