package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.model.response.SaveResponse;

import java.util.UUID;

public interface RouteService {
     SaveResponse createRoute(UUID depertarueAirportId,UUID arrivalAirportId) ;



    }
