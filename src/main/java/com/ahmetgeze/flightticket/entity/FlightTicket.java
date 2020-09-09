package com.ahmetgeze.flightticket.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "flight_ticket")

public class FlightTicket extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "sell_record_fk",referencedColumnName = "id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties( allowSetters=true,value = "flightTickets")
    private SellRecord sellRecord;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "flight_record_fk", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FlightRecord flightRecord;

    @Column(name = "selled_date")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date selledDate;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private double price;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getSelledDate() {
        return selledDate;
    }

    public void setSelledDate(Date selledDate) {
        this.selledDate = selledDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public SellRecord getSellRecord() {
        return sellRecord;
    }

    public void setSellRecord(SellRecord sellRecord) {
        this.sellRecord = sellRecord;
    }

    public FlightRecord getFlightRecord() {
        return flightRecord;
    }

    public void setFlightRecord(FlightRecord flightRecord) {
        this.flightRecord = flightRecord;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightTicket that = (FlightTicket) o;
        return active == that.active &&
                Objects.equals(id, that.id) &&
                Objects.equals(flightRecord, that.flightRecord) &&
                Objects.equals(selledDate.getTime(), that.selledDate.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, selledDate, active);
    }

    @Override
    public String toString() {
        return "FlightTicket{" +
                "id=" + id +
                ", flightRecord=" + flightRecord +
                ", selledDate=" + selledDate +
                ", active=" + active +
                ", price=" + price +
                '}';
    }
}
