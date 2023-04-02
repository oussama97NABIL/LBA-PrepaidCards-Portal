package com.LBA.tools.misc;
public class MomoProviderEntry {
	String providerCode;
	String wording;
	String routingCode;

	public MomoProviderEntry(String providerCode, String wording, String routingCode) {
		super();
		this.providerCode = providerCode;
		this.wording = wording;
		this.routingCode = routingCode;
	}
	public String getProviderCode() {
		return providerCode;
	}
	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}
	public String getWording() {
		return wording;
	}
	public void setWording(String wording) {
		this.wording = wording;
	}
	public String getRoutingCode() {
		return routingCode;
	}
	public void setRoutingCode(String routingCode) {
		this.routingCode = routingCode;
	}
	public String toString(){
		return wording;
	}

}
