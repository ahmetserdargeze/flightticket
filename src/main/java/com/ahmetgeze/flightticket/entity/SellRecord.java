package com.ahmetgeze.flightticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "sell_record")
public class SellRecord extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sellRecord")
    @JsonIgnore
    private FlightTicket flightTicket;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, name = "increase_percentage")
    private Double increasePercentage;

    @Size(max = 16, min = 16)
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Size(max = 5, min = 5)
    @Column(name = "card_date", nullable = false)
    private String cardDate;


    @Column(name = "cvv_code", nullable = false)
    private String cvvCode;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FlightTicket getFlightTicket() {
        return flightTicket;
    }

    public void setFlightTicket(FlightTicket flightTicket) {
        this.flightTicket = flightTicket;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getIncreasePercentage() {
        return increasePercentage;
    }

    public void setIncreasePercentage(Double increasePercentage) {
        this.increasePercentage = increasePercentage;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellRecord that = (SellRecord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(flightTicket, that.flightTicket) &&
                Objects.equals(price, that.price) &&
                Objects.equals(increasePercentage, that.increasePercentage) &&
                Objects.equals(cardNumber, that.cardNumber) &&
                Objects.equals(cardDate, that.cardDate) &&
                Objects.equals(cvvCode, that.cvvCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightTicket, price, increasePercentage, cardNumber, cardDate, cvvCode);
    }
}
