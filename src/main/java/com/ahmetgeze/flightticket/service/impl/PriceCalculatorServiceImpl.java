package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.dao.contract.FlightTicketDao;
import com.ahmetgeze.flightticket.entity.FlightRecord;
import com.ahmetgeze.flightticket.service.contract.PriceCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PriceCalculatorServiceImpl implements PriceCalculatorService {

    @Autowired
    FlightTicketDao flightTicketDao;

    static double quotaRate = 0.10;
    static double raiseRate = 0.10;


    @Override
    public boolean isHasAvailableTicket(FlightRecord flightRecord, int requestedSeatCount, int selledTicketCount) {
        boolean result = false;
        if ((flightRecord.getFligtSeatCount() - selledTicketCount) >= requestedSeatCount) {
            result = true;
        }
        return result;
    }

    @Override
    public double calculateTicketPrice(FlightRecord flightRecord, int selledTicketCount) {
        int capacity = flightRecord.getFligtSeatCount();
        int actualSellingSeatNumber = selledTicketCount + 1;
        double rangeUnit = capacity * quotaRate;
        double indexRange = actualSellingSeatNumber / rangeUnit;
        if (indexRange % 1 == 0) {
            indexRange = indexRange - 1;
        } else {
            indexRange = new BigDecimal(String.valueOf(indexRange)).intValue();
        }
        if (indexRange == 0) {
            return flightRecord.getUnitPrice();
        } else {
            double lastPrice = flightRecord.getUnitPrice();
            for (int i = 0; i < indexRange; i++) {
                lastPrice += lastPrice * raiseRate;
            }
            return lastPrice;
        }


    }


}



