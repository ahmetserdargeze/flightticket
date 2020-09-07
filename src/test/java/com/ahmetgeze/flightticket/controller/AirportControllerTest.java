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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AirportControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    AirportRepository airportRepository;

    Gson gson = new Gson();

    String airport1Name = "Atatürk Havaalanı";
    String airport2Name = "Sabiha Gökçen Havaalanı";
    String airport3Name = "Mustafa Kemal Atatürk Havaalanı";

    Airport airport1;
    Airport airport2;
    Airport airport3;

    @BeforeEach
    void setUp() {
        airport1 = new Airport();
        airport2 = new Airport();
        airport3 = new Airport();

        airport1.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport1Name));
        airport2.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport2Name));
        airport3.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airport3Name));

        airport1 = airportRepository.save(airport1);
        airport2 = airportRepository.save(airport2);
        airport3 = airportRepository.save(airport3);


    }


    @AfterEach
    void delete() {
        airportRepository.deleteAll();
    }

    @Test
    void createAirportTest() throws ParseException {
        StringBuilder testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/airports/");
        testUrl.append("/create");
        testUrl.append("?name={name}");
        ResponseEntity<SaveResponse> responseEntity = restTemplate.postForEntity(testUrl.toString(),
                null,
                SaveResponse.class,
                "TEST 12345"
        );
        Airport result = gson.fromJson(gson.toJson(responseEntity.getBody().getInsertedObject()), Airport.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(result);
        assertEquals("TEST 12345", result.getName());
    }


    @Test
    void searchAirportWithNameTest() throws ParseException {
        StringBuilder testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/airports/");
        testUrl.append("/search");
        testUrl.append("?name={name}");
        ResponseEntity<SearchResponse> responseEntity = restTemplate.getForEntity(testUrl.toString(),
                SearchResponse.class,
                "ata"
        );
        List<Airport> resultList = gson.fromJson(gson.toJson(responseEntity.getBody().getSearchResult()),
                new TypeToken<List<Airport>>() {}.getType());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(resultList);
        assertNotNull(resultList.size() == 2);
        assertThat(resultList, containsInAnyOrder(airport1,airport3));
    }

    @Test
    void listAirportTest() {
        StringBuilder testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/airports/");
        testUrl.append("/list");


        ResponseEntity<SearchResponse> responseEntity = restTemplate.getForEntity(testUrl.toString(),
                SearchResponse.class);
        List<Airport> resultList = gson.fromJson(gson.toJson(responseEntity.getBody().getSearchResult()),
                new TypeToken<List<Airport>>() {}.getType());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(resultList.size() == 3);
        assertThat(resultList, containsInAnyOrder(airport1,airport2,airport3));
    }


}
