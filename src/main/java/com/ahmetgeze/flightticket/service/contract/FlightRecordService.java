package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.entity.FlightRecord;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;

import java.util.Date;
import java.util.UUID;

public interface FlightRecordService {
    SaveResponse createFlightRecord(String flightRecordName, UUID airlinesCompanyId, UUID routeId, int flightSeatCout, Date departureDate, Date arrivalDate);
    SearchResponse searchFlightRecord(UUID airlinesCompanyId,UUID routeId,Date depertureDate,Date arrivalDate);

}
