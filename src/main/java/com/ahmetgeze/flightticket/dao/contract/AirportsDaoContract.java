package com.ahmetgeze.flightticket.dao.contract;

import com.ahmetgeze.flightticket.entity.Airport;

import java.util.List;

public interface AirportsDaoContract {
    List<Airport> searchAirportsWithName(String name);
    Boolean saveAirport(Airport airport);

}
