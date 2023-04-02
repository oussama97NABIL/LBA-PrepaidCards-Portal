package com.LBA.tools.misc;

public class AccountHabEntry {
	String accountNumber;
	String operationId;
	public AccountHabEntry(String accountNumber, String operationId) {
		super();
		this.accountNumber = accountNumber;
		this.operationId = operationId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	
	
	

}
