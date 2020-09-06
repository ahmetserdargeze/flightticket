package com.ahmetgeze.flightticket.repository;

import com.ahmetgeze.flightticket.entity.AirlinesCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AirlinesCompanyRepository extends JpaRepository<AirlinesCompany, UUID> {
    AirlinesCompany findByName(String airlinesCompanyName);
}
