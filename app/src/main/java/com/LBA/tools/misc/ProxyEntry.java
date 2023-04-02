package com.LBA.tools.misc;

public class ProxyEntry {

    String proxy_ID;
    String account_number;

    public ProxyEntry( String account_number, String proxy_ID) {
        this.proxy_ID = proxy_ID;
        this.account_number = account_number;
    }

    public ProxyEntry() {
    }

    public String getProxy_ID() {
        return proxy_ID;
    }

    public void setProxy_ID(String proxy_ID) {
        this.proxy_ID = proxy_ID;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    @Override
    public String toString() {
        return "ProxyEntry{" +
                "proxy_ID='" + proxy_ID + '\'' +
                ", account_number='" + account_number + '\'' +
                '}';
    }
}