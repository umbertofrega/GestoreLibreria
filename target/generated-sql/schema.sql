-- -----------------------------------------------------------------------
-- postgresql SQL script for schema GestoreLibrera
-- -----------------------------------------------------------------------


ALTER TABLE libro
    DROP CONSTRAINT IF EXISTS libro_FK_1;


ALTER TABLE libro
    DROP CONSTRAINT IF EXISTS libro_FK_2;


DROP TABLE autore CASCADE;
DROP SEQUENCE autore_SEQ;
DROP TABLE genere CASCADE;
DROP TABLE libro CASCADE;

-- -----------------------------------------------------------------------
-- autore
-- -----------------------------------------------------------------------
CREATE TABLE autore
(
    ID INTEGER(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    PRIMARY KEY(ID)
);


CREATE SEQUENCE autore_SEQ INCREMENT BY 1 START WITH 1 NO MAXVALUE NO CYCLE;

-- -----------------------------------------------------------------------
-- genere
-- -----------------------------------------------------------------------
CREATE TABLE genere
(
    nome VARCHAR(255) NOT NULL,
    PRIMARY KEY(nome)
);



-- -----------------------------------------------------------------------
-- libro
-- -----------------------------------------------------------------------
CREATE TABLE libro
(
    codice INTEGER(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    autore INTEGER(255) NOT NULL,
    genere VARCHAR(255) NOT NULL,
    valutazione INTEGER(255),
    stato VARCHAR(255) NOT NULL,
    PRIMARY KEY(codice)
);


ALTER TABLE libro
    ADD CONSTRAINT libro_FK_1
    FOREIGN KEY (autore)
    REFERENCES autore (ID);

ALTER TABLE libro
    ADD CONSTRAINT libro_FK_2
    FOREIGN KEY (genere)
    REFERENCES genere (nome);


