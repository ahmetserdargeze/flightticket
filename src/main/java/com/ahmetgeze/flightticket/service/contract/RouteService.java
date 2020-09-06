package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;

import java.util.UUID;

public interface RouteService {
    SaveResponse createRoute(UUID depertarueAirportId, UUID arrivalAirportId);

    SearchResponse filterWithDepertureId(UUID depertarueAirportId);

    SearchResponse filterWithArrivalId(UUID arrivalAirportId);

    SearchResponse searchWithDepertureName(String depertureAirportName);

    SearchResponse searchWithArrivalName(String arrivalAirportName);

    SearchResponse searchWithDepertureNameAndArrivalName(String depertureAirportName, String arrivalAirportName);


}
