@echo off
cd %~dp0

set FILE_PATH=posmall
set SVN_URL=http://www.svnchina.com/svn/posmall/trunk/posmall
set PROFILE=prod
set WAR_NAME=posmall
set GLASSFISH_PATH=..\tools
set DOMAIN_NAME=posmall

set MENU_SCRIPT_PATH=menu

call %MENU_SCRIPT_PATH% %FILE_PATH% %SVN_URL% %PROFILE% %WAR_NAME% %GLASSFISH_PATH% %DOMAIN_NAME%
cd %~dp0