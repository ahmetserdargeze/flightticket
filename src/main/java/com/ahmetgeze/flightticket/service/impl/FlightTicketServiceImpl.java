package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.dao.contract.FlightRecordDao;
import com.ahmetgeze.flightticket.dao.contract.FlightTicketDao;
import com.ahmetgeze.flightticket.entity.FlightRecord;
import com.ahmetgeze.flightticket.entity.FlightTicket;
import com.ahmetgeze.flightticket.entity.SellRecord;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.model.request.SellTicketInputs;
import com.ahmetgeze.flightticket.model.response.MultipleSaveResponse;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.repository.FlightTicketRepository;
import com.ahmetgeze.flightticket.repository.SellRecordRepository;
import com.ahmetgeze.flightticket.service.contract.FlightTicketService;
import com.ahmetgeze.flightticket.service.contract.PriceCalculatorService;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FlightTicketServiceImpl implements FlightTicketService {

    @Autowired
    FlightTicketDao flightTicketDao;

    @Autowired
    FlightTicketRepository flightTicketRepository;

    @Autowired
    SellRecordRepository sellRecordRepository;


    @Autowired
    FlightRecordDao flightRecordDao;

    @Autowired
    PriceCalculatorService priceCalculatorService;

    @Override
    public MultipleSaveResponse sellTicket(SellTicketInputs sellTicketInputs) {
        if (sellTicketInputs.validateInput()) {
            FlightRecord flightRecord = flightRecordDao.getById(sellTicketInputs.getFlightRecordId());
            int selledflightTickets = flightTicketDao.getActiveTicketInFlightRecord(sellTicketInputs.getFlightRecordId()).intValue();

            if (priceCalculatorService.isHasAvailableTicket(flightRecord, sellTicketInputs.getSeatCount(), selledflightTickets)) {
                List<FlightTicket> selledFlightTicketList = new ArrayList<>();
                SellRecord sellRecord = new SellRecord();
                sellRecord.setCvvCode(sellTicketInputs.getPaymentDetails().getCvvCode());
                sellRecord.setCardNumber(UtilsFunc.maskingCreditCardNumber(sellTicketInputs.getPaymentDetails().getCardNumber()));
                sellRecord.setCardDate(sellTicketInputs.getPaymentDetails().getCardDate());
                sellRecord.setCardPlaceHolder(sellTicketInputs.getPaymentDetails().getCardPleaceholderName());
                for (int i = 0; i < sellTicketInputs.getSeatCount(); i++) {
                    FlightTicket selledflightTicket = new FlightTicket();
                    selledflightTicket.setSellRecord(sellRecord);
                    selledflightTicket.setFlightRecord(flightRecord);
                    selledflightTicket.setSelledDate(new Date());
                    Double unitPrice = priceCalculatorService.calculateTicketPrice(flightRecord, selledflightTickets);
                    selledflightTicket.setPrice(unitPrice);
                    sellRecord.addItemToFlightTickets(selledflightTicket);
                    selledFlightTicketList.add(selledflightTicket);
                    selledflightTickets += 1;
                }
                selledFlightTicketList = flightTicketDao.saveFlightTicketAndSellRecordInTransaction(selledFlightTicketList, sellRecord);
                return new MultipleSaveResponse(HttpStatus.OK, "Selling operaitons   end   with Succes", true, selledFlightTicketList);
            } else {
                throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.AVAILABLE_FLIGHT_TICKET_GET_ERR_1, new NullPointerException()));
            }
        } else {
            throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }

    @Override
    public SaveResponse returnFlightTicket(UUID flightTicketId) {
        if (UtilsFunc.isNotNull(flightTicketId)) {
            FlightTicket flightTicket = flightTicketDao.getById(flightTicketId);
            flightTicket.setActive(false);
            flightTicket = flightTicketDao.saveFlightTicket(flightTicket);
            return new SaveResponse(HttpStatus.OK, "Return  Ticket  with Succes", true, flightTicket);
        } else {
            throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }

    @Override
    public SearchResponse searchFlightTicket(UUID flightTicketId) {
        if (UtilsFunc.isNotNull(flightTicketId)) {
            FlightTicket flightTicket = flightTicketDao.getById(flightTicketId);
            List<FlightTicket> ticketList = new ArrayList<>();
            ticketList.add(flightTicket);
            return new SearchResponse(HttpStatus.OK, "Search  Ticket  with Succes", true, ticketList);
        } else {
            throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }
}
