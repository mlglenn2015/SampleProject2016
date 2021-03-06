--------------------------------------------------------------------------------
-- create_stock_price.sql
--------------------------------------------------------------------------------
ALTER SESSION SET CURRENT_SCHEMA=STOCKS;

DROP TABLE STOCK_PRICE;
DROP SEQUENCE SEQ_STOCK_PRICE;

CREATE TABLE STOCK_PRICE (
  ID                  NUMBER(10,0) NOT NULL PRIMARY KEY,
  STOCK_SYMBOL        VARCHAR2(10) NOT NULL,
  CURRENT_PRICE       DECIMAL(10,2) NOT NULL
) TABLESPACE tbs_users_01;

CREATE UNIQUE INDEX stock_price_idx01 ON  STOCK_PRICE (stock_symbol);

CREATE SEQUENCE SEQ_STOCK_PRICE
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 23667
  NOCYCLE
  CACHE 10;

INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'ABX', 15.29);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'AES', 11.40);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'AVGO', 168.16);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'AWK', 71.83);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'BAC', 20.00);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'BRKB', 157.75);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'CCI', 85.00);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'CHL', 54.29);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'COP', 44.76);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'CSAL', 24.32);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'DIS', 98.24);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'DRI', 72.84);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'DUK', 73.60);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'EXC', 31.73);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'FTR', 3.42);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'GIS', 61.26);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'GSK', 38.11);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'VZ', 48.07);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'X', 28.57);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'XOM', 85.28);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (SEQ_STOCK_PRICE.nextval, 'WMT', 68.54);

COMMIT;
