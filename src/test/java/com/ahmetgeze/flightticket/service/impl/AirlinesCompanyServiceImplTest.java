package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.AirlinesCompanyRepository;
import com.ahmetgeze.flightticket.service.contract.AirlinesCompanyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    String airportName1 = "MUSTAFA KEMAL ATATÜRK HAVAYOLLARI";

    @Test
    void createAirlinesCompanyTest() {
        AirlinesCompany result = (AirlinesCompany) airlinesCompanyService.createAirlinesCompany(airportName1).getInsertedObject();
        AirlinesCompany insertedData = airlinesCompanyRepository.findByName(airportName1);
        assertNotNull(result);
        assertNotNull(insertedData);
        assertEquals(insertedData,result);
    }
    @Test
    void createAirlinesCompanyDuplicateNameTest() {
        GeneralException exception = Assertions.assertThrows(GeneralException.class, () -> {
            airlinesCompanyService.createAirlinesCompany(airportName1);
            airlinesCompanyService.createAirlinesCompany(airportName1);
        });
        assertNotNull(exception);
        assertEquals(exception.getCategory(), ExceptionCategory.DB_EXEPTİON);
        assertEquals(exception.getCode(), ExceptionCode.AIRLINES_COMPANY_SAVE_ERR_1);
    }
}
