@ECHO OFF
setlocal
title Generate JAXB classes from XSD schema using xjc

rem Usage:  jaxb_xjc.cmd [schema file] -d [your src dir]

rem XJC Usage: xjc [-options ...] <schema_file/URL/dir> ... [-b <bindinfo>] ...
rem Options:
rem  -nv                  : do not perform strict validation of the input schema(s)
rem  -extension           : allow vendor extensions - do not strictly follow the
rem                         Compatibility Rules and App E.2 from the JAXB Spec
rem  -b <file/dir>        : specify external bindings files (each <file> must have its own -b)
rem                         If a directory is given, **/*.xjb is searched
rem  -d <dir>             : generated files will go into this directory
rem  -p <pkg>             : specifies the target package
rem  -httpproxy <proxy>   : set HTTP/HTTPS proxy. Format is [user[:password]@]proxyHost:proxyPort
rem  -httpproxyfile <file>: set the proxy string (same format as above).
rem  -classpath <arg>     : specify where to find user class files
rem  -catalog <file>      : specify catalog files to resolve external entity references
rem                        support TR9401, XCatalog, and OASIS XML Catalog format.
rem  -readOnly            : generated files will be in read-only mode
rem  -npa                 : suppress generation of package level annotations (**/package-info.java)
rem  -no-header           : suppress generation of a file header with timestamp
rem  -target 2.0          : behave like XJC 2.0 and generate code that doesnt use any 2.1 features.
rem  -xmlschema           : treat input as W3C XML Schema (default)
rem  -relaxng             : treat input as RELAX NG (experimental,unsupported)
rem  -relaxng-compact     : treat input as RELAX NG compact syntax (experimental,unsupported)
rem  -dtd                 : treat input as XML DTD (experimental,unsupported)
rem  -wsdl                : treat input as WSDL and compile schemas inside it (experimental,unsupported)
rem  -verbose             : be extra verbose
rem  -quiet               : suppress compiler output
rem  -help                : display this help message
rem  -version             : display version information

set SCHEMA_FILE=%1
set DEST_FLG=%2
set DEST_DIR=%3

set JAVA_HOME=C:\java\jdk1.8.0_65
%JAVA_HOME%\bin\xjc.exe %SCHEMA_FILE% %DEST_FLG% %DEST_DIR%

endlocal
