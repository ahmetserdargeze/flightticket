package com.ahmetgeze.flightticket.dao.impl;

import com.ahmetgeze.flightticket.dao.contract.AirportsDao;
import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.AirportRepository;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AirportsDaoImpl implements AirportsDao {

    @Autowired
    AirportRepository airportRepository;


    @Override
    public List<Airport> searchAirportsWithName(String name) {
        try {
            return airportRepository.findByNameContains(name);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.AIRPORT_SEARCH_ERR_1, e));
        }
    }

    @Override
    public Boolean saveAirport(Airport airport) {
        try {
            airportRepository.save(airport);
            return true;
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.AIRPORT_SAVE_ERR_1, e));
        }
    }

    @Override
    public Airport getByAirportId(UUID airportId) {
        if (UtilsFunc.isNotNull(airportId)) {
            try {
                return airportRepository.findById(airportId).get();
            }catch (Exception e){
                throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.AIRPORT_GET_ERR_1, e));
            }
        } else {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }
}
