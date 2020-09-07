package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.repository.AirportRepository;
import com.ahmetgeze.flightticket.service.contract.AirportService;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AirportServiceImplTest {
    @Autowired
    AirportService airportService;

    @Autowired
    AirportRepository airportRepository;

    @Before
    void setUp() {
        assertNotNull(airportService);
    }

    @BeforeEach
    void deletAllRecord() {
        airportRepository.deleteAll();
    }

    @AfterEach
    void delete() {
        airportRepository.deleteAll();
    }
    String airportName1 = "ATATURK HAVALİMANI";
    String airportName2 = "SABİHA GÖKÇEN HAVALİMANI";


    @Test
    void saveAirportTest() {
        airportService.createAirport(airportName1);
        Airport airport = airportRepository.findByName(airportName1).get();
        assertNotNull(airport);
        assertEquals(airport.getName(), airportName1);

    }

    @Test
    void duplicateNameErrorTest() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            airportService.createAirport(airportName1);
            airportService.createAirport(airportName1);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.DB_EXEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.AIRPORT_SAVE_ERR_1);
    }

    @Test
    void createAirportNullNameTest() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            airportService.createAirport(null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }

    @Test
    void searchAirportWithNameSingleResultTest() {
        airportService.createAirport(airportName1);
        airportService.createAirport(airportName2);

        List<Airport> result = (List<Airport>) airportService.searchWithAirportName("Ata").getSearchResult();
        assertNotNull(result);
        assertTrue(result.size() == 1);
        assertEquals(result.get(0).getName(), airportName1);

        result = (List<Airport>) airportService.searchWithAirportName("Havalimanı").getSearchResult();

    }

    @Test
    void searchAirportWithNameMultipleResultTest() {
        airportService.createAirport(airportName1);
        airportService.createAirport(airportName2);
        List<Airport> result = (List<Airport>) airportService.searchWithAirportName("Havalimanı").getSearchResult();
        assertNotNull(result);
        assertTrue(result.size() == 2);
    }

    @Test
    void searchWithAirportNameTestForNullAirportNameTest() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
           airportService.searchWithAirportName(null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }

}
