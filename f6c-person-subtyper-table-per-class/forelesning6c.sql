--
-- Eksempel f6c forelesning fredag 27. mars 2026.
--
-- Alternativ c: Subtyping med tabell kun for subtypene,
-- dvs. @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

DROP SCHEMA IF EXISTS f6c_subtyper_per_class CASCADE;
CREATE SCHEMA f6c_subtyper_per_class;
SET search_path TO f6c_subtyper_per_class;

CREATE TABLE person
(
  fnr        CHAR(11), -- Kan også bruke UNSIGNED BIGINT(11). 
                       -- INTEGER er for liten for 11 sifre.
  fornavn    VARCHAR(30),
  etternavn  VARCHAR(30),
  CONSTRAINT person_pk PRIMARY KEY (fnr)
);

CREATE TABLE ansatt
(
  fnr        CHAR(11), -- Kan også bruke UNSIGNED BIGINT(11). 
                       -- INTEGER er for liten for 11 sifre.
  fornavn    VARCHAR(30),
  etternavn  VARCHAR(30),
  stilling   VARCHAR(30),
  CONSTRAINT ansatt_pk PRIMARY KEY (fnr)
);

CREATE TABLE student
(
  fnr        CHAR(11), -- Kan også bruke UNSIGNED BIGINT(11). 
                       -- INTEGER er for liten for 11 sifre.
  fornavn    VARCHAR(30),
  etternavn  VARCHAR(30),
  studium    VARCHAR(30),
  CONSTRAINT student_pk PRIMARY KEY (fnr)
);

INSERT INTO 
    person(fnr, fornavn, etternavn)
VALUES
    ('34567890123', 'Pers', 'Perssen');

INSERT INTO 
    ansatt(fnr, fornavn, etternavn, stilling)
VALUES
    ('12345678901', 'Arne', 'Arnesen', 'Lærer');
    
INSERT INTO 
    student(fnr, fornavn, etternavn, studium)
VALUES
    ('23456789012', 'Stig', 'Stigsen', 'Dataingeniør');
    

