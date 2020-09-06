package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.dao.contract.AirlinesCompanyDao;
import com.ahmetgeze.flightticket.dao.contract.FlightRecordDao;
import com.ahmetgeze.flightticket.dao.contract.RouteDao;
import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import com.ahmetgeze.flightticket.entity.FlightRecord;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.service.contract.FlightRecordService;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class FlightRecordServiceImpl implements FlightRecordService {
    @Autowired
    FlightRecordDao flightRecordDao;

    @Autowired
    AirlinesCompanyDao airlinesCompanyDao;

    @Autowired
    RouteDao routeDao;


    @Override
    public SaveResponse createRoute(String flightRecordName, UUID airlinesCompanyId, UUID routeId, int flightSeatCout, Date departureDate, Date arrivalDate) {
        if (UtilsFunc.isNotNull(flightRecordName, airlinesCompanyId, routeId, flightSeatCout, departureDate, arrivalDate)) {
            if (arrivalDate.getTime() > departureDate.getTime()) {
                if (flightSeatCout > 0) {
                    Route route = routeDao.getById(routeId);
                    AirlinesCompany airlinesCompany = airlinesCompanyDao.getById(airlinesCompanyId);
                    FlightRecord flightRecord = new FlightRecord();
                    flightRecord.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(flightRecordName));
                    flightRecord.setAirlinesCompany(airlinesCompany);
                    flightRecord.setRoute(route);
                    flightRecord.setFligtSeatCount(flightSeatCout);
                    flightRecord.setDepartureDate(departureDate);
                    flightRecord.setArrivalDate(arrivalDate);
                    flightRecord = flightRecordDao.saveFlightRecord(flightRecord);
                    return new SaveResponse(HttpStatus.OK, "Save New Flight Record with Succes", true, flightRecord);

                } else {
                    throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.FLIGHT_RECORD_SEAT_COUNT_ERR_1, new Exception()));
                }

            } else {
                throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.FLIGHT_RECORD_DATE_ERR_1, new Exception()));
            }
        } else {
            throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }
}
