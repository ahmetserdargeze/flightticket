package com.ahmetgeze.flightticket.service.impl;

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

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
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
    String airport6Name = "Mustafa Kemal Havaalanı";
    String airport7Name = "Kemal Havaalanı";

    /*
        Airport airport1;
        Airport airport2;
    */
    Airport airport1;
    Airport airport2;
    Airport airport3;
    Airport airport4;
    Airport airport5;
    Airport airport6;
    Airport airport7;

    Route route1;
    Route route2;
    Route route3;
    Route route4;
    Route route5;
    Route route6;

    @BeforeEach
    void setUp() {
        Airport tmpAirport = new Airport();
        Airport tmpAirport2 = new Airport();
        Airport tmpAirport3 = new Airport();
        Airport tmpAirport4 = new Airport();
        Airport tmpAirport5 = new Airport();
        Airport tmpAirport6 = new Airport();
        Airport tmpAirport7 = new Airport();
        tmpAirport.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport1Name));
        tmpAirport2.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport2Name));
        tmpAirport3.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport3Name));
        tmpAirport4.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport4Name));
        tmpAirport5.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport5Name));
        tmpAirport6.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport6Name));
        tmpAirport7.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport7Name));

        airport1 = airportRepository.save(tmpAirport);
        airport2 = airportRepository.save(tmpAirport2);
        airport3 = airportRepository.save(tmpAirport3);
        airport4 = airportRepository.save(tmpAirport4);
        airport5 = airportRepository.save(tmpAirport5);
        airport6 = airportRepository.save(tmpAirport6);
        airport7 = airportRepository.save(tmpAirport7);

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

        route6 = new Route();
        route6.setArrivalAirport(airport6);
        route6.setDepartureAirport(airport7);
        route6 = routeRepository.save(route6);
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
        assertEquals(exception.getCode(), ExceptionCode.EQUAL_DEPERTURE_ARRIVAL_ROUTE_ID_ERR_1);
    }

    @Test
    void filterWithDepertureId() {
        SearchResponse result1 = routeService.filterWithDepertureId(airport3.getId());
        List<Route> resultList = (List<Route>) result1.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 1);
        assertEquals(route1, resultList.get(0));

    }

    @Test
    void filterWithDepertureIdForMultipleResult() {
        SearchResponse result = routeService.filterWithDepertureId(airport1.getId());
        List<Route> resultList = (List<Route>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 2);
        resultList.forEach(item -> {
            assertTrue(route4.equals(item) || route5.equals(item));


        });
    }

    @Test
    void filterWithDepertureIdForNullDepertureId() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SearchResponse result = routeService.filterWithDepertureId(null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);

    }


    @Test
    void filterWithArrivalId() {
        SearchResponse result1 = routeService.filterWithArrivalId(airport3.getId());
        List<Route> resultList = (List<Route>) result1.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 1);
        assertEquals(route2, resultList.get(0));

    }

    @Test
    void filterWithArrivalIdForMultipleResult() {
        SearchResponse result = routeService.filterWithArrivalId(airport4.getId());
        List<Route> resultList = (List<Route>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 2);
        resultList.forEach(item -> {
            assertTrue(route4.equals(item) || route3.equals(item));
        });
    }

    @Test
    void filterWithArrivalIdForNullDepertureId() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SearchResponse result = routeService.filterWithArrivalId(null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);

    }

    @Test
    void searchWithDepertureName() {
        SearchResponse result = routeService.searchWithDepertureName(airport5.getName());
        List<Route> resultList = (List<Route>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 1);
        assertEquals(route3, resultList.get(0));

    }

    @Test
    void searchWithDepertureNameForMultipleResult() {
        SearchResponse result = routeService.searchWithDepertureName(airport1.getName());
        List<Route> resultList = (List<Route>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 2);
        assertThat(resultList, containsInAnyOrder(route4, route5));
    }

    @Test
    void searchWithDepertureNameForNullDepertureName() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SearchResponse result = routeService.searchWithDepertureName(null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }


    @Test
    void searchWithArrivalName() {
        SearchResponse result = routeService.searchWithArrivalName(airport3.getName());
        List<Route> resultList = (List<Route>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 1);
        assertEquals(route2, resultList.get(0));

    }

    @Test
    void searchWithArrivalNameForMultipleResult() {
        SearchResponse result = routeService.searchWithArrivalName(airport2.getName());
        List<Route> resultList = (List<Route>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 2);
        assertThat(resultList, containsInAnyOrder(route1, route5));
    }

    @Test
    void searchWithArrivalNameForNullArrivalName() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SearchResponse result = routeService.searchWithArrivalName(null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }



    @Test
    void searchWithDepertureNameAndArrivalName() {
        SearchResponse result = routeService.searchWithDepertureNameAndArrivalName(airport4.getName(),airport3.getName());
        List<Route> resultList = (List<Route>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 1);
        assertEquals(route2, resultList.get(0));

    }

    @Test
    void searchWithDepertureNameAndArrivalName2() {
        SearchResponse result = routeService.searchWithDepertureNameAndArrivalName("Kemal","Kemal");
        List<Route> resultList = (List<Route>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 1);
        assertEquals(route6, resultList.get(0));

    }


    @Test
    void searchWithDepertureNameAndArrivalNameForNullDepertureName() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SearchResponse result = routeService.searchWithDepertureNameAndArrivalName(null,airport1.getName());
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }

    @Test
    void searchWithDepertureNameAndArrivalNameForNullArrivalName() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SearchResponse result = routeService.searchWithDepertureNameAndArrivalName(airport1.getName(),null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }
}
