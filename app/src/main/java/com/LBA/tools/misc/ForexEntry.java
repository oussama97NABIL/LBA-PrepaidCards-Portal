package com.LBA.tools.misc;

public class ForexEntry {
	String currencyFrom;
	String currencyTo;
	Double buyingRate;
	Double sellingRate;
	public ForexEntry(String currencyFrom, String currencyTo,
					  Double buyingRate, Double sellingRate) {
		super();
		this.currencyFrom = currencyFrom;
		this.currencyTo = currencyTo;
		this.buyingRate = buyingRate;
		this.sellingRate = sellingRate;
	}
	public String getCurrencyFrom() {
		return currencyFrom;
	}
	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}
	public String getCurrencyTo() {
		return currencyTo;
	}
	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}
	public Double getBuyingRate() {
		return buyingRate;
	}
	public void setBuyingRate(Double buyingRate) {
		this.buyingRate = buyingRate;
	}
	public Double getSellingRate() {
		return sellingRate;
	}
	public void setSellingRate(Double sellingRate) {
		this.sellingRate = sellingRate;
	}
	
	
	

}
