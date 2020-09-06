package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.controller.RouteController;
import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.repository.AirportRepository;
import com.ahmetgeze.flightticket.repository.RouteRepository;
import com.ahmetgeze.flightticket.service.contract.AirportService;
import com.ahmetgeze.flightticket.service.contract.RouteService;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.MatcherAssert.assertThat;

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
    String airport3Name = "Ercan Havaalanı";
    String airport4Name = "Esenboğa  Havaalanı";
    String airport5Name = "Dalaman Havaalanı";

    /*
        Airport airport1;
        Airport airport2;
    */
    Airport airport1;
    Airport airport2;
    Airport airport3;
    Airport airport4;
    Airport airport5;

    Route route1;
    Route route2;
    Route route3;
    Route route4;
    Route route5;

    @BeforeEach
    void setUp() {
        Airport tmpAirport = new Airport();
        Airport tmpAirport2 = new Airport();
        Airport tmpAirport3 = new Airport();
        Airport tmpAirport4 = new Airport();
        Airport tmpAirport5 = new Airport();
        tmpAirport.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport1Name));
        tmpAirport2.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport2Name));
        tmpAirport3.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport3Name));
        tmpAirport4.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport4Name));
        tmpAirport5.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport5Name));

        airport1 = airportRepository.save(tmpAirport);
        airport2 = airportRepository.save(tmpAirport2);
        airport3 = airportRepository.save(tmpAirport3);
        airport4 = airportRepository.save(tmpAirport4);
        airport5 = airportRepository.save(tmpAirport5);

        route1 = new Route();
        route1.setArrivalAirport(airport2);
        route1.setDepartureAirport(airport3);
        route1 = routeRepository.save(route1);

        route2 = new Route();
        route2.setArrivalAirport(airport3);
        route2.setDepartureAirport(airport4);
        route2 = routeRepository.save(route2);

        route3 = new Route();
        route3.setArrivalAirport(airport4);
        route3.setDepartureAirport(airport5);
        route3 = routeRepository.save(route3);

        route4 = new Route();
        route4.setArrivalAirport(airport4);
        route4.setDepartureAirport(airport1);
        route4 = routeRepository.save(route4);


        route5 = new Route();
        route5.setArrivalAirport(airport2);
        route5.setDepartureAirport(airport1);
        route5 = routeRepository.save(route5);
    }

    @AfterEach
    void delete() {
        airportRepository.deleteAll();
        routeRepository.deleteAll();
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

    @Test
    void filterWithDepertureId() {
        SearchResponse result1= routeService.filterWithDepertureId(airport3.getId());
        List<Route> resultList= (List<Route>) result1.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 1);
        assertEquals(route1,resultList.get(0));

    }

    @Test
    void filterWithDepertureIdForMultipleResult() {
        SearchResponse result= routeService.filterWithDepertureId(airport1.getId());
        List<Route> resultList= (List<Route>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 2);
        resultList.forEach(item->{
            assertTrue(route4.equals(item) || route5.equals(item));


        });
    }

    @Test
    void filterWithDepertureIdForNullDepertureId() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SearchResponse result= routeService.filterWithDepertureId(null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);

    }


    @Test
    void filterWithArrivalId() {
        SearchResponse result1= routeService.filterWithArrivalId(airport3.getId());
        List<Route> resultList= (List<Route>) result1.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 1);
        assertEquals(route2,resultList.get(0));

    }

    @Test
    void filterWithArrivalIdForMultipleResult() {
        SearchResponse result= routeService.filterWithArrivalId(airport4.getId());
        List<Route> resultList= (List<Route>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 2);
        resultList.forEach(item->{
            assertTrue(route4.equals(item) || route3.equals(item));
        });
    }

    @Test
    void filterWithArrivalIdForNullDepertureId() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SearchResponse result= routeService.filterWithArrivalId(null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);

    }

}
