package com.ahmetgeze.flightticket.model.response;

import org.springframework.http.HttpStatus;

import java.util.Collection;

public class MultipleSaveResponse extends BaseResponse {
    Collection<?> insertLists;
    public MultipleSaveResponse(HttpStatus status, String message, boolean success, Collection<?> insertLists) {
        super(status, message, success);
        this.insertLists = insertLists;
    }

    public Collection<?> getInsertLists() {
        return insertLists;
    }

    public void setInsertLists(Collection<?> insertLists) {
        this.insertLists = insertLists;
    }

}
