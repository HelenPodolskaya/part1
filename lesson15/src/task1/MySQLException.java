package task1;

public class MySQLException extends java.sql.SQLException {
    private int errorCode;
    private String errorMessage;

    public MySQLException() {
        super();
    }
    public MySQLException(String errorMessage) {
        super(errorMessage);
    }
    public MySQLException(int errorCode, String errorMessage) {
        super(errorCode + ": " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
