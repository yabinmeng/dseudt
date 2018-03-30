CREATE KEYSPACE IF NOT EXISTS testks WITH replication =
    {'class': 'SimpleStrategy', 'replication_factor': '2'} AND durable_writes = true;

CREATE TYPE IF NOT EXISTS testks.pname  (
  fname text,
  lname text
);

CREATE TABLE IF NOT EXISTS testks.testbl (
  id int PRIMARY KEY,
  names list < frozen <pname > >
);