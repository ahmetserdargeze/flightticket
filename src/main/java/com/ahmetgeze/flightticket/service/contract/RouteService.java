package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;

import java.util.UUID;

public interface RouteService {
     SaveResponse createRoute(UUID depertarueAirportId,UUID arrivalAirportId) ;
     SearchResponse filterWithDepertureId(UUID depertarueAirportId);
     SearchResponse filterWithArrivalId(UUID arrivalAirportId);



    }
