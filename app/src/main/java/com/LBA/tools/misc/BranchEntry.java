package com.LBA.tools.misc;

public class BranchEntry {
	String branchCode;
	String bankCode;
	String branchName;
	String achBankCode;
	String gipBankCode;
	String achCode;
	String gipCode;
	public BranchEntry(String branchCode, String bankCode, String branchName,
			String achBankCode, String gipBankCode, String achCode,
			String gipCode) {
		super();
		this.branchCode = branchCode;
		this.bankCode = bankCode;
		this.branchName = branchName;
		this.achBankCode = achBankCode;
		this.gipBankCode = gipBankCode;
		this.achCode = achCode;
		this.gipCode = gipCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getAchBankCode() {
		return achBankCode;
	}
	public void setAchBankCode(String achBankCode) {
		this.achBankCode = achBankCode;
	}
	public String getGipBankCode() {
		return gipBankCode;
	}
	public void setGipBankCode(String gipBankCode) {
		this.gipBankCode = gipBankCode;
	}
	public String getAchCode() {
		return achCode;
	}
	public void setAchCode(String achCode) {
		this.achCode = achCode;
	}
	public String getGipCode() {
		return gipCode;
	}
	public void setGipCode(String gipCode) {
		this.gipCode = gipCode;
	}

	public String toString(){
		return branchName;
	}
	

}
