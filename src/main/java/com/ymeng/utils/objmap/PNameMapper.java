package com.ymeng.utils.objmap;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

/**
 * Java Class that represents an UDT with object mapping
 */
@UDT(keyspace = "testks", name = "pname")
public class PNameMapper {
    @Field(name = "fname")
    private String fname;

    @Field(name = "lname")
    private String lname;

    public PNameMapper(String fname, String lname) {
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
