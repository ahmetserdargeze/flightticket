package com.ahmetgeze.flightticket.repository;

import com.ahmetgeze.flightticket.entity.FlightRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRecordRepository extends JpaRepository<FlightRecord, UUID> {
    List<FlightRecord> findByAirlinesCompanyIdAndRouteIdAndDepartureDateBetween(UUID companyId, UUID routeId, Date depertureStartDate,Date depertureEndDate);


}
