package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.model.response.SaveResponse;

import java.util.Date;
import java.util.UUID;

public interface FlightRecordService {
    SaveResponse createRoute(String flightRecordName, UUID airlinesCompanyId, UUID routeId, int flightSeatCout, Date departureDate, Date arrivalDate);

}
