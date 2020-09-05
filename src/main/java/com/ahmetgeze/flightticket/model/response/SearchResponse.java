package com.ahmetgeze.flightticket.model.response;

import org.springframework.http.HttpStatus;

import java.util.Collection;

public class SearchResponse extends BaseResponse {
    Collection<?> searchResult;
    public SearchResponse(HttpStatus status, String message, boolean success, Collection<?>  searchResult) {
        super(status, message, success);
        this.searchResult = searchResult;
    }

    public Collection<?> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(Collection<?> searchResult) {
        this.searchResult = searchResult;
    }
}
