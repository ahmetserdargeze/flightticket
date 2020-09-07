package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.service.contract.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "airports")
public class AirportController {
    @Autowired
    AirportService airportService;


    @PostMapping("/create")
    ResponseEntity<SaveResponse> createAirport(@RequestParam String name) {
        return new ResponseEntity<SaveResponse>(airportService.createAirport(name), HttpStatus.OK);
    }


    @GetMapping("/search")
    ResponseEntity<SearchResponse> searchAirportWithName(@RequestParam String name) {
        SearchResponse response = airportService.searchWithAirportName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/list")
    ResponseEntity<SearchResponse> listAirports() {
        return new ResponseEntity<SearchResponse>(airportService.listAirports(), HttpStatus.OK);
    }


}
