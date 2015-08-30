@echo off

set ASADMIN_PATH=glassfish4\bin
set DOMAIN_NAME=posmall
set USR_NAME=admin
set PWD_PATH=glassfish4\..

:interface
cd %~dp0
@echo.
@echo.
@echo ----------------------------
@echo  1) Create domain
@echo  2) Delete domain
@echo  3) Start domain
@echo  4) Stop domain
@echo  5) Restart domain
@echo  6) Change admin password
@echo  7) Enable secure admin
@echo  8) Disable secure admin
@echo  q) Quit
@echo ----------------------------
@echo.
goto choose

:choose
set action=-10
set/p action="Please input step number:  "
if {%action%} == {1} (goto create)
if {%action%} == {2} (goto confirm)
if {%action%} == {3} (goto start)
if {%action%} == {4} (goto stop)
if {%action%} == {5} (goto restart)
if {%action%} == {6} (goto password)
if {%action%} == {7} (goto enable_secure)
if {%action%} == {8} (goto disable_secure)
if {%action%} == {q} (goto over) else (goto inputError)

:inputError
@echo.
@echo The input step number is not correct!
@echo.
goto choose

:confirm
set/p action="Plese enter Y or y to confirm delete the domain %DOMAIN_NAME%:  "
if {%action%} == {Y} (goto delete)
if {%action%} == {y} (goto delete)
goto interface

:create
call %ASADMIN_PATH%\asadmin --user %USR_NAME% --passwordfile %PWD_PATH%\passwd create-domain %DOMAIN_NAME%
if errorlevel 1 goto errorMsg
@echo.
@echo Success!
goto interface

:delete
call %ASADMIN_PATH%\asadmin --user %USR_NAME% --passwordfile %PWD_PATH%\passwd delete-domain %DOMAIN_NAME%
if errorlevel 1 goto errorMsg
@echo.
@echo Success!
goto interface

:start
call %ASADMIN_PATH%\asadmin --user %USR_NAME% --passwordfile %PWD_PATH%\passwd start-domain %DOMAIN_NAME%
if errorlevel 1 goto errorMsg
@echo.
@echo Success!
goto interface

:stop
call %ASADMIN_PATH%\asadmin --user %USR_NAME% --passwordfile %PWD_PATH%\passwd stop-domain %DOMAIN_NAME%
if errorlevel 1 goto errorMsg
@echo.
@echo Success!
goto interface

:restart
call %ASADMIN_PATH%\asadmin --user %USR_NAME% --passwordfile %PWD_PATH%\passwd restart-domain %DOMAIN_NAME%
if errorlevel 1 goto errorMsg
@echo.
@echo Success!
goto interface

:password
call %ASADMIN_PATH%\asadmin --user %USR_NAME% --passwordfile %PWD_PATH%\passwd change-admin-password --domain_name %DOMAIN_NAME%
if errorlevel 1 goto errorMsg
@echo.
@echo Success!
goto interface

:enable_secure
rem call %ASADMIN_PATH%\asadmin --user %USR_NAME% --passwordfile %PWD_PATH%\passwd create-service %DOMAIN_NAME%
rem @echo.
rem @echo Create Service Success!
call %ASADMIN_PATH%\asadmin --user %USR_NAME% --passwordfile %PWD_PATH%\passwd enable-secure-admin %DOMAIN_NAME%
if errorlevel 1 goto errorMsg
@echo.
@echo Success!
goto interface

:disable_secure
call %ASADMIN_PATH%\asadmin --user %USR_NAME% --passwordfile %PWD_PATH%\passwd disable-secure-admin %DOMAIN_NAME%
if errorlevel 1 goto errorMsg
@echo.
@echo Success!
goto interface

:errorMsg
@echo.
@echo Error occurred!
goto interface

:over
@echo off