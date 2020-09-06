package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.model.response.SaveResponse;

public interface AirlinesCompanyService {
    SaveResponse createAirlinesCompany(String airlinesCompanyName);

}
