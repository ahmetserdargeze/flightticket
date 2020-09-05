package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.model.exception.ExceptionDetails;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<ExceptionDetails> handleGeneralException(GeneralException ex, WebRequest request){
        ExceptionDetails details = new ExceptionDetails(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(),false,ex.getCategory(),ex.getCode(), ExceptionUtils.getStackTrace(ex),new Date());
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
