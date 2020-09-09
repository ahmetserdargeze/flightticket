package com.ahmetgeze.flightticket.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "sell_record")
public class SellRecord extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "sellRecord")
    @JsonIgnoreProperties( allowSetters=true,value = "sellRecord")
    private List<FlightTicket> flightTickets = new ArrayList<>();

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Column(name = "card_place_holder", nullable = false)
    private String cardPlaceHolder;

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

    public List<FlightTicket> getFlightTickets() {
        return flightTickets;
    }

    public void setFlightTickets(List<FlightTicket> flightTickets) {
        this.flightTickets = flightTickets;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCardPlaceHolder() {
        return cardPlaceHolder;
    }

    public void setCardPlaceHolder(String cardPlaceHolder) {
        this.cardPlaceHolder = cardPlaceHolder;
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

    public void addItemToFlightTickets(FlightTicket flightTicket) {
        this.flightTickets.add(flightTicket);
        this.totalPrice += flightTicket.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SellRecord that = (SellRecord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(totalPrice, that.totalPrice) &&
                Objects.equals(cardPlaceHolder, that.cardPlaceHolder) &&
                Objects.equals(cardNumber, that.cardNumber) &&
                Objects.equals(cardDate, that.cardDate) &&
                Objects.equals(cvvCode, that.cvvCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice, cardPlaceHolder, cardNumber, cardDate, cvvCode);
    }

    @Override
    public String toString() {
        return "SellRecord{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", cardPlaceHolder='" + cardPlaceHolder + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardDate='" + cardDate + '\'' +
                ", cvvCode='" + cvvCode + '\'' +
                '}';
    }
}
