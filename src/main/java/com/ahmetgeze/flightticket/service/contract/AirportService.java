package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;

public interface AirportService{
    SaveResponse createAirport(String airportName);
    SearchResponse searchWithAirportName(String airportName);



}
