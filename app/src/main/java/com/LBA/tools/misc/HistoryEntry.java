package com.LBA.tools.misc;

public class HistoryEntry {

    String op_date ;
    String  service ;
    String op_status ;
    Double amount ;
    String operation_type ;
    String transaction_id ;

    public HistoryEntry(String op_date, String service, String op_status, Double amount, String operation_type , String transaction_id) {
        this.op_date = op_date;
        this.service = service;
        this.op_status = op_status;
        this.amount = amount;
        this.operation_type = operation_type;
        this.transaction_id =  transaction_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOp_date() {
        return op_date;
    }

    public void setOp_date(String op_date) {
        this.op_date = op_date;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOp_status() {
        return op_status;
    }

    public void setOp_status(String op_status) {
        this.op_status = op_status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }


    @Override
    public String toString() {
        return "HistoryEntry{" +
                "op_date='" + op_date + '\'' +
                ", service='" + service + '\'' +
                ", op_status='" + op_status + '\'' +
                ", amount=" + amount +
                ", operation_type='" + operation_type + '\'' +
                ", transaction_id='" + transaction_id + '\'' +
                '}';
    }
}

