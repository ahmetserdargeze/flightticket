package com.ahmetgeze.flightticket.dao.impl;


import com.ahmetgeze.flightticket.dao.contract.RouteDaoContract;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RouteDaoImpl implements RouteDaoContract {

    @Autowired
    RouteRepository routeRepository;


    @Override
    public Boolean saveRoute(Route route) {
        try {
            routeRepository.save(route);
            return true;
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.ROUTE_SAVE_ERR_1, e));
        }
    }

    @Override
    public List<Route> filterRouteWithArrivalId(UUID arrivalId) {
        try {
            return routeRepository.findByArrivalAirportId(arrivalId);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.ROUTE_FILTER_ERR_1, e));
        }
    }

    @Override
    public List<Route> filterRouteWithDepertureId(UUID depertureId) {
        try {
            return routeRepository.findByDepartureAirportId(depertureId);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.ROUTE_FILTER_ERR_2, e));
        }
    }

    @Override
    public List<Route> searchWithDepartureAirportName(String depertureAirportName) {
        try {
            return routeRepository.findByDepertureAirportNameSearch(depertureAirportName);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.ROUTE_SEARCH_ERR_1, e));
        }
    }

    @Override
    public List<Route> searchWithArrivalAirportName(String arrivalAirport) {
        try {
            return routeRepository.findByArrivalAirportNameSearch(arrivalAirport);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.ROUTE_SEARCH_ERR_2, e));
        }
    }

    @Override
    public List<Route> searchWithDepertureAndArrivalAirportName(String depertureAirportName, String arrivalAirportName) {
        try {
            return routeRepository.findByDepertureAndArrivalAirportNameSearch(depertureAirportName, arrivalAirportName);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.ROUTE_SEARCH_ERR_3, e));
        }
    }


}
