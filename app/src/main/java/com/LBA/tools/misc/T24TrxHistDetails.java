package com.LBA.tools.misc;

public class T24TrxHistDetails {
	
	String bookingDate;
	String reference;
	String description;
	String description2;
	String description3;
	String valueDate;
	String debit;
	String credit;
	String closingBalance;
	
	public T24TrxHistDetails() {
		super();
	}

	public T24TrxHistDetails(String bookingDate, String reference,
			String description, String description2, String description3,
			String valueDate, String debit, String credit, String closingBalance) {
		super();
		this.bookingDate = bookingDate;
		this.reference = reference;
		this.description = description;
		this.description2 = description2;
		this.description3 = description3;
		this.valueDate = valueDate;
		this.debit = debit;
		this.credit = credit;
		this.closingBalance = closingBalance;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getDescription3() {
		return description3;
	}

	public void setDescription3(String description3) {
		this.description3 = description3;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(String closingBalance) {
		this.closingBalance = closingBalance;
	}

	@Override
	public String toString() {
		return "T24TrxHistDetails [bookingDate=" + bookingDate + ", reference="
				+ reference + ", description=" + description
				+ ", description2=" + description2 + ", description3="
				+ description3 + ", valueDate=" + valueDate + ", debit="
				+ debit + ", credit=" + credit + ", closingBalance="
				+ closingBalance + "]";
	}
	
	
	
}
