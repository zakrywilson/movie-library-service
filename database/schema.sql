/*
 * Name:    schema.sql
 *
 * Date:    10-09-2012
 *
 * Version: 1
 *
 * Purpose: This script will create all tables for the Movie database.
 *
 * Author: Jim Wilson, Zach Wilson
 *
 * -------------------------------------------------------------------------------------------------
 *
 * Notes on data types:
 *     unsigned tinyint   = 0 to 255
 *     unsigned smallint  = 0 to 65535
 *     unsigned mediumint = 0 to 16777215
 *
 * Notes on file:
 *     2018-03-11: The present purpose of this document is more for reference, as the schema is
 *                 being generated by JPA at runtime. That configuration may change.
 */

DROP DATABASE MDB;     -- Movie Data Base
CREATE DATABASE MDB;

CREATE TABLE MDB.RATING
(
    ID                     INTEGER          NOT NULL,
    NAME                   VARCHAR(100)     NOT NULL UNIQUE,
    DESCRIPTION            VARCHAR(200)     ,
  PRIMARY KEY(ID)
);

CREATE TABLE MDB.GENRE
(
    ID                     INTEGER          NOT NULL,
    NAME                   VARCHAR(100)     NOT NULL UNIQUE,
    DESCRIPTION            VARCHAR(200)     ,
  PRIMARY KEY(ID)
);

CREATE TABLE MDB.LANGUAGE
(
    ID                     INTEGER          NOT NULL,
    NAME                   VARCHAR(100)     NOT NULL UNIQUE,
    DESCRIPTION            VARCHAR(200)     ,
  PRIMARY KEY(ID)
);

CREATE TABLE MDB.TV_SHOW
(
    ID                     INTEGER          NOT NULL AUTO_INCREMENT,
    TITLE                  VARCHAR(100)     NOT NULL,
    DATE_AIRED             DATE             NOT NULL,
    NETWORK                VARCHAR(100)     NOT NULL,
    RATING_ID              INTEGER          NOT NULL,
    GENRE_ID               INTEGER          NOT NULL,
    LANGUAGE_ID            INTEGER          NOT NULL,
    PLOT_SUMMARY           VARCHAR(4096)    NOT NULL,
    IS_SERIES              BOOLEAN          NOT NULL,
 PRIMARY KEY(ID)
);
ALTER TABLE MDB.TV_SHOW ADD CONSTRAINT TVSHOW_RATINGID_FK FOREIGN KEY(RATING_ID) REFERENCES MDB.RATING(ID);
ALTER TABLE MDB.TV_SHOW ADD CONSTRAINT TVSHOW_GENREID_FK FOREIGN KEY(GENRE_ID) REFERENCES MDB.GENRE(ID);
ALTER TABLE MDB.TV_SHOW ADD CONSTRAINT TVSHOW_LANGID_FK FOREIGN KEY(LANGUAGE_ID) REFERENCES MDB.LANGUAGE(ID);

CREATE TABLE MDB.MOVIE
(
    ID                     INTEGER          NOT NULL AUTO_INCREMENT,
    TITLE                  VARCHAR(100)     NOT NULL,
    RELEASE_DATE           DATE             NOT NULL,
    STUDIO                 VARCHAR(100)     NOT NULL,
    RATING_ID              INTEGER          NOT NULL,
    GENRE_ID               INTEGER          NOT NULL,
    LANGUAGE_ID            INTEGER          NOT NULL,
    PLOT_SUMMARY           VARCHAR(1024)    ,
    NOTES                  VARCHAR(4096)    ,
  PRIMARY KEY(ID)
);
ALTER TABLE MDB.MOVIE ADD CONSTRAINT MOVIE_RATINGID_FK FOREIGN KEY(RATING_ID) REFERENCES MDB.RATING(ID);
ALTER TABLE MDB.MOVIE ADD CONSTRAINT MOVIE_GENREID_FK FOREIGN KEY(GENRE_ID) REFERENCES MDB.GENRE(ID);
ALTER TABLE MDB.MOVIE ADD CONSTRAINT MOVIE_LANGID_FK FOREIGN KEY(LANGUAGE_ID) REFERENCES MDB.LANGUAGE(ID);
