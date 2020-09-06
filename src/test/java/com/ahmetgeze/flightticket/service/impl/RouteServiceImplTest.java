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
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RouteServiceImplTest {
    @Autowired
    AirportService airportService;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    RouteService routeService;

    @Autowired
    RouteRepository routeRepository;

    String airport1Name = "Atatürk Havaalanı";
    String airport2Name = "Sabiha Gökçen Havaalanı";

    /*
        Airport airport1;
        Airport airport2;
    */
    Airport airport1;
    Airport airport2;

    @BeforeEach
    void setUp() {
        Airport tmpAirport  = new Airport();
        Airport tmpAirport2  = new Airport();
        tmpAirport.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport1Name));
        tmpAirport2.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport2Name));

        airport1 = airportRepository.save(tmpAirport);
        airport2 = airportRepository.save(tmpAirport2);
    }

    @AfterEach
    void delete() {
       airportRepository.deleteAll();
    }

    @Test
    void createRouteTest() {
        UUID depertureId = airport1.getId();
        UUID arrivalId = airport2.getId();
        SaveResponse result = routeService.createRoute(depertureId, arrivalId);
        Route insertedObject = (Route) result.getInsertedObject();

        assertNotNull(result);
        assertEquals(depertureId, insertedObject.getDepartureAirport().getId());
        assertEquals(arrivalId, insertedObject.getArrivalAirport().getId());
        assertEquals(HttpStatus.OK, result.getStatus());
    }

    @Test
    void createRouteNullDepartureTest() {
        UUID arrivalId = airport2.getId();
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            routeService.createRoute(null, arrivalId);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }

    @Test
    void createRouteNullArrivalTest() {
        UUID depertureId = airport1.getId();
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            routeService.createRoute(depertureId, null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }

    @Test
    void createRouteSameDepertureAndArrivalTest() {
        UUID airportId = airport1.getId();
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            routeService.createRoute(airportId, airportId);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.EQUAL_DEPERTURE_ARRIVAL_ROUTE_ID);
    }


}
