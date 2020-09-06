package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.controller.RouteController;
import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.repository.AirportRepository;
import com.ahmetgeze.flightticket.repository.RouteRepository;
import com.ahmetgeze.flightticket.service.contract.AirportService;
import com.ahmetgeze.flightticket.service.contract.RouteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RouteServiceImplTest {
    @Autowired
    AirportService airportService;

    @Autowired
    RouteService routeService;

    @Autowired
    RouteRepository routeRepository;

    String airport1 = "Atatürk Havaalanı";
    String airport2 = "Sabiha Gökçen Havaalanı";

    RouteController routeController = new RouteController();


    @Test
    void createRouteTest() {
        UUID depertureId = ((Airport) airportService.saveAirport(airport1).getInsertedObject()).getId();
        UUID arrivalId = ((Airport) airportService.saveAirport(airport2).getInsertedObject()).getId();
        SaveResponse result = routeService.createRoute(depertureId, arrivalId);
        Route insertedObject = (Route) result.getInsertedObject();

        assertNotNull(result);
        assertEquals(depertureId, insertedObject.getDepartureAirport().getId());
        assertEquals(arrivalId, insertedObject.getArrivalAirport().getId());
        assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void createRouteNullDepartureTest() {
        UUID arrivalId = ((Airport) airportService.saveAirport(airport2).getInsertedObject()).getId();
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            routeService.createRoute(null, arrivalId);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }

    @Test
    void createRouteNullArrivalTest() {
        UUID depertureId = ((Airport) airportService.saveAirport(airport1).getInsertedObject()).getId();
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            routeService.createRoute(depertureId, null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }

    @Test
    void createRouteSameDepertureAndArrivalTest() {
        UUID airportId = ((Airport) airportService.saveAirport(airport1).getInsertedObject()).getId();
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            routeService.createRoute(airportId, airportId);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.EQUAL_DEPERTURE_ARRIVAL_ROUTE_ID);
    }





}
