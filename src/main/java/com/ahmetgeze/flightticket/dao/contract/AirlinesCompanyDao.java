package com.ahmetgeze.flightticket.dao.contract;

import com.ahmetgeze.flightticket.entity.AirlinesCompany;

import java.util.List;
import java.util.UUID;

public interface AirlinesCompanyDao {
    AirlinesCompany getById(UUID airlinesCompanyId);
    AirlinesCompany saveAirlinesCompany(AirlinesCompany airlinesCompany) ;
    List<AirlinesCompany> searchAirlinesCompanyWithName(String AirlinesCompanyName) ;

}
