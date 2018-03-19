package com.ymeng.utils.codec;

/**
 * Java Class that represents a UDT
 */
public class PName {
    private String fname;
    private String lname;

    public PName(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    public String getFName() {
        return this.fname;
    }
    public void setFName(String fname) {
        this.fname = fname;
    }

    public String getLName() {
        return this.lname;
    }
    public void setLName(String lname) {
        this.lname = lname;
    }
}
