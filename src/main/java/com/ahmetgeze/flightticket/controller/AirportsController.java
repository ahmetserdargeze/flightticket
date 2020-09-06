package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.dao.impl.AirportsDaoImpl;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.service.contract.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "airports")
public class AirportsController {

    @Autowired
    AirportsDaoImpl airportsDaoImpl;

    @Autowired
    AirportService airportService;


    @GetMapping("/searchWithName")
    ResponseEntity<SearchResponse> searchAirportWithName(@RequestParam String name) {
        SearchResponse response = airportService.searchAirport(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/save")
    ResponseEntity<SaveResponse> saveAirport(@RequestParam String name) {
        return new ResponseEntity<SaveResponse>(airportService.saveAirport(name), HttpStatus.OK);
    }


}
