package com.ahmetgeze.flightticket.dao.contract;

import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import com.ahmetgeze.flightticket.entity.Airport;

import java.util.List;

public interface AirlinesCompanyDao {
    AirlinesCompany saveAirlinesCompany(AirlinesCompany airlinesCompany) ;
    List<AirlinesCompany> searchAirlinesCompanyWithName(String AirlinesCompanyName) ;

}
