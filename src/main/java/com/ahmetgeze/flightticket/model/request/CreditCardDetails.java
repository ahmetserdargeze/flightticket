package com.ahmetgeze.flightticket.model.request;

import com.ahmetgeze.flightticket.utils.UtilsFunc;

public class CreditCardDetails {
    private String cardNumber;
    private String cardDate;
    private String cvvCode;
    private String cardPleaceholderName;

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

    public String getCardPleaceholderName() {
        return cardPleaceholderName;
    }

    public void setCardPleaceholderName(String cardPleaceholderName) {
        this.cardPleaceholderName = cardPleaceholderName;
    }

    public boolean validateInput() {
        if (UtilsFunc.isNotNull(cardNumber, cardDate, cvvCode)) {
            if (cvvCode.length() != 3) {
                return false;
            } else if (cardDate.length() != 5) {
                return false;
            } else if (cvvCode.length() != 3) {
                return false;
            }
            return true;
        }
        return false;
    }
}
