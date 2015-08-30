@echo off
cd %~dp0

set SQL_PATH=..\..\database\sql
set USR_NAME=root
set USR_PWD=Dz1wDyE8
set DB_NAME=posmall
set CODING_FORMAT=utf8

set MENU_SCRIPT_PATH=menu

call %MENU_SCRIPT_PATH% %SQL_PATH% %USR_NAME% %USR_PWD% %DB_NAME% %CODING_FORMAT%

cd %~dp0