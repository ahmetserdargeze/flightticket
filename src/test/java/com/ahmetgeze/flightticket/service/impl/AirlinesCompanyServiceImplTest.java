package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.model.response.SearchResponse;
import com.ahmetgeze.flightticket.repository.AirlinesCompanyRepository;
import com.ahmetgeze.flightticket.service.contract.AirlinesCompanyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AirlinesCompanyServiceImplTest {

    @Autowired
    AirlinesCompanyService airlinesCompanyService;

    @Autowired
    AirlinesCompanyRepository airlinesCompanyRepository;

    @AfterEach
    void cleanWorkspace() {
        airlinesCompanyRepository.deleteAll();
    }
    String airlinceCompanyName = "MUSTAFA KEMAL ATATÜRK HAVAYOLLARI";

    AirlinesCompany airlinesCompany1 = new AirlinesCompany();
    AirlinesCompany airlinesCompany2 = new AirlinesCompany();
    AirlinesCompany airlinesCompany3 = new AirlinesCompany();

    @BeforeEach
    void setUp() {
        airlinesCompany1.setName("ESENBOĞA COMP");
        airlinesCompany2.setName("SABİHA GÖKÇEN COMP");
        airlinesCompany3.setName("DALAMAN COMP");

        airlinesCompany1 = airlinesCompanyRepository.save(airlinesCompany1);
        airlinesCompany2 = airlinesCompanyRepository.save(airlinesCompany2);
        airlinesCompany3 = airlinesCompanyRepository.save(airlinesCompany3);
    }

    @AfterEach
    void delete() {
        airlinesCompanyRepository.deleteAll();
    }

    @Test
    void createAirlinesCompanyTest() {
        AirlinesCompany result = (AirlinesCompany) airlinesCompanyService.createAirlinesCompany(airlinceCompanyName).getInsertedObject();
        AirlinesCompany insertedData = airlinesCompanyRepository.findByName(airlinceCompanyName);
        assertNotNull(result);
        assertNotNull(insertedData);
        assertEquals(insertedData,result);
    }
    @Test
    void createAirlinesCompanyDuplicateNameTest() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            airlinesCompanyService.createAirlinesCompany(airlinceCompanyName);
            airlinesCompanyService.createAirlinesCompany(airlinceCompanyName);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.DB_EXEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.AIRLINES_COMPANY_SAVE_ERR_1);
    }

    @Test
    void createAirlinesCompanyNullNameTest() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            airlinesCompanyService.createAirlinesCompany(null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }

    @Test
    void searchWithAirlinesCompanyNameTest() {
        SearchResponse result = airlinesCompanyService.searchWithAirlinesCompanyName(airlinesCompany2.getName());
        List<AirlinesCompany> resultList = (List<AirlinesCompany>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 1);
        assertEquals(airlinesCompany2, resultList.get(0));
    }

    @Test
    void searchWithAirlinesCompanyNameTestForMultipleResult() {
        SearchResponse result = airlinesCompanyService.searchWithAirlinesCompanyName("COMP");
        List<AirlinesCompany> resultList = (List<AirlinesCompany>) result.getSearchResult();
        assertNotNull(resultList);
        assertTrue(resultList.size() == 3);
        assertThat(resultList, containsInAnyOrder(airlinesCompany1,airlinesCompany2,airlinesCompany3));
    }

    @Test
    void searchWithAirlinesCompanyNameTestForNullAirlinesCompanyName() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            SearchResponse result = airlinesCompanyService.searchWithAirlinesCompanyName(null);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.SERVİCE_EXCEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.NULL_INPUT_ERR);
    }

}
