package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.dao.contract.AirportsDaoContract;
import com.ahmetgeze.flightticket.dao.contract.RouteDaoContract;
import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.service.contract.RouteService;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    AirportsDaoContract airportDao;

    @Autowired
    RouteDaoContract routeDao;


    @Override
    public SaveResponse createRoute(UUID depertarueAirportId, UUID arrivalAirportId) {
        if (UtilsFunc.isNotNull(depertarueAirportId, arrivalAirportId)) {
            if (!depertarueAirportId.equals(arrivalAirportId)) {
                Airport depertureAirport = airportDao.getByAirportId(depertarueAirportId);
                Airport arrivalAirport = airportDao.getByAirportId(arrivalAirportId);
                Route route = new Route();
                route.setArrivalAirport(arrivalAirport);
                route.setDepartureAirport(depertureAirport);
                routeDao.saveRoute(route);
                return new SaveResponse(HttpStatus.OK, "Save New Route with Succes", true, route);
            } else {
                throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.EQUAL_DEPERTURE_ARRIVAL_ROUTE_ID, new Exception()));
            }
        } else {
            throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }
}
