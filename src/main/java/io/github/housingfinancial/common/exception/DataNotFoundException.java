package io.github.housingfinancial.common.exception;

public class DataNotFoundException extends RuntimeException {

    private final int httpStatus = 204; //NO_CONTENT

    public int getHttpStatus() {
        return httpStatus;
    }

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
