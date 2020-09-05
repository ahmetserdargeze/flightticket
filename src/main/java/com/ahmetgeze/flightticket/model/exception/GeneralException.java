package com.ahmetgeze.flightticket.model.exception;

public class GeneralException extends RuntimeException {
    private ExceptionCategory category;
    private ExceptionCode code;
    private Exception exception;

    public GeneralException(ExceptionCategory category,ExceptionCode code, Exception exception) {
        super(exception.getMessage());
        this.category = category;
        this.code = code;
        this.exception = exception;
    }

    public ExceptionCategory getCategory() {
        return category;
    }

    public void setCategory(ExceptionCategory category) {
        this.category = category;
    }

    public ExceptionCode getCode() {
        return code;
    }

    public void setCode(ExceptionCode code) {
        this.code = code;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
