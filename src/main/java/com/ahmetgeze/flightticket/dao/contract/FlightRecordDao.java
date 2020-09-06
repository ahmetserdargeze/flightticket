package com.ahmetgeze.flightticket.dao.contract;

import com.ahmetgeze.flightticket.entity.FlightRecord;

public interface FlightRecordDao {
    FlightRecord saveFlightRecord(FlightRecord flightRecord);

}
