package com.LBA.tools.misc;
public class BankEntry {
	String bankCode;
	String wording;
	String achCode;
	String gipCode;

	public BankEntry(String bankCode, String wording, String achCode,String gipCode) {
		super();
		this.bankCode = bankCode;
		this.wording = wording;
		this.achCode = achCode;
		this.gipCode = gipCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getWording() {
		return wording;
	}
	public void setWording(String wording) {
		this.wording = wording;
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
		return wording;
	}
}
