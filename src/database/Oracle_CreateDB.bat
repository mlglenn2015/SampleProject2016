@echo off
REM
REM Copyright (c) 2005, 2011, Oracle and/or its affiliates.
REM All rights reserved.
REM
REM  NAME
REM      createdb.bat - Create an XE initial database
REM
REM  DESCRIPTION
REM
REM      This script will create the initial XE database,
REM      run catalog, catproc, install Oracle Text, XDB,
REM      and HTML DB.  It will also create the initial
REM      init.ora file and an spfile at the end
REM
REM      It will initially remove all existing files
REM
REM  ARGUMENTS
REM
REM      -force:    Avoid the 10 second delay
REM      -shiploc:  Put files in /usr/lib/oracle/oexpress
REM      -dbchar:   Specify the character set of the database
REM      -runas:    Create service to run as a specifed user
REM      -filedir:  Location where database files should be created
REM      -logdir:   Locations where the log files will be generated
REM
REM  NOTES
REM
REM  TO DO ITEMS!!!
REM    1)  Make sure this works if there are spaces in ORACLE_HOME (i.e.
REM                                                C:\Program Files\oracle\...)
REM        If spaces in ORACLE_HOME, there will most likely need to be
REM                                          single-quotes or parenthesis added
REM          around several init.ora parameter values.
REM    2)  Depending upon how this script is called, verify that any languages
REM                                                         with spaces in them
REM          (i.e. TRADITIONAL CHINESE) are passed correctly into this script
REM                                                      and parse accordingly.
REM    3)  Depending upon the requirements, add return codes to the very bottom
REM                                                 of the script.  An 'exit 1'
REM          statement after the :end1 label, and an 'exit 0' statement after
REM                                                 the :end label.  Doing this
REM          however, will cause all of cmd.exe to go away, which is OK if this
REM                                                            thing is spawned
REM          programatically, but not OK if running interactively.
REM    4)  Behavior differences from Linux shell script:
REM          - if -force is not specified, Linux waits 10 seconds before
REM                                          proceeding.  Windows waits forever
REM              until the user hits a key
REM          - on Linux, the db is considered to be up if PMON is running.
REM                                                 On Windows, the database is
REM              considered to be up if OracleServiceXE is running.
REM    5)  Confirm locations of files (i.e. dbs vs. database, is O_H\log the
REM                                              correct place for trace files,
REM          password file name difference from Linux, etc...)
REM    6)  Depending on the verbosity required for this script, you may want to
REM                                                  remove all the redirection
REM          to 'nul' in order to get alot more verbose output.
REM    7)  This script doesn't create the TNSListener service.  If required,
REM                                                   this will need to be done
REM          somewhere.
REM    8)  Fix call to dbms_workload_repository.  The redirection symbol (right
REM                                                    arrow) screws up 'echo'.
REM    9)  Verify oradim operation is coded correctly.
REM   10)  There are currently no dependencies on MKS Toolkit or other
REM                                            non-Windows programs.  However,
REM          Command Extensions are relied upon heavily and will need to be
REM                                                  enabled in cmd, if they've
REM          been turned off.   See 'cmd /?' for info on Command Extensions.
REM
REM  MODIFIED   (MM/DD/YYYY)
REM     svaggu   01/21/11 - Updated the user.dbf limit to 11GB
REM     bkhaladk 01/23/06 - call utlrp after revokes
REM     rpingte  01/05/06 - revoke execute permissions for UTL_FILE,UTL_TCP,
REM                         UTL_HTTP and UTL_SMTP for public
REM     rpingte  12/06/05 - bug# 4778605: set time_zone
REM     rpingte  12/06/05 - bug# 4776339: set local host access
REM     rpingte  12/08/05 - do not limit suze of SYSAUX
REM     sravada  12/01/05 - add locator to XE
REM     rpingte  11/18/05 - add init.ora parameters and limit user.dbf to 5GB
REM     rpingte  10/18/05 - move loading htmldb to loadhtmldb.bat
REM     rpingte  10/13/05 - change ftp port, disable AWR and undo tbs
REM     rpingte  10/04/05 - fix ftp and http port
REM     rpingte  09/30/05 - Increase shared servers to 4 for HTMLDB
REM     rpingte  09/22/05 - add filedir and logdir arguments
REM     rpingte  09/17/05 - allow creation in a view
REM     rpingte  09/16/05 - fix most of the TODO's issues
REM     dcolello 09/15/05 - port to Windows
REM     rpingte  09/08/05 - load_trans.sql for htmldb
REM     rpingte  09/07/05 - rpingte_db_creation_scripts
REM     rpingte  08/25/05 - use ORACLE_HOME
REM     bengsig  08/19/05 - Creation
REM

setlocal

REM save away our batch file name
set cmdline=%0

REM Check validity of ORACLE_HOME and set SID
if "(%ORACLE_HOME%)" == "()" goto bad_ohome
if not exist "%ORACLE_HOME%\database" goto bad_ohome
if exist "%ORACLE_HOME%\database\cdb_chk.txt" del /F/Q "%ORACLE_HOME%\database\cdb_chk.txt" > nul 2>&1

REM if we can't delete our write test file, then bail
if exist "%ORACLE_HOME%\database\cdb_chk.txt" goto bad_ohome

REM try to create a file in dbs
echo > "%ORACLE_HOME%\database\cdb_chk.txt"

REM if we can't, then bail
if not exist "%ORACLE_HOME%\database\cdb_chk.txt" goto bad_ohome

REM clean up and continue
del /F/Q "%ORACLE_HOME%\database\cdb_chk.txt"
goto parseargs

:bad_ohome
echo ORACLE_HOME must be set and %%ORACLE_HOME%%\database must be writeable
goto end1

:parseargs
set force=no
set shiploc=no
set dbchar=no
set dbcharset=us7ascii
set runas=no
set runasarg=none
set filedir=%ORACLE_HOME%\database
set fileloc=no
set logdir=%ORACLE_HOME%\log
set logloc=no


:nextarg
if "(%1)"=="()" goto force
if "(%1)"=="(-force)" goto do_force
if "(%1)"=="(-shiploc)" goto do_shiploc
if "(%1)"=="(-dbchar)" goto do_dbchar
if "(%1)"=="(-runas)" goto do_runas
if "(%1)"=="(-filedir)" goto do_filedir
if "(%1)"=="(-logdir)" goto do_logdir
if "(%1)"=="(-help)" goto do_help
echo xe_createdb: Unknown argument '%1'.  Try -help.
goto end1

:do_force
set force=yes
shift
goto nextarg

:do_shiploc
set shiploc=yes
shift
goto nextarg

:do_dbchar
set dbchar=yes
shift
set dbcharset=%1
shift
goto nextarg

:do_runas
set runas=yes
shift
set runasarg=%1
shift
goto nextarg

:do_filedir
set fileloc=yes
shift
set filedir=%1
shift
goto nextarg

:do_logdir
set logloc=yes
shift
set logdir=%1
shift
goto nextarg

:do_help
echo Usage: xe_createdb [-shiploc] [-force] [-dbchar us7ascii] [-help]
echo.
echo   -shiploc to create database in shipping location
echo   -force   to silently overwrite database
echo   -dbchar  specify the database character set
echo   -help    this help
goto end

:force

if NOT "(%ADE_NT_ROOT%)" == "(C:\ADE)" set ORACLE_SID=XE
echo ORACLE_HOME=%ORACLE_HOME%
echo ORACLE_SID=%ORACLE_SID%

if (%force%)==(yes) goto check_db
echo Existing database will be erased, hit ctrl-c to cancel.
pause

:check_db
REM check to see if database service is already running?
set running=no
for /f %%i in ('net start') do if /I "%%i"=="OracleService%ORACLE_SID%" set running=yes
Rem In a view  just start the stop and restart the service
if "(%ADE_NT_ROOT%)" == "(C:\ADE)" net stop OracleService%ORACLE_SID%
if "(%ADE_NT_ROOT%)" == "(C:\ADE)" net start OracleService%ORACLE_SID%
if "(%ADE_NT_ROOT%)" == "(C:\ADE)" goto deletefiles
if (%running%)==(no) goto deletefiles
echo The OracleService%ORACLE_SID% service is already running.
echo Please stop the service before running %cmdline%.
if NOT "(%ADE_NT_ROOT%)" == "(C:\ADE)" goto end1

:deletefiles
if EXIST "%logdir%" rmdir /S/Q "%logdir%" > nul 2>&1
if EXIST "%filedir%" rmdir /S/Q "%filedir%" > nul 2>&1
mkdir "%logdir%" > nul 2>&1
mkdir "%filedir%" > nul 2>&1
if EXIST %ORACLE_HOME%\flash_recovery_area  rmdir /S/Q %ORACLE_HOME%\flash_recovery_area > nul 2>&1
mkdir %ORACLE_HOME%\flash_recovery_area > nul 2>&1


REM create init.ora
echo db_name=%ORACLE_SID% > "%filedir%\init%ORACLE_SID%.ora"
echo. >> "%filedir%\init%ORACLE_SID%.ora"
echo control_files=%filedir%\control.dbf >> "%filedir%\init%ORACLE_SID%.ora"
echo. >> "%filedir%\init%ORACLE_SID%.ora"
echo undo_management=auto >> "%filedir%\init%ORACLE_SID%.ora"
echo undo_tablespace=undotbs1 >> "%filedir%\init%ORACLE_SID%.ora"
echo. >> "%filedir%\init%ORACLE_SID%.ora"
echo sga_target=340M >> "%filedir%\init%ORACLE_SID%.ora"
echo pga_aggregate_target=100M >> "%filedir%\init%ORACLE_SID%.ora"
echo. >> "%filedir%\init%ORACLE_SID%.ora"
echo sessions=20 >> "%filedir%\init%ORACLE_SID%.ora"
echo open_cursors=300 >> "%filedir%\init%ORACLE_SID%.ora"
echo. >> "%filedir%\init%ORACLE_SID%.ora"
echo remote_login_passwordfile=EXCLUSIVE >> "%filedir%\init%ORACLE_SID%.ora"
echo. >> "%filedir%\init%ORACLE_SID%.ora"
echo compatible=11.2.0.0.0 >> "%filedir%\init%ORACLE_SID%.ora"
echo. >> "%filedir%\init%ORACLE_SID%.ora"
echo diagnostic_dest=%logdir% >> "%filedir%\init%ORACLE_SID%.ora"
echo audit_file_dest=%logdir% >> "%filedir%\init%ORACLE_SID%.ora"
echo. >> "%filedir%\init%ORACLE_SID%.ora"
echo job_queue_processes=4 >> "%filedir%\init%ORACLE_SID%.ora"
echo shared_servers=4 >> "%filedir%\init%ORACLE_SID%.ora"
echo dispatchers="(PROTOCOL=TCP) (SERVICE=%ORACLE_SID%XDB)" >> "%filedir%\init%ORACLE_SID%.ora"
echo db_recovery_file_dest_size=10G >> "%filedir%\init%ORACLE_SID%.ora"
echo db_recovery_file_dest="%ORACLE_HOME%\\flash_recovery_area" >> "%filedir%\init%ORACLE_SID%.ora"

REM Delete the password file if it exists
if EXIST "%T_WORK%\pwd%ORACLE_SID%.ora" del %T_WORK%\pwd%ORACLE_SID%.ora
if EXIST "%filedir%\pwd%ORACLE_SID%.ora" del %filedir%\pwd%ORACLE_SID%.ora

REM If in a view the service must be created using mktwork, so just create
REM the password file
if "(%ADE_NT_ROOT%)" == "(C:\ADE)" orapwd "file=%T_WORK%\pwd%ORACLE_SID%.ora" password=oracle entries=5
if "(%ADE_NT_ROOT%)" == "(C:\ADE)" goto skip_service_creation

REM Create Oracle service after deleting any existing one
oradim -delete -sid XE > nul 2>&1
if "(%runas%)" == "(yes)" set /P password=Enter password for %USERDOMAIN%\%USERNAME%:
if "(%runas%)" == "(no)" oradim -new -sid XE -syspwd oracle -startmode auto -srvcstart system -pfile "%filedir%\init%ORACLE_SID%.ora" -shutmode normal
if "(%runas%)" == "(yes)" oradim -new -sid XE -syspwd oracle -startmode auto -srvcstart system -pfile "%filedir%\init%ORACLE_SID%.ora" -shutmode normal -runas %USERDOMAIN%\%USERNAME%/%password%

:skip_service_creation

echo spool xe_createdb.log > "%ORACLE_HOME%\xe_createdb.sql"
echo connect sys/oracle as sysdba >> "%ORACLE_HOME%\xe_createdb.sql"
echo startup nomount pfile=%%filedir%%\init%%ORACLE_SID%%.ora >> "%ORACLE_HOME%\xe_createdb.sql"
echo whenever sqlerror exit; >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo create database  >> "%ORACLE_HOME%\xe_createdb.sql"
echo   maxinstances 1 >> "%ORACLE_HOME%\xe_createdb.sql"
echo   maxloghistory 2 >> "%ORACLE_HOME%\xe_createdb.sql"
echo   maxlogfiles 16 >> "%ORACLE_HOME%\xe_createdb.sql"
echo   maxlogmembers 2 >> "%ORACLE_HOME%\xe_createdb.sql"
echo   maxdatafiles 30 >> "%ORACLE_HOME%\xe_createdb.sql"
echo datafile '%filedir%\system.dbf' >> "%ORACLE_HOME%\xe_createdb.sql"
echo   size 200M reuse autoextend on next 10M maxsize 600M >> "%ORACLE_HOME%\xe_createdb.sql"
echo   extent management local >> "%ORACLE_HOME%\xe_createdb.sql"
echo sysaux datafile '%filedir%\sysaux.dbf' >> "%ORACLE_HOME%\xe_createdb.sql"
echo   size 10M reuse autoextend on next  10M >> "%ORACLE_HOME%\xe_createdb.sql"
echo default temporary tablespace temp tempfile '%filedir%\temp.dbf' >> "%ORACLE_HOME%\xe_createdb.sql"
echo   size 20M reuse autoextend on next  10M maxsize 500M >> "%ORACLE_HOME%\xe_createdb.sql"
echo undo tablespace undotbs1 datafile '%filedir%\undotbs1.dbf' >> "%ORACLE_HOME%\xe_createdb.sql"
echo   size 50M reuse autoextend on next  5M maxsize 500M >> "%ORACLE_HOME%\xe_createdb.sql"
echo  --character set al32utf8 >> "%ORACLE_HOME%\xe_createdb.sql"
echo  character set %dbcharset% >> "%ORACLE_HOME%\xe_createdb.sql"
echo  national character set al16utf16 >> "%ORACLE_HOME%\xe_createdb.sql"
echo  set time_zone='00:00' >> "%ORACLE_HOME%\xe_createdb.sql"
echo  controlfile reuse >> "%ORACLE_HOME%\xe_createdb.sql"
echo  logfile '%filedir%\log1.dbf' size 50m reuse >> "%ORACLE_HOME%\xe_createdb.sql"
echo        , '%filedir%\log2.dbf' size 50m reuse >> "%ORACLE_HOME%\xe_createdb.sql"
echo        , '%filedir%\log3.dbf' size 50m reuse >> "%ORACLE_HOME%\xe_createdb.sql"
echo user system identified by oracle >> "%ORACLE_HOME%\xe_createdb.sql"
echo user sys identified by oracle >> "%ORACLE_HOME%\xe_createdb.sql"
echo / >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- create the tablespace for users data >> "%ORACLE_HOME%\xe_createdb.sql"
echo create tablespace users >> "%ORACLE_HOME%\xe_createdb.sql"
echo   datafile '%filedir%\users.dbf' >> "%ORACLE_HOME%\xe_createdb.sql"
echo   size 100M reuse autoextend on next 10M maxsize 11G >> "%ORACLE_HOME%\xe_createdb.sql"
echo   extent management local >> "%ORACLE_HOME%\xe_createdb.sql"
echo / >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- install data dictionary views: >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\rdbms\admin\catalog.sql >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- run catblock >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\rdbms\admin\catblock >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- run catproc >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\rdbms\admin\catproc >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- run catoctk >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\rdbms\admin\catoctk >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- run pupbld >> "%ORACLE_HOME%\xe_createdb.sql"
echo connect system/oracle >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\sqlplus\admin\pupbld >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\sqlplus\admin\help\hlpbld.sql helpus.sql; >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- run plustrace >> "%ORACLE_HOME%\xe_createdb.sql"
echo connect sys/oracle as sysdba >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\sqlplus\admin\plustrce >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- Install context >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\ctx\admin\catctx oracle SYSAUX TEMP NOLOCK; >> "%ORACLE_HOME%\xe_createdb.sql"
echo connect CTXSYS/oracle >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\ctx\admin\defaults\dr0defin.sql "AMERICAN" >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- Install XDB >> "%ORACLE_HOME%\xe_createdb.sql"
echo connect sys/oracle as sysdba >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\rdbms\admin\catqm.sql oracle SYSAUX TEMP; >> "%ORACLE_HOME%\xe_createdb.sql"
echo connect SYS/oracle as SYSDBA >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\rdbms\admin\catxdbj.sql; >> "%ORACLE_HOME%\xe_createdb.sql"
echo connect SYS/oracle as SYSDBA >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\rdbms\admin\catxdbdbca.sql 0 8080; >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- set Local access >> "%ORACLE_HOME%\xe_createdb.sql"
echo connect SYS/oracle as SYSDBA >> "%ORACLE_HOME%\xe_createdb.sql"
echo begin dbms_xdb.setListenerLocalAccess(TRUE); end; >> "%ORACLE_HOME%\xe_createdb.sql"
echo / >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- Install Spatial Locator >> "%ORACLE_HOME%\xe_createdb.sql"
echo connect sys/oracle as sysdba >> "%ORACLE_HOME%\xe_createdb.sql"
echo create user MDSYS identified by MDSYS account lock; >> "%ORACLE_HOME%\xe_createdb.sql"
echo Alter session set current_schema=MDSYS; >> "%ORACLE_HOME%\xe_createdb.sql"
echo Alter user MDSYS default tablespace SYSAUX;  >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\md\admin\mdprivs.sql >> "%ORACLE_HOME%\xe_createdb.sql"
echo @%%ORACLE_HOME%%\md\admin\catmdloc.sql >> "%ORACLE_HOME%\xe_createdb.sql"
echo Alter session set current_schema=SYS; >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo create spfile='%filedir%\spfile.ora' from pfile >> "%ORACLE_HOME%\xe_createdb.sql"
echo / >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo alter user anonymous account unlock >> "%ORACLE_HOME%\xe_createdb.sql"
echo / >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo disconnect >> "%ORACLE_HOME%\xe_createdb.sql"
echo.  >> "%ORACLE_HOME%\xe_createdb.sql"
echo -- recompile invalid objects >> "%ORACLE_HOME%\xe_createdb.sql"
echo connect sys/oracle as sysdba >> "%ORACLE_HOME%\xe_createdb.sql"
echo begin dbms_workload_repository.modify_snapshot_settings(0); end; >> "%ORACLE_HOME%\xe_createdb.sql"
echo / >> "%ORACLE_HOME%\xe_createdb.sql"
echo begin dbms_scheduler.disable('AUTO_SPACE_ADVISOR_JOB', true); end; >> "%ORACLE_HOME%\xe_createdb.sql"
echo / >> "%ORACLE_HOME%\xe_createdb.sql"
echo spool off >> "%ORACLE_HOME%\xe_createdb.sql"
echo exit >> "%ORACLE_HOME%\xe_createdb.sql"

sqlplus /nolog @%%ORACLE_HOME%%\xe_createdb.sql
del "%ORACLE_HOME%\xe_createdb.sql"

for /F "delims=: " %%i in ('FINDSTR.EXE ORA- xe_createdb.log') DO (
  SET err=%%~i
)
if "(%err%)"=="(ORA-12701)" echo Database creation failed
if "(%err%)"=="(ORA-12701)" goto end

REM
echo exit | sqlplus "sys/oracle as sysdba" @%%ORACLE_HOME%%\demo\schema\human_resources\hr_main HR USERS TEMP oracle demo_

REM Grant access to hr, revoke public executete, Load CLR for ODP
echo spool invclr.log > "%ORACLE_HOME%\invclr.sql"
echo connect sys/oracle as sysdba >> "%ORACLE_HOME%\invclr.sql"
echo grant connect to hr; >> "%ORACLE_HOME%\invclr.sql"
echo revoke execute on sys.UTL_FILE from public; >> "%ORACLE_HOME%\invclr.sql"
echo revoke execute on sys.UTL_TCP  from public; >> "%ORACLE_HOME%\invclr.sql"
echo revoke execute on sys.UTL_HTTP from public; >> "%ORACLE_HOME%\invclr.sql"
echo revoke execute on sys.UTL_SMTP from public; >> "%ORACLE_HOME%\invclr.sql"
echo grant execute on UTL_FILE to XDB; >> "%ORACLE_HOME%\invclr.sql"
echo @%%ORACLE_HOME%%\rdbms\admin\dbmsclr.plb >> "%ORACLE_HOME%\invclr.sql"
echo @%%ORACLE_HOME%%\rdbms\admin\utlrp.sql; >> "%ORACLE_HOME%\invclr.sql"
echo.  >> "%ORACLE_HOME%\invclr.sql"
echo spool off >> "%ORACLE_HOME%\invhtml.sql"
echo exit >> "%ORACLE_HOME%\invclr.sql"
sqlplus /nolog @%%ORACLE_HOME%%\invclr.sql
del /Q/F "%ORACLE_HOME%\invclr.sql" > nul 2>&1

REM Save the oldfashioned init.ora file
move /Y "%filedir%\init%ORACLE_SID%.ora" "%filedir%\init%ORACLE_SID%_DEFAULT.ora" > nul 2>&1

REM create spfile pointer if shiploc
if (%shiploc%)==(yes) echo spfile=%filedir%\spfile.ora > "%filedir%\init%ORACLE_SID%.ora"
goto end

:end1
echo %cmdline% terminated unsucessfully.
REM fallthrough here for cleanup

:end
endlocal

