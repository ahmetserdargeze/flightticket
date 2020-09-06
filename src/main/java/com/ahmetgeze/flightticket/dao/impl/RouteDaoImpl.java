package com.ahmetgeze.flightticket.dao.impl;


import com.ahmetgeze.flightticket.dao.contract.RouteDaoContract;
import com.ahmetgeze.flightticket.entity.Route;
import com.ahmetgeze.flightticket.model.exception.ExceptionCategory;
import com.ahmetgeze.flightticket.model.exception.ExceptionCode;
import com.ahmetgeze.flightticket.model.exception.GeneralException;
import com.ahmetgeze.flightticket.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteDaoImpl implements RouteDaoContract {

    @Autowired
    RouteRepository routeRepository;


    @Override
    public Boolean saveRoute(Route route) {
        try {
            routeRepository.save(route);
            return true;
        }catch (Exception e){
            throw(new GeneralException(ExceptionCategory.DB_EXEPTİON, ExceptionCode.ROUTE_SAVE_ERR_1,e));
        }
    }
}
