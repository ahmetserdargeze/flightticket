package com.ahmetgeze.flightticket.dao.contract;

import com.ahmetgeze.flightticket.entity.Airports;

import java.util.List;

public interface AirportsDaoContract {
    List<Airports> searchAirportsWithName(String name);
    Boolean saveAirport(Airports airports);

}
