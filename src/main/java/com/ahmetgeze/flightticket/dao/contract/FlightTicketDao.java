package com.ahmetgeze.flightticket.dao.contract;

import com.ahmetgeze.flightticket.entity.FlightRecord;
import com.ahmetgeze.flightticket.entity.FlightTicket;
import com.ahmetgeze.flightticket.entity.SellRecord;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;

import java.util.List;
import java.util.UUID;

public interface FlightTicketDao {
     Long getActiveTicketInFlightRecord(UUID flightRecordId);
     List<FlightTicket> saveFlightTicketAndSellRecordInTransaction(List<FlightTicket> selledTicket, SellRecord sellRecord);
     FlightTicket getById(UUID flightTicketId) ;
     FlightTicket saveFlightTicket(FlightTicket flightTicket);

}
