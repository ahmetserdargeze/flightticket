package com.ahmetgeze.flightticket.dao.impl;

import com.ahmetgeze.flightticket.dao.contract.AirlinesCompanyDao;
import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import com.ahmetgeze.flightticket.entity.Airport;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.AirlinesCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AirlinesCompanyDaoImpl implements AirlinesCompanyDao {

    @Autowired
    AirlinesCompanyRepository airlinesCompanyRepository;

    @Override
    public AirlinesCompany getById(UUID airlinesCompanyId) {
        try {
            return airlinesCompanyRepository.findById(airlinesCompanyId).get();
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.AIRLINES_COMPANY_GET_ERR_1, e));
        }
    }

    @Override
    public AirlinesCompany saveAirlinesCompany(AirlinesCompany airlinesCompany) {
        try {
            return airlinesCompanyRepository.save(airlinesCompany);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.AIRLINES_COMPANY_SAVE_ERR_1, e));
        }
    }

    @Override
    public List<AirlinesCompany> searchAirlinesCompanyWithName(String AirlinesCompanyName) {
        try {
            return airlinesCompanyRepository.findByNameContains(AirlinesCompanyName);
        } catch (Exception e) {
            throw (new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.AIRLINES_SEARCH_ERR_1, e));
        }
    }


}
