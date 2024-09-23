package exception;

import javax.servlet.http.HttpServletResponse;

public class ExistStorageException extends BaseException {
    public ExistStorageException(String message) {
        super(message);
    }

    @Override
    public final int getStatusResponse() {
        return HttpServletResponse.SC_CONFLICT;
    }
}