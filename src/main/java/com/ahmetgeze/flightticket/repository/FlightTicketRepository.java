package com.ahmetgeze.flightticket.repository;

import com.ahmetgeze.flightticket.entity.FlightTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightTicketRepository extends JpaRepository<FlightTicket, UUID> {
}
