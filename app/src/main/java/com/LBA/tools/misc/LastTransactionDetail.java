package com.LBA.tools.misc;

public class LastTransactionDetail {
    private String TransactionType;
    private String Amount;
    private String Currency;
    private String Date;
    private String ReferenceNumber;
    private String Location;

    public LastTransactionDetail(String transactionType, String amount, String currency, String date, String referenceNumber, String location) {
        TransactionType = transactionType;
        Amount = amount;
        Currency = currency;
        Date = date;
        ReferenceNumber = referenceNumber;
        Location = location;
    }

    public LastTransactionDetail(String transactionType) {
        TransactionType = transactionType;
    }


    public LastTransactionDetail() {
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getReferenceNumber() {
        return ReferenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        ReferenceNumber = referenceNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    @Override
    public String toString() {
        return "LastTransactionDetail{" +
                "TransactionType='" + TransactionType + '\'' +
                ", Amount='" + Amount + '\'' +
                ", Currency='" + Currency + '\'' +
                ", Date='" + Date + '\'' +
                ", ReferenceNumber='" + ReferenceNumber + '\'' +
                ", Location='" + Location + '\'' +
                '}';
    }
}
