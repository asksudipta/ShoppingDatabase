package se.lexicon.dbConnection;

import se.lexicon.exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {

    public static final String JDBC_URL="jdbc:mysql://localhost:3306/shopping_db";
    public static final String JDBC_USERNAME="root";
    public static final String JDBC_PASSWORD="12345";

    public static Connection getMySQLConnection() throws DatabaseConnectionException{
        Connection connection=null;
        try {
            connection= DriverManager.getConnection(JDBC_URL,JDBC_USERNAME,JDBC_PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage(),JDBC_URL);
        }
        return connection;
    }

}
