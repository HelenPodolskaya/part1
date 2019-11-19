package task1;

public class MathBoxException  extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public MathBoxException() {
        super();
    }
    public MathBoxException(String errorMessage) {
        super(errorMessage);
    }
    public MathBoxException(String errorCode, String errorMessage) {
        super(errorCode + ": " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
