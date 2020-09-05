package com.ahmetgeze.flightticket.repository;

import com.ahmetgeze.flightticket.entity.Airports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AirportsRepository extends JpaRepository<Airports, UUID> {
    List<Airports> findByNameContains(String name);
}
