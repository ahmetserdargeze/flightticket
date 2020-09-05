package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;

public interface AirportService{
    SaveResponse saveAirport(String name);
    SearchResponse searchAirport(String name);



}
