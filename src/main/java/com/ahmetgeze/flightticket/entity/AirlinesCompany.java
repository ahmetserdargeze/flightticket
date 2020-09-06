package com.ahmetgeze.flightticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "airlines_company")
public class AirlinesCompany extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;


    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "airlinesCompany")
    @JsonIgnore
    private List<FlightRecord> flightRecords;


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
        this.name = name;
    }

    public List<FlightRecord> getFlightRecords() {
        return flightRecords;
    }

    public void setFlightRecords(List<FlightRecord> flightRecords) {
        this.flightRecords = flightRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirlinesCompany that = (AirlinesCompany) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
