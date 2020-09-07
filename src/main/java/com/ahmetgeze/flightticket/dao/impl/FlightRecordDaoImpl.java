package com.ahmetgeze.flightticket.dao.impl;

import com.ahmetgeze.flightticket.dao.contract.FlightRecordDao;
import com.ahmetgeze.flightticket.entity.FlightRecord;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.FlightRecordRepository;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class FlightRecordDaoImpl implements FlightRecordDao {

    @Autowired
    FlightRecordRepository flightRecordRepository;

    @Override
    public FlightRecord getById(UUID flightRecordId) {
        try {
            return flightRecordRepository.findById(flightRecordId).get();
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.FLIGHT_GET_ERR_1, e));
        }
    }

    @Override
    public FlightRecord saveFlightRecord(FlightRecord flightRecord) {
        try {
            flightRecord = flightRecordRepository.save(flightRecord);
            return flightRecord;
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.FLIGHT_RECORD_SAVE_ERR_1, e));
        }
    }

    @Override
    public List<FlightRecord> searchFlightRecordWithAirlinesCompanyAndRouteAndDepertureDateAndArrivalDate(FlightRecord flightRecord) {
        Date depertureEndDate = UtilsFunc.getNextDayDateFromDate(flightRecord.getDepartureDate());
        try {
            return flightRecordRepository.findByAirlinesCompanyIdAndRouteIdAndDepartureDateBetween(
                    flightRecord.getAirlinesCompany().getId(),
                    flightRecord.getRoute().getId(),
                    flightRecord.getDepartureDate(),
                    depertureEndDate);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.FLIGHT_RECORD_SEARCH_ERR_1, e));
        }
    }

    @Override
    public List<FlightRecord> listAllFlightRecord() {
        try {
            return flightRecordRepository.findAll();
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.FLIGHT_RECORD_SEARCH_ERR_2, e));
        }
    }
}
