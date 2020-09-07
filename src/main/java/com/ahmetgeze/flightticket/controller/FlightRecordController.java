package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.service.contract.FlightRecordService;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/flightRecord")
public class FlightRecordController {

    @Autowired
    FlightRecordService flightRecordService;

    @PostMapping("/create")
    ResponseEntity<SaveResponse> createFlightRecord(@RequestParam String flightRecordName,
                                                    @RequestParam UUID airlinesCompanyId,
                                                    @RequestParam UUID routeId,
                                                    @RequestParam int flightSeatCout,
                                                    @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date departureDate,
                                                    @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") Date arrivalDate) {
        return new ResponseEntity<SaveResponse>(flightRecordService.createFlightRecord(flightRecordName, airlinesCompanyId, routeId, flightSeatCout, departureDate, arrivalDate), HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<SearchResponse> searchFlightRecord(@RequestParam UUID airlinesCompanyId, @RequestParam UUID routeId, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date departureDate) {
        return new ResponseEntity<SearchResponse>(flightRecordService.searchFlightRecord(airlinesCompanyId, routeId, departureDate), HttpStatus.OK);
    }

    @GetMapping("/list")
    ResponseEntity<SearchResponse> listFlightRecord() {
        return new ResponseEntity<SearchResponse>(flightRecordService.listAllFlightRecord(), HttpStatus.OK);
    }


}
