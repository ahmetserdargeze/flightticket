package com.ahmetgeze.flightticket.service.impl;

import com.ahmetgeze.flightticket.dao.contract.AirlinesCompanyDao;
import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.model.response.SaveResponse;
import com.ahmetgeze.flightticket.service.contract.AirlinesCompanyService;
import com.ahmetgeze.flightticket.utils.UtilsFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AirlinesCompanyServiceImpl implements AirlinesCompanyService {

    @Autowired
    AirlinesCompanyDao airlinesCompanyDao;

    @Override
    public SaveResponse createAirlinesCompany(String airlinesCompanyName) {
        if (UtilsFunc.isNotNull(airlinesCompanyName)) {
            AirlinesCompany airlinesCompany = new AirlinesCompany();
            airlinesCompany.setName(UtilsFunc.toUpperCaseWithTurkishCharacter(airlinesCompanyName));
            airlinesCompany = airlinesCompanyDao.saveAirlines(airlinesCompany);
            return new SaveResponse(HttpStatus.OK, "Save New Airlines Company with Succes", true, airlinesCompany);
        } else {
            throw (new GeneralException(ExceptionCategory.SERVİCE_EXCEPTİON, ExceptionCode.NULL_INPUT_ERR, new NullPointerException()));
        }
    }

}
