package com.ymeng;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.ymeng.utils.DBConstant;
import com.ymeng.utils.codec.PName;
import com.ymeng.utils.codec.PNameCodec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UDTCodecTest
{
    public static void main( String[] args )
    {
        Cluster cluster = null;
        try {
            CodecRegistry codecRegistry = new CodecRegistry();

            cluster = Cluster.builder()
                    .addContactPoint(DBConstant.DSE_NODE_IP_LIST)
                    .withCodecRegistry(codecRegistry)
                    .build();

            Session session = cluster.connect();

            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();

            ProtocolVersion myCurrentVersion = cluster.getConfiguration()
                    .getProtocolOptions()
                    .getProtocolVersion();

            UserType pNameType =
                cluster.getMetadata().getKeyspace(DBConstant.KEYSPACE_NAME).getUserType(DBConstant.UDT_TYPE_NAME);

            TypeCodec<UDTValue> pNameTypeCodec = codecRegistry.codecFor(pNameType);
            PNameCodec pNameCodec = new PNameCodec(pNameTypeCodec, PName.class);
            codecRegistry.register(pNameCodec);

            List<PName> names = new ArrayList<PName>();
            names.add(new PName("Sam", "Winchester"));

            TableMetadata tableMetadata =
                cluster.getMetadata().getKeyspace(DBConstant.KEYSPACE_NAME).getTable(DBConstant.TABLE_NAME);

            BuiltStatement insBltQuery1 = QueryBuilder.insertInto(tableMetadata)
                    .value("id", 100)
                    .value("names", names);
            session.execute(insBltQuery1);

            BuiltStatement insBltQuery2 = QueryBuilder.insertInto(tableMetadata)
                    .value("id", 101);
            session.execute(insBltQuery2);

            System.out.println("\n\n---------------");
            System.out.println("Cassandra version: " + row.getString("release_version") +
                    "; Portocol version: " + myCurrentVersion);

        } finally {
            if (cluster != null) cluster.close();
        }
    }
}