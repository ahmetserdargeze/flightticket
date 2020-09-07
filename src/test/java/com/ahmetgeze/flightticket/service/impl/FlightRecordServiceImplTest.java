package com.ahmetgeze.flightticket.service.impl;

        import com.ahmetgeze.flightticket.entity.AirlinesCompany;
        import com.ahmetgeze.flightticket.entity.Airport;
        import com.ahmetgeze.flightticket.entity.FlightRecord;
        import com.ahmetgeze.flightticket.entity.Route;
        import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
        import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
        import com.ahmetgeze.flightticket.model.exception.GeneralException;
        import com.ahmetgeze.flightticket.model.response.SaveResponse;
        import com.ahmetgeze.flightticket.repository.AirlinesCompanyRepository;
        import com.ahmetgeze.flightticket.repository.AirportRepository;
        import com.ahmetgeze.flightticket.repository.FlightRecordRepository;
        import com.ahmetgeze.flightticket.repository.RouteRepository;
        import com.ahmetgeze.flightticket.service.contract.FlightRecordService;
        import com.ahmetgeze.flightticket.utils.UtilsFunc;
        import org.junit.jupiter.api.AfterEach;
        import org.junit.jupiter.api.Assertions;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.context.SpringBootTest;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.Locale;
        import java.util.TimeZone;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class FlightRecordServiceImplTest {
    @Autowired
    FlightRecordService flightRecordService;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    AirlinesCompanyRepository airlinesCompanyRepository;

    @Autowired
    FlightRecordRepository flightRecordRepository;

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

    String airlinesCompanyName = "TÜRK hava yolları";

    String flighRecordName = "TK123";
    int flightRecordSeatCount = 100;


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
    }

    @AfterEach
    void delete() {
        airportRepository.deleteAll();
        routeRepository.deleteAll();
        airlinesCompanyRepository.deleteAll();
    }

    @Test
    void createFlightRecordTest() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("Turkey"));

        String departureDateString = "22-01-2015 10:15:55";
        String arrivalDateString = "22-01-2015 12:15:55";
        Date departureDate = formatter.parse(departureDateString);
        Date arrivalDate = formatter.parse(arrivalDateString);
        SaveResponse result = flightRecordService.createFlightRecord(flighRecordName, airlinesCompany.getId(), route1.getId(), flightRecordSeatCount, departureDate, arrivalDate);
        FlightRecord insertedObject = (FlightRecord) result.getInsertedObject();
        FlightRecord insertedObjectFromRepo = flightRecordRepository.findById(insertedObject.getId()).get();

        assertNotNull(result);
        assertNotNull(insertedObjectFromRepo);
        assertEquals(insertedObject, insertedObjectFromRepo);
        assertEquals(insertedObject.getAirlinesCompany(), airlinesCompany);
    }

    @Test
    void createFlightRecordForDepertureDateGreatThanArrivalDateTest() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("Turkey"));

            String arrivalDateString = "22-01-2015 10:15:55";
            String departureDateString = "22-01-2015 12:15:55";
            Date departureDate = formatter.parse(departureDateString);
            Date arrivalDate = formatter.parse(arrivalDateString);
            SaveResponse result = flightRecordService.createFlightRecord(flighRecordName, airlinesCompany.getId(), route1.getId(), flightRecordSeatCount, departureDate, arrivalDate);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.FLIGHT_RECORD_DATE_ERR_1);

    }

    @Test
    void createFlightRecordForFlightSeatCountLowerThanZeroTest() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("Turkey"));

            String departureDateString = "22-01-2015 10:15:55";
            String arrivalDateString = "22-01-2015 12:15:55";
            Date departureDate = formatter.parse(departureDateString);
            Date arrivalDate = formatter.parse(arrivalDateString);
            SaveResponse result = flightRecordService.createFlightRecord(flighRecordName, airlinesCompany.getId(), route1.getId(), 0, departureDate, arrivalDate);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(ExceptionCode.FLIGHT_RECORD_SEAT_COUNT_ERR_1, exception.getCode());
    }

    @Test
    void createFlightRecordForNullInputTest() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("Turkey"));

            String departureDateString = "22-01-2015 10:15:55";
            Date departureDate = formatter.parse(departureDateString);
            Date arrivalDate = null;
            SaveResponse result = flightRecordService.createFlightRecord(flighRecordName, airlinesCompany.getId(), route1.getId(), 0, departureDate, arrivalDate);
        });
        assertNotNull(exception);
        assertEquals(ExceptionCategory.SERVİCE_EXCEPTİON, exception.getCategory());
        assertEquals(ExceptionCode.NULL_INPUT_ERR, exception.getCode());
    }




}
