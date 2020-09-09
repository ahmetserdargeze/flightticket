package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.entity.FlightRecord;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.repository.AirlinesCompanyRepository;
import com.ahmetgeze.flightticket.repository.AirportRepository;
import com.ahmetgeze.flightticket.repository.FlightRecordRepository;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightRecordControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    AirportRepository airportRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    AirlinesCompanyRepository airlinesCompanyRepository;

    @Autowired
    FlightRecordRepository flightRecordRepository;

    Gson gson = new Gson();


    String airport1Name = "Atatürk Havaalanı";
    String airport2Name = "Sabiha Gökçen Havaalanı";

    Airport airport1;
    Airport airport2;
    AirlinesCompany airlinesCompany;


    Route route1 = new Route();
    FlightRecord flightRecord1 = new FlightRecord();
    FlightRecord flightRecord2 = new FlightRecord();


    String airlinesCompanyName = "TÜRK hava yolları";

    String flighRecordName = "TK123";
    String flighRecordName2 = "TK1234";
    String flighRecordName3 = "TK1235";
    String flighRecordName4 = "TK1236";
    int flightRecordSeatCount = 100;
    String departureDateString = "22-01-2015 10:15:55";
    String arrivalDateString = "22-01-2015 12:15:55";


    @BeforeEach
    void setUp() {
        airport1 = new Airport();
        airport2 = new Airport();

        airport1.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport1Name));
        airport2.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport2Name));

        airport1 = airportRepository.save(airport1);
        airport2 = airportRepository.save(airport2);


        route1 = new Route();
        route1.setArrivalAirport(airport1);
        route1.setDepartureAirport(airport2);
        route1 = routeRepository.save(route1);

        airlinesCompany = new AirlinesCompany();
        airlinesCompany.setName(airlinesCompanyName);
        airlinesCompany = airlinesCompanyRepository.save(airlinesCompany);

        flightRecord1 = new FlightRecord();

        flightRecord1.setAirlinesCompany(airlinesCompany);
        flightRecord1.setRoute(route1);
        flightRecord1.setDepartureDate(new Date());
        flightRecord1.setFligtSeatCount(flightRecordSeatCount);
        flightRecord1.setArrivalDate(new Date());
        flightRecord1.setName(flighRecordName2);
        flightRecordRepository.save(flightRecord1);

        flightRecord2 = new FlightRecord();
        flightRecord2.setAirlinesCompany(airlinesCompany);
        flightRecord2.setRoute(route1);
        flightRecord2.setFligtSeatCount(flightRecordSeatCount);
        flightRecord2.setDepartureDate(new Date());
        flightRecord2.setArrivalDate(new Date());
        flightRecord2.setName(flighRecordName3);
        flightRecordRepository.save(flightRecord2);

    }

    @AfterEach
    void delete() {
        airportRepository.deleteAll();
        routeRepository.deleteAll();
        airlinesCompanyRepository.deleteAll();
        flightRecordRepository.deleteAll();
    }

    @Test
    void createFlightRecordTest() throws ParseException {
        StringBuilder testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/flightRecord/");
        testUrl.append("/create");
        testUrl.append("?flightRecordName={flightRecordName}&");
        testUrl.append("airlinesCompanyId={airlinesCompanyId}&");
        testUrl.append("routeId={routeId}&");
        testUrl.append("flightSeatCout={flightSeatCout}&");
        testUrl.append("departureDate={departureDate}&");
        testUrl.append("arrivalDate={arrivalDate}&");
        ResponseEntity<SaveResponse> responseEntity = restTemplate.postForEntity(testUrl.toString(),
                null,
                SaveResponse.class,
                flighRecordName,
                airlinesCompany.getId(),
                route1.getId(),
                100,
                departureDateString,
                arrivalDateString);
        FlightRecord createdFlightRecord = gson.fromJson(gson.toJson(responseEntity.getBody().getInsertedObject()), FlightRecord.class);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("Turkey"));
        Date departureDate = formatter.parse(departureDateString);
        Date arrivalDate = formatter.parse(arrivalDateString);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(createdFlightRecord);
        assertEquals(airport2, createdFlightRecord.getRoute().getDepartureAirport());
        assertEquals(airport1, createdFlightRecord.getRoute().getArrivalAirport());
        assertEquals(route1, createdFlightRecord.getRoute());
        assertEquals(departureDate.getTime(), createdFlightRecord.getDepartureDate().getTime());
        assertEquals(arrivalDate.getTime(), createdFlightRecord.getArrivalDate().getTime());
    }

    @Test
    void searchFlightRecordList() {
        StringBuilder testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/flightRecord/");
        testUrl.append("/search");
        testUrl.append("?airlinesCompanyId={airlinesCompanyId}&");
        testUrl.append("routeId={routeId}&");
        testUrl.append("departureDate={departureDate}");

        ResponseEntity<SearchResponse> responseEntity = restTemplate.getForEntity(testUrl.toString(),
                SearchResponse.class,
                airlinesCompany.getId(),
                route1.getId(),
                "08-09-2020"
        );
        List<FlightRecord> resultList = gson.fromJson(gson.toJson(responseEntity.getBody().getSearchResult()),
                new TypeToken<List<FlightRecord>>() {}.getType());


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(resultList.size() == 2);
        assertThat(resultList, containsInAnyOrder(flightRecord1,flightRecord2));
    }


    @Test
    void listFlightRecordList() {
        StringBuilder testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/flightRecord/");
        testUrl.append("/list");


        ResponseEntity<SearchResponse> responseEntity = restTemplate.getForEntity(testUrl.toString(),
                SearchResponse.class);
        List<FlightRecord> resultList = gson.fromJson(gson.toJson(responseEntity.getBody().getSearchResult()),
                new TypeToken<List<FlightRecord>>() {}.getType());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(resultList.size() == 2);
        assertThat(resultList, containsInAnyOrder(flightRecord1,flightRecord2));
    }

}
