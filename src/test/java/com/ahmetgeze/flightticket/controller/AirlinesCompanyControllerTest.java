package com.ahmetgeze.flightticket.controller;

import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.repository.AirlinesCompanyRepository;
import com.ahmetgeze.flightticket.repository.AirportRepository;
import com.ahmetgeze.flightticket.repository.RouteRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AirlinesCompanyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    AirlinesCompanyRepository airlinesCompanyRepository;

    Gson gson = new Gson();

    AirlinesCompany airlinesCompany1 = new AirlinesCompany();
    AirlinesCompany airlinesCompany2 = new AirlinesCompany();
    AirlinesCompany airlinesCompany3 = new AirlinesCompany();

    @BeforeEach
    void setUp() {
        airlinesCompany1.setName("ESENBOĞA");
        airlinesCompany2.setName("SABİHA GÖKÇEN");
        airlinesCompany3.setName("DALAMAN");

        airlinesCompany1 = airlinesCompanyRepository.save(airlinesCompany1);
        airlinesCompany2 = airlinesCompanyRepository.save(airlinesCompany2);
        airlinesCompany3 = airlinesCompanyRepository.save(airlinesCompany3);
    }

    @AfterEach
    void delete() {
        airlinesCompanyRepository.deleteAll();
    }

    @Test
    public void testCreateEndPoint() {
        StringBuilder testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/airlinesCompany/");
        testUrl.append("/create");
        testUrl.append("?airlinesCompanyName={airlinesCompanyName}");
        ResponseEntity<SaveResponse> responseEntity = restTemplate.postForEntity(testUrl.toString(),
                null,
                SaveResponse.class,
                "TEST");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        AirlinesCompany createdAirlinesCompany = gson.fromJson(gson.toJson(responseEntity.getBody().getInsertedObject()), AirlinesCompany.class);
        AirlinesCompany insertedAirlinesCompany = airlinesCompanyRepository.findByName("TEST");
        assertNotNull(createdAirlinesCompany);
        assertNotNull(insertedAirlinesCompany);
        assertEquals(insertedAirlinesCompany, createdAirlinesCompany);
    }

    @Test
    public void testSearchEndPoint() {
        StringBuilder testUrl = new StringBuilder();
        testUrl.append("http://localhost:");
        testUrl.append(port);
        testUrl.append("/airlinesCompany/");
        testUrl.append("/searchWithName");
        testUrl.append("?airlinesCompanyName={airlinesCompanyName}");
        ResponseEntity<SearchResponse> responseEntity = restTemplate.getForEntity(testUrl.toString(),
                SearchResponse.class,
                airlinesCompany1.getName());
        List<AirlinesCompany> searchList = gson.fromJson(gson.toJson(responseEntity.getBody().getSearchResult()), new TypeToken<List<AirlinesCompany>>() {
        }.getType());
        assertNotNull(searchList);
        assertTrue(searchList.size() == 1);
        assertEquals(airlinesCompany1, searchList.get(0));
    }


}
