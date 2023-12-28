package org.dromara.dynamictp.common.ex;

public class DtpException extends RuntimeException {

    public DtpException() {
        super();
    }

    public DtpException(String message) {
        super(message);
    }

    public DtpException(String message, Throwable cause) {
        super(message, cause);
    }

    public DtpException(Throwable cause) {
        super(cause);
    }
}