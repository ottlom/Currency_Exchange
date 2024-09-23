package exception;

import javax.servlet.http.HttpServletResponse;

public class NotExistStorageException extends BaseException {
    public NotExistStorageException(String message) {
        super(message);
    }

    @Override
    public final int getStatusResponse() {
        return HttpServletResponse.SC_NOT_FOUND;
    }
}