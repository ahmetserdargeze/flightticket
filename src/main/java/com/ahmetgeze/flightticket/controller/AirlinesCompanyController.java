package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.service.contract.AirlinesCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "airlinesCompany")
public class AirlinesCompanyController {

    @Autowired
    AirlinesCompanyService airlinesCompanyService;

    @PostMapping("/create")
    ResponseEntity<SaveResponse> createAirlinesCompany(@RequestParam String airlinesCompanyName) {
        return new ResponseEntity<SaveResponse>(airlinesCompanyService.createAirlinesCompany(airlinesCompanyName), HttpStatus.OK);
    }

    @GetMapping("/searchWithName")
    ResponseEntity<SearchResponse> searchAirlinesCompanyWithName(@RequestParam String airlinesCompanyName) {
        SearchResponse response = airlinesCompanyService.searchWithAirlinesCompanyName(airlinesCompanyName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
