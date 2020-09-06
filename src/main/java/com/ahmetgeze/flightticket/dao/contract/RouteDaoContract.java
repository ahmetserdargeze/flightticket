package com.ahmetgeze.flightticket.dao.contract;

import com.ahmetgeze.flightticket.entity.Route;

import java.util.List;
import java.util.UUID;

public interface RouteDaoContract {
     Boolean saveRoute(Route route) ;

     List<Route> filterRouteWithArrivalId(UUID arrivalId);
     List<Route> filterRouteWithDepertureId(UUID depertureId);

     List<Route> searchWithDepartureAirportName(String depertureAirportName);
     List<Route> searchWithArrivalAirportName(String arrivalAirport);
     List<Route> searchWithDepertureAndArrivalAirportName(String depertureAirportName,String arrivalAirportName);

    }
