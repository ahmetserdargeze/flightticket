package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.dao.contract.AirportsDao;
import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
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
    public SaveResponse createAirport(String airportName) {
        if (UtilsFunc.isNotNull(airportName)) {
            Airport airport = new Airport();
            airport.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airportName));
            airportDao.saveAirport(airport);
            return new SaveResponse(HttpStatus.OK, "Save New Airport with Succes", true, airport);
        } else {
            throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }

    @Override
    public SearchResponse searchWithAirportName(String airportName) {
        if (UtilsFunc.isNotNull(airportName)) {
            List<Airport> airports = airportDao.searchAirportsWithName(UtilsFunc.toUpperCaseWithTurkishCharacter(airportName));
            return new SearchResponse(HttpStatus.OK, "Get Airports with Succes", true, airports);
        } else {
            throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }

    @Override
    public SearchResponse listAirports() {
        List<Airport> flightRecords = airportDao.listAirports();
        return new SearchResponse(HttpStatus.OK, !flightRecords.isEmpty() ? "List Airports  with Succes" : "Listed Airports with Succes but Resultset is empty", true, flightRecords);
    }

}



