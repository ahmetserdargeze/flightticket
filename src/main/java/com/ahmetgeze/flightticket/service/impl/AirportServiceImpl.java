package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.dao.contract.AirportsDao;
import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.service.contract.AirportService;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportServiceImpl implements AirportService {
    @Autowired
    AirportsDao airportDao;


    @Override
    public SaveResponse saveAirport(String name) {
        Airport airport = new Airport();
        airport.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(name));
        airportDao.saveAirport(airport);
        return new SaveResponse(HttpStatus.OK, "Save New Airport with Succes", true, airport);
    }

    @Override
    public SearchResponse searchAirport(String name) {
        List<Airport> airports =  airportDao.searchAirportsWithName(UtilsFunc.toUpperCaseWithTurkishCharacter(name));
        return new SearchResponse(HttpStatus.OK, "Get Airports with Succes", true, airports);
    }


}
