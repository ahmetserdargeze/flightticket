package com.ahmetgeze.flightticket.dao.impl;

import com.ahmetgeze.flightticket.dao.contract.AirlinesCompanyDao;
import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.AirlinesCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AirlinesCompanyDaoImpl implements AirlinesCompanyDao {

    @Autowired
    AirlinesCompanyRepository airlinesCompanyRepository;

    @Override
    public AirlinesCompany saveAirlines(AirlinesCompany airlinesCompany) {
        try {
            return airlinesCompanyRepository.save(airlinesCompany);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.AIRLINES_COMPANY_SAVE_ERR_1, e));
        }
    }


}
