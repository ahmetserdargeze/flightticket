package com.ahmetgeze.flightticket.dao.contract;

import com.ahmetgeze.flightticket.entity.Airport;

import java.util.List;
import java.util.UUID;

public interface AirportsDao {
    List<Airport> searchAirportsWithName(String name);
    Boolean saveAirport(Airport airport);
    Airport getByAirportId(UUID airportId);

}
