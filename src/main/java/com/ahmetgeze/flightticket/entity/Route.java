package com.ahmetgeze.flightticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "route")
public class Route extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    //    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "departure_fk", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Airport departureAirport;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "arrival_fk", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Airport arrivalAirport;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "route")
    @JsonIgnore
    private List<FlightRecord>  flightRecords;

    public List<FlightRecord> getFlightRecords() {
        return flightRecords;
    }

    public void setFlightRecords(List<FlightRecord> flightRecords) {
        this.flightRecords = flightRecords;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureAirport, arrivalAirport);
    }
}
