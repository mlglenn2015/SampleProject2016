

INSERT INTO ORDER_STATUS (ID, ORDER_STATUS, DESCRIPTION)
  VALUES (NEXT VALUE FOR SEQ_ORDER_STATUS, 'PENDING', 'ORDER IN PENDING STATUS');
INSERT INTO ORDER_STATUS (ID, ORDER_STATUS, DESCRIPTION)
  VALUES (NEXT VALUE FOR SEQ_ORDER_STATUS, 'COMPLETED', 'ORDER HAS BEEN COMPLETED');
INSERT INTO ORDER_STATUS (ID, ORDER_STATUS, DESCRIPTION)
  VALUES (NEXT VALUE FOR SEQ_ORDER_STATUS, 'CANCELLED', 'ORDER HAS BEEN CANCELLED');

COMMIT;


INSERT INTO ORDER_TYPES (ID, ORDER_TYPE, DESCRIPTION)
  VALUES (NEXT VALUE FOR SEQ_ORDER_TYPES, 'MARKET ORDER', 'ORDER PLACED AT MARKET ORDER PRICE');
INSERT INTO ORDER_TYPES (ID, ORDER_TYPE, DESCRIPTION)
  VALUES (NEXT VALUE FOR SEQ_ORDER_TYPES, 'LIMIT ORDER', 'ORDER PLACED AT MAX LIMIT ORDER PRICE');
INSERT INTO ORDER_TYPES (ID, ORDER_TYPE, DESCRIPTION)
  VALUES (NEXT VALUE FOR SEQ_ORDER_TYPES, 'STOP LOSS ORDER', 'ORDER PLACED TO PREVENT LOSS');

COMMIT;


INSERT INTO STOCK_ORDER (ID, STOCK_SYMBOL, ACTION, QUANTITY, PRICE, ORDER_DATE, ORDER_TYPE, ORDER_STATUS)
  VALUES (NEXT VALUE FOR SEQ_STOCK_ORDER, 'ZTEST', 'BUY', 0, 0.00, CURRENT_TIMESTAMP, 'MARKET ORDER', 'CANCELLED');

COMMIT;


--INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
--  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'A', 9.99);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'ABX', 15.29);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'AES', 11.40);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'AVGO', 168.16);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'AWK', 71.83);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'BAC', 20.00);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'BRKB', 157.75);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'CCI', 85.00);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'CHL', 54.29);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'COP', 44.76);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'CSAL', 24.32);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'DIS', 98.24);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'DRI', 72.84);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'DUK', 73.60);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'EXC', 31.73);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'FTR', 3.42);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'GIS', 61.26);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'GSK', 38.11);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'VZ', 48.07);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'X', 28.57);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'XOM', 85.28);
INSERT INTO STOCK_PRICE (ID, STOCK_SYMBOL, CURRENT_PRICE)
  VALUES (NEXT VALUE FOR SEQ_STOCK_PRICE, 'WMT', 68.54);

COMMIT;


INSERT INTO TRANSACTION_LOG (ID, LOG_DATE_TIME, TRANSACTION_TYPE, TRANSACTION_DATA)
  VALUES (NEXT VALUE FOR SEQ_TRANSACTION_LOG, CURRENT_TIMESTAMP, 'INTIAL TEST', 'DATA');
INSERT INTO TRANSACTION_LOG (ID, LOG_DATE_TIME, TRANSACTION_TYPE, TRANSACTION_DATA)
  VALUES (NEXT VALUE FOR SEQ_TRANSACTION_LOG, CURRENT_TIMESTAMP, 'STOCK PRICE INQUIRY', 'DATA');
INSERT INTO TRANSACTION_LOG (ID, LOG_DATE_TIME, TRANSACTION_TYPE, TRANSACTION_DATA)
  VALUES (NEXT VALUE FOR SEQ_TRANSACTION_LOG, CURRENT_TIMESTAMP, 'STOCK PURCHASE', 'DATA');
INSERT INTO TRANSACTION_LOG (ID, LOG_DATE_TIME, TRANSACTION_TYPE, TRANSACTION_DATA)
  VALUES (NEXT VALUE FOR SEQ_TRANSACTION_LOG, CURRENT_TIMESTAMP, 'STOCK SALE', 'DATA');

COMMIT;


INSERT INTO TRANSACTION_TYPES (ID, TRANSACTION_TYPE, DESCRIPTION)
  VALUES (NEXT VALUE FOR SEQ_TRANSACTION_TYPES, 'STOCK PRICE INQUIRY', 'STOCK PRICE INQUIRY TRANSACTION');
INSERT INTO TRANSACTION_TYPES (ID, TRANSACTION_TYPE, DESCRIPTION)
  VALUES (NEXT VALUE FOR SEQ_TRANSACTION_TYPES, 'STOCK PURCHASE', 'STOCK PURCHASE TRANSACTION');
INSERT INTO TRANSACTION_TYPES (ID, TRANSACTION_TYPE, DESCRIPTION)
  VALUES (NEXT VALUE FOR SEQ_TRANSACTION_TYPES, 'STOCK SALE', 'STOCK SALE TRANSACTION');

COMMIT;


INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_MESSAGES,
  'error.invalid.usstate', 'Input is not a valid US State.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_MESSAGES,
  'error.invalid.uszip', 'Input is not a valid US Zip Code.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_MESSAGES,
  'error.invalid.uszip4', 'Input is not a valid US Zip4 code.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_MESSAGES,
  'error.invalid.header.source', 'Invalid Header Source.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_MESSAGES,
  'error.invalid.stocksymbol', 'Invalid Stock Symbol.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_MESSAGES,
  'error.invalid.quantity', 'Invalid Quantity.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_MESSAGES,
  'error.invalid.orderaction', 'Invalid Order Action.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_MESSAGES,
  'error.invalid.ordertype', 'Invalid Order Type.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_MESSAGES,
  'info.status.success', 'Last request successful.');
INSERT INTO APPLICATION_MESSAGES (ID, MESSAGE_KEY, MESSAGE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_MESSAGES,
  'info.status.fail', 'There was a problem with the last request.');

COMMIT;


INSERT INTO APPLICATION_PARAMETER (ID, PARAMETER_KEY, PARAMETER_VALUE, ENABLED, CREATE_DATE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_PARAMETER,
    'parm.application.charsetutf8',
    'UTF-8',
    'Y', CURRENT_TIMESTAMP);
INSERT INTO APPLICATION_PARAMETER (ID, PARAMETER_KEY, PARAMETER_VALUE, ENABLED, CREATE_DATE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_PARAMETER,
    'parm.faultstr.client',
    'client',
    'Y', CURRENT_TIMESTAMP);
INSERT INTO APPLICATION_PARAMETER (ID, PARAMETER_KEY, PARAMETER_VALUE, ENABLED, CREATE_DATE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_PARAMETER,
    'parm.faultstr.server',
    'server',
    'Y', CURRENT_TIMESTAMP);
INSERT INTO APPLICATION_PARAMETER (ID, PARAMETER_KEY, PARAMETER_VALUE, ENABLED, CREATE_DATE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_PARAMETER,
    'parm.request.successful',
    'Request Successful',
    'Y', CURRENT_TIMESTAMP);
INSERT INTO APPLICATION_PARAMETER (ID, PARAMETER_KEY, PARAMETER_VALUE, ENABLED, CREATE_DATE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_PARAMETER,
    'parm.request.failed',
    'Request Failed',
    'Y', CURRENT_TIMESTAMP);
INSERT INTO APPLICATION_PARAMETER (ID, PARAMETER_KEY, PARAMETER_VALUE, ENABLED, CREATE_DATE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_PARAMETER,
    'parm.request.pending',
    'Request Pending',
    'Y', CURRENT_TIMESTAMP);
INSERT INTO APPLICATION_PARAMETER (ID, PARAMETER_KEY, PARAMETER_VALUE, ENABLED, CREATE_DATE)
  VALUES (NEXT VALUE FOR SEQ_APPLICATION_PARAMETER,
    'parm.validation.requestheader.source',
    'STOCKTICKER_20170131',
    'Y', CURRENT_TIMESTAMP);

COMMIT;
