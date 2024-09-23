package exception;

import javax.servlet.http.HttpServletResponse;

public class EmptyDataFormException extends BaseException {
    public EmptyDataFormException(String message) {
        super(message);
    }

    @Override
    public final int getStatusResponse() {
        return HttpServletResponse.SC_BAD_REQUEST;
    }
}