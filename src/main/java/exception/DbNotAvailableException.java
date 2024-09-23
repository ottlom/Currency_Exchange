package exception;

import javax.servlet.http.HttpServletResponse;

public class DbNotAvailableException extends BaseException implements StatusResponseSetter {
    public DbNotAvailableException(String message) {
        super(message);
    }

    @Override
    public final int getStatusResponse() {
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }
}