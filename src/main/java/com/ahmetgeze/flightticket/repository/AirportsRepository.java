package com.ahmetgeze.flightticket.repository;

import com.ahmetgeze.flightticket.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AirportsRepository extends JpaRepository<Airport, UUID> {
    List<Airport> findByNameContains(String name);
}
