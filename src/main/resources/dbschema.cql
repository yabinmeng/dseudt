CREATE KEYSPACE testks WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '2'} AND durable_writes = true;

CREATE TYPE testks.pname (
  fname text,
  lname text
);

CREATE TABLE testks.testbl (
  id int PRIMARY KEY,
  names list < frozen <pname > >
);