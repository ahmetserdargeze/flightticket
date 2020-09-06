package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.service.contract.RouteService;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    RouteService routeService;

    @PostMapping("/create")
    ResponseEntity<SaveResponse> createRoute(@RequestParam UUID departureId, @RequestParam UUID arrivalId) {
        return new ResponseEntity<SaveResponse>(routeService.createRoute(departureId, arrivalId), HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<SearchResponse> searchRoute(@RequestParam String departureName, @RequestParam String arrivalName) {
        if (UtilsFunc.isNotNull(departureName, arrivalName)) {
            return new ResponseEntity<SearchResponse>(routeService.searchWithDepertureNameAndArrivalName(departureName, arrivalName), HttpStatus.OK);
        } else if (UtilsFunc.isNotNull(departureName)) {
            return new ResponseEntity<SearchResponse>(routeService.searchWithDepertureName(departureName), HttpStatus.OK);
        } else if (UtilsFunc.isNotNull(arrivalName)) {
            return new ResponseEntity<SearchResponse>(routeService.searchWithArrivalName(arrivalName), HttpStatus.OK);
        } else {
            throw (new GeneralException(ExceptionCategory.REST_EXCEPTION, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }

    @GetMapping("/filter")
    ResponseEntity<SearchResponse> searchRoute(@RequestParam UUID departureId, @RequestParam UUID arrivalId) {
        if (UtilsFunc.isNotNull(departureId)) {
            return new ResponseEntity<SearchResponse>(routeService.filterWithDepertureId(departureId), HttpStatus.OK);
        } else if (UtilsFunc.isNotNull(arrivalId)) {
            return new ResponseEntity<SearchResponse>(routeService.filterWithArrivalId(arrivalId), HttpStatus.OK);
        } else {
            throw (new GeneralException(ExceptionCategory.REST_EXCEPTION, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }


}
