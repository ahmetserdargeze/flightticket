package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.dao.impl.AirportsDao;
import com.ahmetgeze.flightticket.entity.Airports;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.service.contract.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {
    @Autowired
    AirportsDao airportsDao;


    @Override
    public SaveResponse saveAirport(String name) {
        Airports airport = new Airports(name);
        airportsDao.saveAirport(airport);
        return new SaveResponse(HttpStatus.OK, "Save New Airport with Succes", true, airport);
    }

    @Override
    public SearchResponse searchAirport(String name) {
        List<Airports> airports =  airportsDao.searchAirportsWithName(name);
        return new SearchResponse(HttpStatus.OK, "Get Airports with Succes", true, airports);
    }


}
