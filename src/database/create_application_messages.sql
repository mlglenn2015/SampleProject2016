
--------------------------------------------------------------------------------
-- create_application_messages.sql
--------------------------------------------------------------------------------
ALTER SESSION SET CURRENT_SCHEMA=STOCKS;

DROP TABLE APPLICATION_MESSAGES;
DROP SEQUENCE SEQ_APPLICATION_MESSAGES;

CREATE TABLE APPLICATION_MESSAGES (
  ID          NUMBER(10)                          NOT NULL PRIMARY KEY,
  MESSAGE_KEY VARCHAR2(100)                       NOT NULL,
  MESSAGE     VARCHAR2(500)                       NOT NULL
);

CREATE INDEX app_msgs_idx01 ON  APPLICATION_MESSAGES (MESSAGE_KEY);

CREATE SEQUENCE SEQ_APPLICATION_MESSAGES
MINVALUE 1
MAXVALUE 999999999999999999999999999
START WITH 1
INCREMENT BY 1
CACHE 20;

INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (SEQ_APPLICATION_MESSAGES.nextval,
  'error.invalid.usstate', 'Input is not a valid US State.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (SEQ_APPLICATION_MESSAGES.nextval,
  'error.invalid.uszip', 'Input is not a valid US Zip Code.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (SEQ_APPLICATION_MESSAGES.nextval,
  'error.invalid.uszip4', 'Input is not a valid US Zip4 code.');

COMMIT;