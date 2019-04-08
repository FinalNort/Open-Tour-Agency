package integration;

import java.sql.*;

public interface DAO_Login_Interface {
    public Object Login(Object dati) throws SQLException;
    public Object statusName(Object dati);
}
