package com.ahmetgeze.flightticket.repository;

import com.ahmetgeze.flightticket.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AirportRepository extends JpaRepository<Airport, UUID> {
    List<Airport> findByNameContains(String name);
    Optional<Airport> findByName(String name);
}
