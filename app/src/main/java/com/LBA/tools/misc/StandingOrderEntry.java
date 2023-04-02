package com.LBA.tools.misc;

public class StandingOrderEntry  implements java.io.Serializable {

    private String standingOrderId;
    private String debitAccount;
    private String creditAccount;
    private Double amount;
    private String expiryDate;
    private Character periodType='M';
    private String dayOfWeek="Mo";
    private int dayOfMonth;
    private String dateOfYear;
    private String operationDate;


    public StandingOrderEntry(){};


    /*public StandingOrderEntry(String StandingOrderId, String debitAccount,
                             String creditAccount, Double amount, Date expiryDate,
                             Character periodType, String dayOfWeek, int dayOfMonth,
                             Date dateOfYear, Date operationDate) {
        this.StandingOrderId = StandingOrderId;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
        this.expiryDate = (expiryDate!=null?new SimpleDateFormat("dd/MM/yyyy").format(expiryDate):null);
        this.periodType = periodType;
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.dateOfYear = (dateOfYear!=null?new SimpleDateFormat("dd/MM/yyyy").format(dateOfYear): null);
        this.operationDate = (operationDate!=null?new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(operationDate):null);
    }*/

    public StandingOrderEntry(String standingOrderId, String debitAccount,
                             String creditAccount, Double amount, String expiryDate,
                             Character periodType, String dayOfWeek, int dayOfMonth,
                             String dateOfYear, String operationDate) {
        this.standingOrderId = standingOrderId;
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
        this.expiryDate = expiryDate;
        this.periodType = periodType;
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.dateOfYear = dateOfYear;
        this.operationDate = operationDate;
    }

    public String getStandingOrderId() {
        return this.standingOrderId;
    }

    public void setStandingOrderId(String standingOrderId) {
        this.standingOrderId = standingOrderId;
    }

    public String getDebitAccount() {
        return this.debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getCreditAccount() {
        return this.creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Character getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Character periodType) {
        this.periodType = periodType;
    }

    public String getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getDayOfMonth() {
        return this.dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getDateOfYear() {
        return this.dateOfYear;
    }

    public void setDateOfYear(String dateOfYear) {
        this.dateOfYear = dateOfYear;
    }

    public String getOperationDate() {
        return this.operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

}



/////////////////////////////////
