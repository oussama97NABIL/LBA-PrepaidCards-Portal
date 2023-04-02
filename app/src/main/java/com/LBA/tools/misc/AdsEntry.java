package com.LBA.tools.misc;

import android.graphics.Bitmap;

public class AdsEntry {

    private String id;
    private Bitmap image;
    //  28062022
    private String target;

    //  28062022
    public AdsEntry(String id, Bitmap image , String target) {
        this.id = id;
        this.target = target;
        this.image = image;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public AdsEntry() {

    }

    public AdsEntry(String id, Bitmap image) {
        this.id = id;
        this.image = image;
    }
}