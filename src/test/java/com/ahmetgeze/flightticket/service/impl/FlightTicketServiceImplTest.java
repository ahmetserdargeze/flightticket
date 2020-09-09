package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.entity.*;
import com.ahmetgeze.flightticket.model.request.CreditCardDetails;
import com.ahmetgeze.flightticket.model.request.SellTicketInputs;
import com.ahmetgeze.flightticket.repository.*;
import com.ahmetgeze.flightticket.service.contract.FlightTicketService;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FlightTicketServiceImplTest {
    @Autowired
    FlightTicketService flightTicketService;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    AirlinesCompanyRepository airlinesCompanyRepository;

    @Autowired
    FlightRecordRepository flightRecordRepository;

    @Autowired
    FlightTicketRepository flightTicketRepository;
    @Autowired
    SellRecordRepository sellRecordRepository;

    String airport1Name = "Atatürk Havaalanı";
    String airport2Name = "Sabiha Gökçen Havaalanı";


    /*
        Airport airport1;
        Airport airport2;
    */
    Airport airport1;
    Airport airport2;
    AirlinesCompany airlinesCompany;


    Route route1 = new Route();
    FlightRecord flightRecord1 = new FlightRecord();


    String airlinesCompanyName = "TÜRK hava yolları";
    String flighRecordName2 = "TK1234";
    int flightRecordSeatCount = 100;


    @BeforeEach
    void setUp() throws ParseException {
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("Turkey"));
        String departureDateString = "22-01-2015 23:59:59";
        String arrivalDateString = "22-01-2015 00:01:01";
        Date departureDate = formatter.parse(departureDateString);
        Date arrivalDate = formatter.parse(arrivalDateString);

        flightRecord1 = new FlightRecord();
        flightRecord1.setName(flighRecordName2);
        flightRecord1.setAirlinesCompany(airlinesCompany);
        flightRecord1.setRoute(route1);
        flightRecord1.setArrivalDate(arrivalDate);
        flightRecord1.setDepartureDate(departureDate);
        flightRecord1.setFligtSeatCount(flightRecordSeatCount);
        flightRecord1.setUnitPrice(100d);
        flightRecord1 = flightRecordRepository.save(flightRecord1);
    }

    @AfterEach
    void delete() {
        airportRepository.deleteAll();
        routeRepository.deleteAll();
        airlinesCompanyRepository.deleteAll();
    }

    @Test
    void sellTesting1() {
        SellTicketInputs sellTicketInputs = new SellTicketInputs();
        sellTicketInputs.setFlightRecordId(flightRecord1.getId());
        sellTicketInputs.setSeatCount(10);
        CreditCardDetails creditCardDetails = new CreditCardDetails();
        creditCardDetails.setCardDate("12/24");
        creditCardDetails.setCardNumber("4221-1611-2233-0005");
        creditCardDetails.setCvvCode("315");
        creditCardDetails.setCardPleaceholderName("ahmet serdar geze");
        sellTicketInputs.setPaymentDetails(creditCardDetails);
        List<FlightTicket> flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();

        flightTickets.forEach(item -> {
            assertEquals("422116******0005", item.getSellRecord().getCardNumber());
            if (item.getPrice() != 100 || item.getSellRecord().getTotalPrice() != 1000) {
                fail();
            }
        });
    }

    @Test
    void sellTesting2() {
        SellTicketInputs sellTicketInputs = new SellTicketInputs();
        sellTicketInputs.setFlightRecordId(flightRecord1.getId());
        sellTicketInputs.setSeatCount(10);
        CreditCardDetails creditCardDetails = new CreditCardDetails();
        creditCardDetails.setCardDate("12/24");
        creditCardDetails.setCardNumber("4221,1611,2233,0005");
        creditCardDetails.setCvvCode("315");
        creditCardDetails.setCardPleaceholderName("ahmet serdar geze");
        sellTicketInputs.setPaymentDetails(creditCardDetails);
        List<FlightTicket> flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();

        flightTickets.forEach(item -> {
            assertEquals("422116******0005", item.getSellRecord().getCardNumber());
            if (item.getPrice() != 100 || item.getSellRecord().getTotalPrice() != 1000) {
                fail();
            }
        });
    }

    @Test
    void sellTesting3() {
        SellTicketInputs sellTicketInputs = new SellTicketInputs();
        sellTicketInputs.setFlightRecordId(flightRecord1.getId());
        sellTicketInputs.setSeatCount(10);
        CreditCardDetails creditCardDetails = new CreditCardDetails();
        creditCardDetails.setCardDate("12/24");
        creditCardDetails.setCardNumber("4221,1611,2233,0005");
        creditCardDetails.setCvvCode("315");
        creditCardDetails.setCardPleaceholderName("ahmet serdar geze");
        sellTicketInputs.setPaymentDetails(creditCardDetails);
        List<FlightTicket> flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();

        flightTickets.forEach(item -> {
            assertEquals("422116******0005", item.getSellRecord().getCardNumber());
            if (item.getPrice() != 100 || item.getSellRecord().getTotalPrice() != 1000) {
                fail();
            }
        });

        sellTicketInputs.setSeatCount(3);
        flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();
        flightTickets.forEach(item -> {
            assertEquals("422116******0005", item.getSellRecord().getCardNumber());
            if (item.getPrice() != 110 || item.getSellRecord().getTotalPrice() != 330) {
                fail();
            }
        });

        sellTicketInputs.setSeatCount(7);
        flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();
        flightTickets.forEach(item -> {
            assertEquals("422116******0005", item.getSellRecord().getCardNumber());
            if (item.getPrice() != 110 || item.getSellRecord().getTotalPrice() != 770) {
                fail();
            }
        });

        sellTicketInputs.setSeatCount(7);
        flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();
        flightTickets.forEach(item -> {
            assertEquals("422116******0005", item.getSellRecord().getCardNumber());
            if (item.getPrice() != 121 || item.getSellRecord().getTotalPrice() != 847) {
                fail();
            }
        });

        sellTicketInputs.setSeatCount(5);
        flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();
        flightTickets.forEach(item -> {
            assertEquals("422116******0005", item.getSellRecord().getCardNumber());
            if (item.getSellRecord().getTotalPrice() != 629.2) {
                fail();
            }
        });
    }


    @Test
    void sellAndReturnTesting4() {
        SellTicketInputs sellTicketInputs = new SellTicketInputs();
        sellTicketInputs.setFlightRecordId(flightRecord1.getId());
        sellTicketInputs.setSeatCount(10);
        CreditCardDetails creditCardDetails = new CreditCardDetails();
        creditCardDetails.setCardDate("12/24");
        creditCardDetails.setCardNumber("4221,1611,2233,0005");
        creditCardDetails.setCvvCode("315");
        creditCardDetails.setCardPleaceholderName("ahmet serdar geze");
        sellTicketInputs.setPaymentDetails(creditCardDetails);
        List<FlightTicket> flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();

        flightTickets.forEach(item -> {
            assertEquals("422116******0005", item.getSellRecord().getCardNumber());
            if (item.getPrice() != 100 || item.getSellRecord().getTotalPrice() != 1000) {
                fail();
            }
        });

        flightTicketService.returnFlightTicket(flightTickets.get(0).getId());

        sellTicketInputs.setSeatCount(1);
        flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();
        flightTickets.forEach(item -> {
            assertEquals("422116******0005", item.getSellRecord().getCardNumber());
            if (item.getPrice() != 100 || item.getSellRecord().getTotalPrice() != 100) {
                fail();
            }
        });

        sellTicketInputs.setSeatCount(1);
        flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();
        flightTickets.forEach(item -> {
            assertEquals("422116******0005", item.getSellRecord().getCardNumber());
            if (item.getPrice() != 110 || item.getSellRecord().getTotalPrice() != 110) {
                fail();
            }
        });
    }


    @Test
    void researchTesting4() {
        SellTicketInputs sellTicketInputs = new SellTicketInputs();
        sellTicketInputs.setFlightRecordId(flightRecord1.getId());
        sellTicketInputs.setSeatCount(10);
        CreditCardDetails creditCardDetails = new CreditCardDetails();
        creditCardDetails.setCardDate("12/24");
        creditCardDetails.setCardNumber("4221,1611,2233,0005");
        creditCardDetails.setCvvCode("315");
        creditCardDetails.setCardPleaceholderName("ahmet serdar geze");
        sellTicketInputs.setPaymentDetails(creditCardDetails);
        List<FlightTicket> flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();


        sellTicketInputs.setSeatCount(1);
        flightTickets = (List<FlightTicket>) flightTicketService.sellTicket(sellTicketInputs).getInsertLists();
        List<FlightTicket> flightTicketsActual = (List<FlightTicket>) flightTicketService.searchFlightTicket(flightTickets.get(0).getId()).getSearchResult();

        assertNotNull(flightTickets);
        assertTrue(flightTickets.size() == 1);
        assertTrue(flightTicketsActual.size() == 1);
        assertEquals(flightTickets.get(0),flightTicketsActual.get(0));

    }
}
