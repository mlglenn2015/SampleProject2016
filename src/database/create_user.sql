
#open SQL Plus

connect SYSTEM/admin

CREATE USER stocks IDENTIFIED BY stocks;

GRANT CONNECT TO stocks;

GRANT RESOURCE, DBA TO stocks;

GRANT CREATE SESSION TO stocks;

# localhost:1521:xe  stocks/stocks


