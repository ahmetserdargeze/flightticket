package com.ahmetgeze.flightticket.dao.impl;

import com.ahmetgeze.flightticket.dao.contract.FlightTicketDao;
import com.ahmetgeze.flightticket.entity.FlightTicket;
import com.ahmetgeze.flightticket.entity.SellRecord;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.FlightRecordRepository;
import com.ahmetgeze.flightticket.repository.FlightTicketRepository;
import com.ahmetgeze.flightticket.repository.SellRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
public class FlightTicketDaoImpl implements FlightTicketDao {
    @Autowired
    FlightTicketRepository flightTicketRepository;

    @Autowired
    SellRecordRepository sellRecordRepository;

    @Override
    public Long getActiveTicketInFlightRecord(UUID flightRecordId) {
        try {
            return flightTicketRepository.getSelledSeatCount(flightRecordId);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.FLIGHT_TICKET_GET_ERR_1, e));
        }
    }

    @Override
    @Transactional(rollbackOn = GeneralException.class)
    public List<FlightTicket> saveFlightTicketAndSellRecordInTransaction(List<FlightTicket> selledTickets, SellRecord sellRecord) {
        try {
            sellRecord = sellRecordRepository.save(sellRecord);
            return flightTicketRepository.saveAll(selledTickets);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.FLIGHT_TICKET_SELL_ERR_1, e));
        }
    }

    @Override
    public FlightTicket getById(UUID flightTicketId) {
        try {
            return flightTicketRepository.findById(flightTicketId).get();
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.FLIGHT_TICKET_GET_ERR_2, e));
        }
    }

    @Override
    public FlightTicket saveFlightTicket(FlightTicket flightTicket) {
        try {
            return flightTicketRepository.save(flightTicket);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.FLIGHT_TICKET_SAVE_ERR_1, e));
        }
    }

}
