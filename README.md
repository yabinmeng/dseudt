## Description
This is a sample code of exploring different methods to insert CQL UDT (User Defined Type) into Cassandra through DataStax Cassandra/Java Driver

- UserType (UDTUserTypeTest.java): UserType + UDTValue (No Domain Class)
- Custom Codec (UDTObjMapTest.java): UserType + CustomCodec + Domain Class
- Object Mapper (UDTCodecTest.java): Domain Class with Object Mapping
