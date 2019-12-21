package task2;

public class SerializeExceptionClass extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public SerializeExceptionClass() {
        super();
    }
    public SerializeExceptionClass(String errorMessage) {
        super(errorMessage);
    }
    public SerializeExceptionClass(String errorCode, String errorMessage) {
        super(errorCode + ": " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
