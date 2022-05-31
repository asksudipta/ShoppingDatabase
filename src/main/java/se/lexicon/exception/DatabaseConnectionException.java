package se.lexicon.exception;

public class DatabaseConnectionException  extends Exception{
    private String jdbcUrl;

    public DatabaseConnectionException(String message,String jdbcUrl){
        super(message);
        this.jdbcUrl=jdbcUrl;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }
}
