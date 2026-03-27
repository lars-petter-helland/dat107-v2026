-- SQL for todoliste-eksemplet gjennomgått i timen fredag 27. mars 2026

DROP SCHEMA IF EXISTS f6_todoliste CASCADE;
CREATE SCHEMA f6_todoliste;
SET search_path TO f6_todoliste;
    
CREATE TABLE todoliste
(
    id		SERIAL PRIMARY KEY,
    navn	VARCHAR
);

CREATE TABLE todo
(
    id      SERIAL PRIMARY KEY,
    tekst   VARCHAR,
    listeid INTEGER,
    CONSTRAINT listeFK FOREIGN KEY (listeid) REFERENCES todoliste(id)
);

-- Et par spørringer for å vise hva vi har
SELECT * FROM todoliste ORDER BY id ASC;
SELECT * FROM todo ORDER BY id ASC;
