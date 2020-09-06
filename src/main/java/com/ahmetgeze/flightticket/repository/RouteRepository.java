package com.ahmetgeze.flightticket.repository;

import com.ahmetgeze.flightticket.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {

    List<Route> findByArrivalAirportId(UUID arrivalAirportId);

    List<Route> findByDepartureAirportId(UUID depertureAirportId);

    @Query(value = "SELECT r FROM Route as r  where r.departureAirport.id in (select a.id from Airport  as a where a.name like  %?1%)")
    List<Route> findByDepertureAirportNameSearch(String depertureAirpotName);

    @Query(value = "SELECT r FROM Route as r  where r.arrivalAirport.id in (select a.id from Airport  as a where a.name like  %?1%)")
    List<Route> findByArrivalAirportNameSearch(String arrivalAirpotName);

    @Query(value = "SELECT r FROM Route as r  " +
            " where " +
            " r.departureAirport.id in (select a.id from Airport  as a where a.name like  %?1%)  and" +
            " r.arrivalAirport.id in (select a.id from Airport  as a where a.name like  %?2%) ")
    List<Route> findByDepertureAndArrivalAirportNameSearch(String depertureAirpotName, String arrivalAirpotName);

}
