package com.ahmetgeze.flightticket.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "flight_ticket")
public class FlightTicket extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sell_record_fk", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SellRecord sellRecord;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "flight_record_fk", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FlightRecord flightRecord;

    @Column(name = "selled_date")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date selledDate;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

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

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightTicket that = (FlightTicket) o;
        return seatNumber == that.seatNumber &&
                isActive == that.isActive &&
                Objects.equals(id, that.id) &&
                Objects.equals(sellRecord, that.sellRecord) &&
                Objects.equals(flightRecord, that.flightRecord) &&
                Objects.equals(selledDate.getTime(), that.selledDate.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sellRecord, flightRecord, selledDate, seatNumber, isActive);
    }
}
