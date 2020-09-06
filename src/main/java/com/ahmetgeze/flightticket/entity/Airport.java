package com.ahmetgeze.flightticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "airport")
public class Airport extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;


    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "departureAirport")
    @JsonIgnore
    private List<Route> departureRoute;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "arrivalAirport")
    @JsonIgnore
    private List<Route> arrivalRoute;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.isEmpty() ? null : name;
    }

    public List<Route> getDepartureRoute() {
        return departureRoute;
    }

    public void setDepartureRoute(List<Route> departureRoute) {
        this.departureRoute = departureRoute;
    }

    public List<Route> getArrivalRoute() {
        return arrivalRoute;
    }

    public void setArrivalRoute(List<Route> arrivalRoute) {
        this.arrivalRoute = arrivalRoute;
    }
}
