package com.ymeng.utils.objmap;

import com.datastax.driver.mapping.annotations.FrozenValue;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.List;

@Table(keyspace = "testks", name = "testbl",
        readConsistency = "QUORUM",
        writeConsistency = "QUORUM")
public class TestTblRcord {
    @PartitionKey
    private int id;

    @FrozenValue
    private List<PNameMapper> names;

    public TestTblRcord(int id) {
        this.id = id;
    }

    public TestTblRcord(int id, List<PNameMapper> names) {
        this.id = id;
        this.names = names;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PNameMapper> getNames() {
        return this.names;
    }

    public void setNames(List<PNameMapper> names) {
        this.names = names;
    }
}
