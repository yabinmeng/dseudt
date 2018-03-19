package com.ymeng;


import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.ymeng.utils.DBConstant;
import com.ymeng.utils.objmap.PNameMapper;
import com.ymeng.utils.objmap.TestTblRcord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.datastax.driver.mapping.Mapper.Option.saveNullFields;

public class UDTObjMapTest
{
    public static void main( String[] args )
    {

        Cluster cluster = null;
        try {

            cluster = Cluster.builder()
                    .addContactPoint(DBConstant.DSE_NODE_IP_LIST)
                    .build();

            Session session = cluster.connect();

            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();

            ProtocolVersion myCurrentVersion = cluster.getConfiguration()
                    .getProtocolOptions()
                    .getProtocolVersion();

            TableMetadata tableMetadata =
                cluster.getMetadata().getKeyspace(DBConstant.KEYSPACE_NAME).getTable(DBConstant.TABLE_NAME);

            MappingManager manager = new MappingManager(session);
            Mapper<TestTblRcord> mapper = manager.mapper(TestTblRcord.class);

            List<PNameMapper> names2 = new ArrayList<PNameMapper>();
            names2.add(new PNameMapper("john", "smith"));
            names2.add(new PNameMapper("adam", "borton"));

            TestTblRcord testTblRcord1 = new TestTblRcord(3, names2);
            TestTblRcord testTblRcord2 = new TestTblRcord(4);
            TestTblRcord testTblRcord3 = new TestTblRcord(5);

            mapper.save(testTblRcord1);
            mapper.save(testTblRcord2);
            // Avoid tombstone with saveNullFields(false)
            mapper.save(testTblRcord3, saveNullFields(false));

            System.out.println("\n\n---------------");
            System.out.println("Cassandra version: " + row.getString("release_version") +
                    "; Portocol version: " + myCurrentVersion);

        } finally {
            if (cluster != null) cluster.close();
        }
    }
}