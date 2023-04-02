package com.LBA.tools.misc;

public class BranchGeoEntry {

    //private String atmId;
    //private String atmName;
    //private String atmAddress;
    //private String zone;
    //private String branch;
    //private String location;
    //private String geoLocation;
    //private String latitude;
    //private String lobgitude;
    //private String webAddress;

    private String branchCode;
    private String branchName;
    private String mnemonic;
    private String address;
    private String city;
    private String region;
    private String GPS;
    private String saturdayOpen;
    private String latitude;
    private String longitude;


    /*public BranchGeoEntry() {
        super();
    }*/

    public BranchGeoEntry(String branchCode, String branchName, String mnemonic, String address, String city, String region,
                          String GPS, String latitude, String longitude, String saturdayOpen) {
        super();
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.mnemonic = mnemonic;
        this.address = address;
        this.city = city;
        this.region = region;
        this.GPS = GPS;
        this.latitude = latitude;
        this.longitude = longitude;
        this.saturdayOpen = saturdayOpen;
    }
    public String getbranchCode() {
        return branchCode;
    }
    public void setbranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
    public String getbranchName() {
        return branchName;
    }
    public void setbranchName(String branchName) {
        this.branchName = branchName;
    }
    public String getmnemonic() {
        return mnemonic;
    }
    public void setmnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
    public String getaddress() {
        return address;
    }
    public void setaddress(String address) {
        this.address = address;
    }
    public String getcity() {
        return city;
    }
    public void setcity(String city) {
        this.city = city;
    }
    public String getregion() {
        return region;
    }
    public void setregion(String region) {
        this.region = region;
    }
    public String getGPS() {
        return GPS;
    }
    public void setGPS(String GPS) {
        this.GPS = GPS;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getlongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getsaturdayOpen() {
        return saturdayOpen;
    }
    public void setsaturdayOpen(String saturdayOpen) {
        this.saturdayOpen = saturdayOpen;
    }


}
