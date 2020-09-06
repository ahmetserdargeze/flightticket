package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.service.contract.AirlinesCompanyService;
import com.ahmetgeze.flightticket.service.contract.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "airlinesCompany")
public class AirlinesCompanyController {

    @Autowired
    AirlinesCompanyService airlinesCompanyService;

    @PostMapping("/create")
    ResponseEntity<SaveResponse> createAirlinesCompany(@RequestParam String airlinesCompanyName) {
        return new ResponseEntity<SaveResponse>(airlinesCompanyService.createAirlinesCompany(airlinesCompanyName), HttpStatus.OK);
    }
}
