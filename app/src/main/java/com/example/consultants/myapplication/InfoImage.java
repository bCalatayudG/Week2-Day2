package com.example.consultants.myapplication;

import java.util.Arrays;

public class InfoImage {

    private byte[] blob;
    private String desc;
    private String id;

    public InfoImage(byte[] blob, String desc, String id) {
        this.blob = blob;
        this.desc = desc;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "InfoImage{" +
                " desc='" + desc + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }







}
