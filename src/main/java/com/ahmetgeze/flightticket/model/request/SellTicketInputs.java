package com.ahmetgeze.flightticket.model.request;

import com.ahmetgeze.flightticket.utils.UtilsFunc;

import java.util.UUID;

public class SellTicketInputs {
    CreditCardDetails paymentDetails;
    UUID flightRecordId;
    int seatCount;

    public CreditCardDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(CreditCardDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public UUID getFlightRecordId() {
        return flightRecordId;
    }

    public void setFlightRecordId(UUID flightRecordId) {
        this.flightRecordId = flightRecordId;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public boolean validateInput() {
        boolean result = false;
        if (this.paymentDetails.validateInput() && UtilsFunc.isNotNull(flightRecordId, seatCount)) {
            result = true;
        }
        return result;
    }
}
