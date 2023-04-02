package com.LBA.tools.misc;

public class BeneficiaryEntry {

    private String userCode;
    private String beneficiary_name;
    private String beneficiary_account;
    private String beneficiary_inst_code;
    private String beneficiary_operation;
    private String beneficiary_ach_code;
    private String beneficiary_mobile;
    private String amount;
    private String narration;
    private String purpose;
    private String user_data;


    public BeneficiaryEntry() {

    }

    public BeneficiaryEntry(String userCode, String beneficiary_name, String beneficiary_account, String beneficiary_inst_code, String beneficiary_operation, String beneficiary_ach_code,
                            String beneficiary_mobile, String narration, String amount, String purpose, String user_data) {
        this.userCode = userCode;
        this.beneficiary_name = beneficiary_name;
        this.beneficiary_account = beneficiary_account;
        this.beneficiary_inst_code = beneficiary_inst_code;
        this.beneficiary_operation = beneficiary_operation;
        this.beneficiary_ach_code = beneficiary_ach_code;
        this.beneficiary_mobile = beneficiary_mobile;
        this.narration=narration;
        this.amount = amount;
        this.purpose=purpose;
        this.user_data=user_data;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getBeneficiary_name() {
        return beneficiary_name;
    }

    public void setBeneficiary_name(String beneficiary_name) {
        this.beneficiary_name = beneficiary_name;
    }

    public String getBeneficiary_account() {
        return beneficiary_account;
    }

    public void setBeneficiary_account(String beneficiary_account) {
        this.beneficiary_account = beneficiary_account;
    }

    public String getBeneficiary_inst_code() {
        return beneficiary_inst_code;
    }

    public void setBeneficiary_inst_code(String beneficiary_inst_code) {
        this.beneficiary_inst_code = beneficiary_inst_code;
    }

    public String getBeneficiary_operation() {
        return beneficiary_operation;
    }

    public void setBeneficiary_operation(String beneficiary_operation) {
        this.beneficiary_operation = beneficiary_operation;
    }

    public String getBeneficiary_ach_code() {
        return beneficiary_ach_code;
    }

    public void setBeneficiary_ach_code(String beneficiary_ach_code) {
        this.beneficiary_ach_code = beneficiary_ach_code;
    }

    public String getBeneficiary_mobile() {
        return beneficiary_mobile;
    }

    public void setBeneficiary_mobile(String beneficiary_mobile) {
        this.beneficiary_mobile = beneficiary_mobile;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }
    public String getAmount(){
        return amount;
    }

    public void setNarration(String narration){
        this.narration = narration;
    }

    public String getNarration(){
        return narration;
    }

    public String getPurpose(){
        return purpose;
    }
    public void setPurpose(String purpose){
        this.purpose = purpose;
    }

    public String getUser_data(){
        return user_data;
    }
    public void setUser_data(String user_data){
        this.user_data = user_data;
    }
}
