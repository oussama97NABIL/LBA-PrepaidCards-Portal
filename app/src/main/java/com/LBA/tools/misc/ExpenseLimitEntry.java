package com.LBA.tools.misc;

public class ExpenseLimitEntry {

    String OPERATION_TYPE;
    String LIMITID;
    String ACCOUNT_NUMBER;
    String ACTIVATION_DATE;
    String DAILY_LIMIT;
    String LIMIT_TYPE;

    public ExpenseLimitEntry(String OPERATION_TYPE, String LIMITID, String ACCOUNT_NUMBER, String ACTIVATION_DATE, String DAILY_LIMIT , String LIMIT_TYPE) {
        this.OPERATION_TYPE = OPERATION_TYPE;
        this.LIMITID = LIMITID;
        this.LIMIT_TYPE = LIMIT_TYPE;
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
        this.ACTIVATION_DATE = ACTIVATION_DATE;
        this.DAILY_LIMIT = DAILY_LIMIT;
    }

    public ExpenseLimitEntry(String OPERATION_TYPE, String LIMITID, String ACCOUNT_NUMBER, String DAILY_LIMIT,String LIMIT_TYPE) {
        this.OPERATION_TYPE = OPERATION_TYPE;
        this.LIMITID = LIMITID;
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
        this.DAILY_LIMIT = DAILY_LIMIT;
        this.LIMIT_TYPE = LIMIT_TYPE;
    }


    public String getOPERATION_TYPE() {
        return OPERATION_TYPE;
    }

    public void setOPERATION_TYPE(String OPERATION_TYPE) {
        this.OPERATION_TYPE = OPERATION_TYPE;
    }

    public String getLIMITID() {
        return LIMITID;
    }

    public void setLIMITID(String LIMITID) {
        this.LIMITID = LIMITID;
    }

    public String getACCOUNT_NUMBER() {
        return ACCOUNT_NUMBER;
    }

    public void setACCOUNT_NUMBER(String ACCOUNT_NUMBER) {
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
    }

    public String getACTIVATION_DATE() {
        return ACTIVATION_DATE;
    }

    public void setACTIVATION_DATE(String ACTIVATION_DATE) {
        this.ACTIVATION_DATE = ACTIVATION_DATE;
    }

    public String getLIMIT_TYPE() {
        return LIMIT_TYPE;
    }

    public void setLIMIT_TYPE(String LIMIT_TYPE) {
        this.LIMIT_TYPE = LIMIT_TYPE;
    }

    public String getDAILY_LIMIT() {
        return DAILY_LIMIT;
    }

    public void setDAILY_LIMIT(String DAILY_LIMIT) {
        this.DAILY_LIMIT = DAILY_LIMIT;
    }

    @Override
    public String toString() {
        return "ExpenseLimitEntry{" +
                "OPERATION_TYPE='" + OPERATION_TYPE + '\'' +
                ", LIMITID='" + LIMITID + '\'' +
                ", ACCOUNT_NUMBER='" + ACCOUNT_NUMBER + '\'' +
                ", ACTIVATION_DATE='" + ACTIVATION_DATE + '\'' +
                ", DAILY_LIMIT='" + DAILY_LIMIT + '\'' +
                ", LIMIT_TYPE='" + LIMIT_TYPE + '\'' +
                '}';
    }
}
