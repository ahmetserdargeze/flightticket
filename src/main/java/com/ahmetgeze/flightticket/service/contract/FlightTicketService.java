package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.model.request.SellTicketInputs;
import com.ahmetgeze.flightticket.model.response.MultipleSaveResponse;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface FlightTicketService {

    MultipleSaveResponse sellTicket(SellTicketInputs sellTicketInputs);

    SaveResponse returnFlightTicket(UUID flightTicketId);

    SearchResponse searchFlightTicket(UUID flightTicketId);


}
