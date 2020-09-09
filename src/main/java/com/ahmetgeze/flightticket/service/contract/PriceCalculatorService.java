package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.entity.FlightRecord;

import java.util.UUID;

public interface PriceCalculatorService {

    boolean isHasAvailableTicket(FlightRecord flightRecord, int requestedSeatCount,int selledTicketCount);

    double calculateTicketPrice(FlightRecord flightRecord,int selledTicketCount);


}
