package com.ahmetgeze.flightticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "flight_record")
public class FlightRecord extends BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;


    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "airlines_company_fk", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AirlinesCompany airlinesCompany;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "route_fk", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Route route;


    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "flightRecord")
    @JsonIgnore
    @Column(nullable = true)
    private List<FlightTicket> flightTickets;


    @Column(name = "flight_seat_count", nullable = false)
    private int fligtSeatCount;

    @Column(name = "available_seat_count", nullable = false)
    private int availableSeatCount;


    @Column(name = "departure_date")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date departureDate;


    @Column(name = "arrival_date")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalDate;


    @Column(name = "unit_price")
    private double unitPrice;


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

    public AirlinesCompany getAirlinesCompany() {
        return airlinesCompany;
    }

    public void setAirlinesCompany(AirlinesCompany airlinesCompany) {
        this.airlinesCompany = airlinesCompany;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getFligtSeatCount() {
        return fligtSeatCount;
    }

    public void setFligtSeatCount(int fligtSeatCount) {
        this.fligtSeatCount = fligtSeatCount;
    }

    public int getAvailableSeatCount() {
        return availableSeatCount;
    }

    public void setAvailableSeatCount(int availableSeatCount) {
        this.availableSeatCount = availableSeatCount;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<FlightTicket> getFlightTickets() {
        return flightTickets;
    }

    public void setFlightTickets(List<FlightTicket> flightTickets) {
        this.flightTickets = flightTickets;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightRecord that = (FlightRecord) o;
        return fligtSeatCount == that.fligtSeatCount &&
                availableSeatCount == that.availableSeatCount &&
                Double.compare(that.unitPrice, unitPrice) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(airlinesCompany, that.airlinesCompany) &&
                Objects.equals(route, that.route) &&
                Objects.equals(departureDate.getTime(), that.departureDate.getTime()) &&
                Objects.equals(arrivalDate.getTime(), that.arrivalDate.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, airlinesCompany, route, flightTickets, fligtSeatCount, availableSeatCount, departureDate, arrivalDate, unitPrice);
    }
}
