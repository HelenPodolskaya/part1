package task1;
import java.util.InputMismatchException;

public class PetLibException extends Exception {
    private String errorCode;
    private String errorMessage;

    public PetLibException() {
        super();
    }
    public PetLibException(String errorMessage) {
        super(errorMessage);
    }
    public PetLibException(String errorCode, String errorMessage) {
        super(errorCode + ": " + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
