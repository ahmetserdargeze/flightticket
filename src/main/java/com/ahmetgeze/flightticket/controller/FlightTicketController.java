package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.model.request.SellTicketInputs;
import com.ahmetgeze.flightticket.model.response.MultipleSaveResponse;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.service.contract.FlightTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/flightTicket")
public class FlightTicketController {
    @Autowired
    FlightTicketService flightTicketService;

    @PostMapping("/sell")
    @ResponseBody
    ResponseEntity<MultipleSaveResponse> createFlightRecord(SellTicketInputs inputs) {
        return new ResponseEntity<MultipleSaveResponse>(flightTicketService.sellTicket(inputs), HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<SearchResponse> searchFlightRecord(@RequestParam UUID flightTicketId) {
        return new ResponseEntity<SearchResponse>(flightTicketService.searchFlightTicket(flightTicketId), HttpStatus.OK);
    }

    @PostMapping("/return")
    ResponseEntity<SaveResponse> returnFlightRecord(@RequestParam UUID flightTicketId) {
        return new ResponseEntity<SaveResponse>(flightTicketService.returnFlightTicket(flightTicketId), HttpStatus.OK);
    }

}
