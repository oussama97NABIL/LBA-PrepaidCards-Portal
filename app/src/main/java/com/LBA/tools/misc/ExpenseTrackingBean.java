package com.LBA.tools.misc;

import java.io.Serializable;

public class ExpenseTrackingBean implements Serializable {

    String Account_debit;
    Double amount;
    int month;
    String transaction_purpose;
    String transaction_type;

    public ExpenseTrackingBean(String account_debit, Double amount, int month, String transaction_purpose, String transaction_type) {
        Account_debit = account_debit;
        this.amount = amount;
        this.month = month;
        this.transaction_purpose = transaction_purpose;
        this.transaction_type = transaction_type;
    }

    public String getAccount_debit() {
        return Account_debit;
    }

    public Double getAmount() {
        return amount;
    }

    public int getMonth() {
        return month;
    }

    public String getTransaction_purpose() {
        return transaction_purpose;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setAccount_debit(String account_debit) {
        Account_debit = account_debit;
    }

    @Override
    public String toString() {
        return "ExpenseTrackingBean{" +
                "Account_debit='" + Account_debit + '\'' +
                ", amount=" + amount +
                ", month=" + month +
                ", transaction_purpose='" + transaction_purpose + '\'' +
                ", transaction_type='" + transaction_type + '\'' +
                '}';
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setTransaction_purpose(String transaction_purpose) {
        this.transaction_purpose = transaction_purpose;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

}
