package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.repository.AirlinesCompanyRepository;
import com.ahmetgeze.flightticket.repository.AirportRepository;
import com.ahmetgeze.flightticket.repository.RouteRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AirlinesCompanyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    AirlinesCompanyRepository airlinesCompanyRepository;

    Gson gson = new Gson();


    @AfterEach
    void delete() {
        airlinesCompanyRepository.deleteAll();
    }

    @Test
    public void testCreateEndPoint()  {
        StringBuilder  testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/airlinesCompany/");
        testUrl.append("/create");
        testUrl.append("?airlinesCompanyName={airlinesCompanyName}");
        ResponseEntity<SaveResponse> responseEntity = restTemplate.postForEntity(testUrl.toString(),
                null,
                SaveResponse.class,
                "TEST");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        AirlinesCompany createdAirlinesCompany = gson.fromJson(gson.toJson(responseEntity.getBody().getInsertedObject()), AirlinesCompany.class);
        AirlinesCompany insertedAirlinesCompany = airlinesCompanyRepository.findByName("TEST");
        assertNotNull(createdAirlinesCompany);
        assertNotNull(insertedAirlinesCompany);
        assertEquals(insertedAirlinesCompany,createdAirlinesCompany);
    }


}
