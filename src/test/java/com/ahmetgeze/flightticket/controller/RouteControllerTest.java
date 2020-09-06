package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.repository.AirportRepository;
import com.ahmetgeze.flightticket.repository.RouteRepository;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;



    @Autowired
    AirportRepository airportRepository;

    @Autowired
    RouteRepository routeRepository;

    Gson gson = new Gson();

    String airport1Name = "Atatürk Havaalanı";
    String airport2Name = "Sabiha Gökçen Havaalanı";
    String airport3Name = "Ercan Havaalanı";
    String airport4Name = "Esenboğa  Havaalanı";
    String airport5Name = "Dalaman Havaalanı";
    String airport6Name = "Mustafa Kemal Havaalanı";
    String airport7Name = "Kemal Havaalanı";


    Airport airport1;
    Airport airport2;
    Airport airport3;
    Airport airport4;


    Route route1;


    @BeforeEach
    void setUp() {
        Airport tmpAirport = new Airport();
        Airport tmpAirport2 = new Airport();
        Airport tmpAirport3 = new Airport();
        Airport tmpAirport4 = new Airport();

        tmpAirport.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport1Name));
        tmpAirport2.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport2Name));
        tmpAirport3.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport3Name));
        tmpAirport4.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport4Name));

        airport1 = airportRepository.save(tmpAirport);
        airport2 = airportRepository.save(tmpAirport2);
        airport3 = airportRepository.save(tmpAirport3);
        airport4 = airportRepository.save(tmpAirport4);

        route1 = new Route();
        route1.setArrivalAirport(airport3);
        route1.setDepartureAirport(airport4);
        route1 = routeRepository.save(route1);

    }

    @AfterEach
    void delete() {
        airportRepository.deleteAll();
        routeRepository.deleteAll();
    }


    @Test
    public void testCreateEndPoint()  {
        StringBuilder  testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/route/");
        testUrl.append("/create");
        testUrl.append("?departureId={departureId}&arrivalId={arrivalId}&");
        ResponseEntity<SaveResponse> responseEntity = restTemplate.postForEntity(testUrl.toString(),
                null,
                SaveResponse.class,
                airport1.getId(),
                airport2.getId());
        Route createdRoute = gson.fromJson(gson.toJson(responseEntity.getBody().getInsertedObject()),Route.class);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(airport1,createdRoute.getDepartureAirport());
        assertEquals(airport2,createdRoute.getArrivalAirport());
    }

    @Test
    public void testSearchEndPoint()  {
        StringBuilder  testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/route/");
        testUrl.append("/search");
        testUrl.append("?departureName={departureName}&arrivalName={arrivalName}&");
        ResponseEntity<SearchResponse> responseEntity = restTemplate.getForEntity(testUrl.toString(),
                SearchResponse.class,
                airport4.getName(),
                airport3.getName());
        List<Route> searchList = gson.fromJson(gson.toJson(responseEntity.getBody().getSearchResult()), new TypeToken<List<Route>>(){}.getType());

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertNotNull(searchList);
        assertTrue(!searchList.isEmpty());
        assertTrue(searchList.size() == 1);
        assertEquals(route1,searchList.get(0));
    }

    @Test
    public void testFilterPoint()  {
        StringBuilder  testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/route/");
        testUrl.append("/filter");
        testUrl.append("?departureId={departureId}&arrivalId={arrivalId}&");
        ResponseEntity<SearchResponse> responseEntity = restTemplate.getForEntity(testUrl.toString(),
                SearchResponse.class,
                null,
                airport3.getId());
        List<Route> searchList = gson.fromJson(gson.toJson(responseEntity.getBody().getSearchResult()), new TypeToken<List<Route>>(){}.getType());

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertNotNull(searchList);
        assertTrue(!searchList.isEmpty());
        assertTrue(searchList.size() == 1);
        assertEquals(route1,searchList.get(0));
    }

}
