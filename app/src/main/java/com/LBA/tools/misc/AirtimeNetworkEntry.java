package com.LBA.tools.misc;

/**
 * Created by amine.wahbi on 3/11/2015.
 */
public class AirtimeNetworkEntry {

    String name;
    String id;

    public AirtimeNetworkEntry(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString(){
        return name;
    }

}
