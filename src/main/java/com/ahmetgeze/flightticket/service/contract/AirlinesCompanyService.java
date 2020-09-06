package com.ahmetgeze.flightticket.service.contract;

import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.model.response.SearchResponse;

public interface AirlinesCompanyService {
    SaveResponse createAirlinesCompany(String airlinesCompanyName);

    SearchResponse searchWithAirlinesCompanyName(String airlinesCompanyName);


}
