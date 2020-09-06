package com.ahmetgeze.flightticket.dao.impl;

import com.ahmetgeze.flightticket.dao.contract.FlightRecordDao;
import com.ahmetgeze.flightticket.entity.FlightRecord;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.FlightRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightRecordDaoImpl implements FlightRecordDao {

    @Autowired
    FlightRecordRepository flightRecordRepository;

    @Override
    public FlightRecord saveFlightRecord(FlightRecord flightRecord) {
        try {
            flightRecord = flightRecordRepository.save(flightRecord);
            return flightRecord;
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.FLIGHT_RECORD_SAVE_ERR_1, e));
        }
    }
}
