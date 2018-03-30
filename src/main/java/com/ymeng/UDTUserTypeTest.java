package com.ymeng;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.BuiltStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.ymeng.utils.DBConstant;

import java.util.*;

public class UDTUserTypeTest
{
    public static void main( String[] args )
    {
        Cluster cluster = null;
        try {
            CodecRegistry codecRegistry = new CodecRegistry();

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

            UserType pNameType =
                cluster.getMetadata().getKeyspace(DBConstant.KEYSPACE_NAME).getUserType(DBConstant.UDT_TYPE_NAME);

            UDTValue udtValue = pNameType.newValue();
            udtValue.setString("fname", "Jon");
            udtValue.setString("lname", "snow");

            List<UDTValue> udtValues = new ArrayList<UDTValue>();
            udtValues.add(udtValue);

            BuiltStatement insBltQuery1 = QueryBuilder.insertInto(tableMetadata)
                .value("id", 301)
                .value("names", udtValues);
            session.execute(insBltQuery1);

            // Read all records from the table
            String[] colNames = new String[]{"id", "names"};
            BuiltStatement readQuery = QueryBuilder.select(colNames).from(tableMetadata);

            ResultSet rs1 = session.execute(readQuery);
            Iterator<Row> rs1Rows = rs1.all().iterator();

            while (rs1Rows.hasNext()) {
                Row rs1Row = rs1Rows.next();
                System.out.println(rs1Row.toString());
            }

            System.out.println("\n\n---------------");
            System.out.println("Cassandra version: " + row.getString("release_version") +
                    "; Portocol version: " + myCurrentVersion);

        } finally {
            if (cluster != null) cluster.close();
        }
    }
}