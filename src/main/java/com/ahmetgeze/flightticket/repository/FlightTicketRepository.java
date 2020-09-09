package com.ahmetgeze.flightticket.repository;

import com.ahmetgeze.flightticket.entity.FlightTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FlightTicketRepository extends JpaRepository<FlightTicket, UUID> {

    Optional<FlightTicket> findById(UUID flightTicketId);

    @Query("SELECT COUNT(1) FROM FlightTicket f WHERE f.flightRecord.id=:flightRecordId and  f.active = true")
    Long getSelledSeatCount(@Param("flightRecordId") UUID flightRecordId);




}
