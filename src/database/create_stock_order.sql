--------------------------------------------------------------------------------
-- create_stock_order.sql
--------------------------------------------------------------------------------
ALTER SESSION SET CURRENT_SCHEMA=STOCKS;

DROP TABLE STOCK_ORDER;
DROP SEQUENCE SEQ_STOCK_ORDER;

CREATE TABLE STOCK_ORDER (
  ID                  NUMBER(10,0) NOT NULL PRIMARY KEY,
  STOCK_SYMBOL        VARCHAR2(10) NOT NULL,
  ACTION              VARCHAR2(4) NOT NULL, -- BUY, SELL
  QUANTITY            NUMBER NOT NULL,
  PRICE               DECIMAL(10,2) NOT NULL,
  ORDER_DATE          TIMESTAMP NOT NULL,
  ORDER_TYPE          VARCHAR2(25) NOT NULL,
  ORDER_STATUS        VARCHAR2(25) NOT NULL
);

CREATE INDEX stock_order_idx01 ON  STOCK_ORDER (order_date);
CREATE INDEX stock_order_idx02 ON  STOCK_ORDER (order_type);
CREATE INDEX stock_order_idx03 ON  STOCK_ORDER (order_status);

CREATE SEQUENCE SEQ_STOCK_ORDER
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 23667
  NOCYCLE
  CACHE 10;

INSERT INTO STOCK_ORDER (ID, STOCK_SYMBOL, ACTION, QUANTITY, PRICE, ORDER_DATE, ORDER_TYPE, ORDER_STATUS)
  VALUES (SEQ_STOCK_ORDER.nextval, 'ZTEST', 'BUY', 0, 0.00, CURRENT_TIMESTAMP, 'MARKET ORDER', 'CANCELLED');

COMMIT;
