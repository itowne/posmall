@echo off
:: parma:
:: %1:SQL_PATH
:: %2:USR_NAME
:: %3:USR_PWD
:: %4:DB_NAME
:: %5:CODING_FORMAT

cd %~dp0
set ITERACTE_DIR_SCRIPT_PATH=iteracte_dir
set EXEC_SQL_SCRIPT_PATH=exec_sql

goto choose

:choose
@echo.
@echo ----------------------
@echo  1) Create database
@echo  2) Init database
@echo  3) Insert test date
@echo  0) Execute 1 2 3
@echo  q) quit
@echo ----------------------
@echo.
goto show

:show
set action=-10
set/p action="Please input step number:  "
if {%action%} == {1} (goto createdb)
if {%action%} == {2} (goto initdb)
if {%action%} == {3} (goto insertdb)
if {%action%} == {0} (goto all)
if {%action%} == {q} (goto over) else (goto errorshow)

:errorshow
@echo.
@echo The input step number is not correct!
@echo.
goto show

:createdb
call %ITERACTE_DIR_SCRIPT_PATH% %SQL_PATH%\1_create_db %EXEC_SQL_SCRIPT_PATH% %2 %3 %4 %5
goto choose

:initdb
call %ITERACTE_DIR_SCRIPT_PATH% %SQL_PATH%\2_init_db %EXEC_SQL_SCRIPT_PATH% %2 %3 %4 %5
goto choose

:insertdb
call %ITERACTE_DIR_SCRIPT_PATH% %SQL_PATH%\3_test_db %EXEC_SQL_SCRIPT_PATH% %2 %3 %4 %5
goto choose

:all
call %ITERACTE_DIR_SCRIPT_PATH% %SQL_PATH% %EXEC_SQL_SCRIPT_PATH% %2 %3 %4 %5
goto choose

:over
@echo off