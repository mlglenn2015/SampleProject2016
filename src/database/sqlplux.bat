
rem MLG created 2016-12-12

@echo off

set JAVA_HOME=C:\Java\jdk1.8.0_65
set ORACLE_HOME=C:\Oracle\ExpressDB\app\oracle\product\11.2.0\server\
set ORACLE_BASE=C:\Oracle\ExpressDB\
set ORACLE_DB_LISTENER_PORT=1521
set ORACLE_MSFT_TRX_LISTENER_PORT=2030
set ORACLE_HTTP_LISTENER_PORT=8080

echo ORACLE_HOME=%ORACLE_HOME%
echo PATH=%PATH%
echo JAVA_HOME=%JAVA_HOME%

echo Starting C:\Oracle\ExpressDB\app\oracle\product\11.2.0\server\bin\sqlplus.exe ...

C:\Oracle\ExpressDB\app\oracle\product\11.2.0\server\bin\sqlplus.exe /nolog

