package com.ahmetgeze.flightticket.dao.impl;

import com.ahmetgeze.flightticket.dao.contract.AirportsDaoContract;
import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.AirportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AirportsDao implements AirportsDaoContract {

    @Autowired
    AirportsRepository airportsRepository;


    @Override
    public List<Airport> searchAirportsWithName(String name) {
        try {
           return airportsRepository.findByNameContains(name.toUpperCase());
        }catch (Exception e){
            throw(new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.AIRPORT_SEARCH_ERR_1,e));
        }
    }

    @Override
    public Boolean saveAirport(Airport airport) {
        try {
            airportsRepository.save(airport);
            return true;
        }catch (Exception e){
            throw(new GeneralException(ExceptionCategory.DB_EXEPTİON,ExceptionCode.AIRPORT_SAVE_ERR_1,e));
        }
    }
}
