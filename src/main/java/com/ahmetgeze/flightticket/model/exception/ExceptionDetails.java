package com.ahmetgeze.flightticket.model.exception;

import com.ahmetgeze.flightticket.model.response.BaseResponse;
import org.springframework.http.HttpStatus;

import java.util.Date;


public class ExceptionDetails extends BaseResponse {
    private ExceptionCategory exeptionCategory;
    private ExceptionCode exceptionCode;
    private String details;
    private Date exceptionTime;

    public ExceptionDetails(HttpStatus status, String message, boolean success, ExceptionCategory exeptionCategory, ExceptionCode exceptionCode, String details, Date exceptionTime) {
        super(status, message, success);
        this.exeptionCategory = exeptionCategory;
        this.exceptionCode = exceptionCode;
        this.details = details;
        this.exceptionTime = exceptionTime;
    }

    public ExceptionCategory getExeptionCategory() {
        return exeptionCategory;
    }

    public void setExeptionCategory(ExceptionCategory exeptionCategory) {
        this.exeptionCategory = exeptionCategory;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getExceptionTime() {
        return exceptionTime;
    }

    public void setExceptionTime(Date exceptionTime) {
        this.exceptionTime = exceptionTime;
    }
}
