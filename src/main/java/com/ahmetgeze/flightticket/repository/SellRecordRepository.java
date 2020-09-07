package com.ahmetgeze.flightticket.repository;

import com.ahmetgeze.flightticket.entity.SellRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SellRecordRepository extends JpaRepository<SellRecord, UUID> {
}
