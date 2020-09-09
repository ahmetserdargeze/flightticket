package com.ahmetgeze.flightticket.dao.contract;

import com.ahmetgeze.flightticket.entity.FlightRecord;

import java.util.List;
import java.util.UUID;

public interface FlightRecordDao {
    FlightRecord getById(UUID routeId);


    FlightRecord saveFlightRecord(FlightRecord flightRecord);

    List<FlightRecord> searchFlightRecordWithAirlinesCompanyAndRouteAndDepertureDateAndArrivalDate(FlightRecord flightRecord);

    List<FlightRecord> listAllFlightRecord();

}
