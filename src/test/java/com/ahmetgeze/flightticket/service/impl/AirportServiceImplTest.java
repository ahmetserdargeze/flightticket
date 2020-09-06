package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.AirportRepository;
import com.ahmetgeze.flightticket.service.contract.AirportService;
import org.junit.Before;
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
    String airportName1 = "ATATURK HAVALİMANI";
    String airportName2 = "SABİHA GÖKÇEN HAVALİMANI";


    @Test
    void testSaveAirport() {
        airportService.saveAirport(airportName1);
        Airport airport = airportRepository.findByName(airportName1).get();
        assertNotNull(airport);
        assertEquals(airport.getName(), airportName1);

    }

    @Test
    void testDuplicateNameError() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            airportService.saveAirport(airportName1);
            airportService.saveAirport(airportName1);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.DB_EXEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.AIRPORT_SAVE_ERR_1);
    }

    @Test
    void searchAirportWithNameSingleResult() {
        airportService.saveAirport(airportName1);
        airportService.saveAirport(airportName2);

        List<Airport> result = (List<Airport>) airportService.searchAirport("Ata").getSearchResult();
        assertNotNull(result);
        assertTrue(result.size() == 1);
        assertEquals(result.get(0).getName(), airportName1);

        result = (List<Airport>) airportService.searchAirport("Havalimanı").getSearchResult();

    }

    @Test
    void searchAirportWithNameMultipleResult() {
        airportService.saveAirport(airportName1);
        airportService.saveAirport(airportName2);
        List<Airport> result = (List<Airport>) airportService.searchAirport("Havalimanı").getSearchResult();
        assertNotNull(result);
        assertTrue(result.size() == 2);
    }
}
