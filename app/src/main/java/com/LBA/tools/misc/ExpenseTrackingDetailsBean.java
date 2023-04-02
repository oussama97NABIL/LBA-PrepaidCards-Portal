package com.LBA.tools.misc;

public class ExpenseTrackingDetailsBean {
    String account_credit;
    String account_debit;
    Double amount;
    String operation_date;
    String transaction_type;
    String narration;

    public ExpenseTrackingDetailsBean(String account_credit, String account_debit, Double amount, String operation_date, String transaction_type,String narration) {
        this.account_credit = account_credit;
        this.account_debit = account_debit;
        this.amount = amount;
        this.operation_date = operation_date;
        this.transaction_type = transaction_type;
        this.narration = narration;
    }

    @Override
    public String toString() {
        return "ExpenseTrackingDetailsBean{" +
                "account_credit='" + account_credit + '\'' +
                ", account_debit='" + account_debit + '\'' +
                ", amount=" + amount +
                ", operation_date='" + operation_date + '\'' +
                ", transaction_type='" + transaction_type + '\'' +
                ", narration='" + narration + '\'' +
                '}';
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getAccount_credit() {
        return account_credit;
    }

    public void setAccount_credit(String account_credit) {
        this.account_credit = account_credit;
    }

    public String getAccount_debit() {
        return account_debit;
    }

    public void setAccount_debit(String account_debit) {
        this.account_debit = account_debit;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOperation_date() {
        return operation_date;
    }

    public void setOperation_date(String operation_date) {
        this.operation_date = operation_date;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }


}
