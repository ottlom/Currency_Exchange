package exception;

public class BaseException extends RuntimeException implements StatusResponseSetter {
    public BaseException(String message) {
        super(message);
    }

    @Override
    public int getStatusResponse() {
        return 0;
    }
}