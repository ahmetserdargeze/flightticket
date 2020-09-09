package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.entity.FlightRecord;
import com.ahmetgeze.flightticket.service.contract.PriceCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PriceCalculationServiceImplTest {
    @Autowired
    PriceCalculatorService priceCalculatorService;

    FlightRecord flightRecord;

    @BeforeEach
    void setUp() {
        flightRecord = new FlightRecord();
        flightRecord.setUnitPrice(100);
        flightRecord.setFligtSeatCount(100);

    }

    @Test
    void isHasAvailableTicket() {
        assertEquals(true, priceCalculatorService.isHasAvailableTicket(flightRecord, 10, 10));
        assertEquals(true, priceCalculatorService.isHasAvailableTicket(flightRecord, 50, 50));
        assertEquals(false, priceCalculatorService.isHasAvailableTicket(flightRecord, 60, 50));
    }

    @Test
    void calculateTicketPriceTest() {
        assertEquals(100, priceCalculatorService.calculateTicketPrice(flightRecord, 9));
        assertEquals(110, priceCalculatorService.calculateTicketPrice(flightRecord, 10));
        assertEquals(110, priceCalculatorService.calculateTicketPrice(flightRecord, 19));
        assertEquals(121, priceCalculatorService.calculateTicketPrice(flightRecord, 20));
        assertEquals(133.1, priceCalculatorService.calculateTicketPrice(flightRecord, 30));
        assertEquals(133.1, priceCalculatorService.calculateTicketPrice(flightRecord, 30));
        assertEquals(146.41, priceCalculatorService.calculateTicketPrice(flightRecord, 40));
        assertEquals(161.051, priceCalculatorService.calculateTicketPrice(flightRecord, 50));
        assertEquals(177.15609999999998, priceCalculatorService.calculateTicketPrice(flightRecord, 60));
        assertEquals(194.87170999999998, priceCalculatorService.calculateTicketPrice(flightRecord, 70));
        assertEquals(214.35888099999997, priceCalculatorService.calculateTicketPrice(flightRecord, 80));
        assertEquals(235.79476909999997, priceCalculatorService.calculateTicketPrice(flightRecord, 90));
    }

}
