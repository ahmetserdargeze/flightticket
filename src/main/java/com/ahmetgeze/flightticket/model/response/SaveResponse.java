package com.ahmetgeze.flightticket.model.response;
import org.springframework.http.HttpStatus;

public class SaveResponse extends BaseResponse {
    private Object insertedObject;

    public SaveResponse(HttpStatus status, String message, boolean success, Object insertedObject) {
        super(status, message, success);
        this.insertedObject = insertedObject;
    }

    public Object getInsertedObject() {
        return insertedObject;
    }

    public void setInsertedObject(Object insertedObject) {
        this.insertedObject = insertedObject;
    }
}
