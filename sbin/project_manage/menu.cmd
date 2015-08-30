@echo off
:: param
:: %1:FILE_PATH
:: %2:SVN_URL
:: %3:PROFILE
:: %4:WAR_NAME
:: %5:GLASSFISH_PATH
set FILE_PATH=%1
set SVN_URL=%2
set PROFILE=%3
set WAR_NAME=%4
set GLASSFISH_PATH=%5
set DOMAIN_NAME=%6

set CHECK_OUT_PATH=check_out
set UPDATE_PROJECT_PATH=..\update_project
set BUILD_WAR_PATH=..\build_war
set DEPLOY_APP_PATH=deploy_app

goto interface

:interface
cd %~dp0
@echo.
@echo ----------------------
@echo  1) Check out project
@echo  2) Update project
@echo  3) Build war
@echo  4) Deploy app
@echo  0) Execute 1 2 3 4
@echo  q) Quit
@echo ----------------------
@echo.
goto choose

:choose
set action=-10
set/p action="Please input step number:  "
if {%action%} == {1} (goto isExistProject)
if {%action%} == {2} (goto update)
if {%action%} == {3} (goto build)
if {%action%} == {4} (goto deploy)
if {%action%} == {0} (goto execute)
if {%action%} == {q} (goto over) else (goto inputError)

:inputError
@echo.
@echo The input step number is not correct!
@echo.
goto choose

:isExistProject
if not exist %FILE_PATH% (
	goto checkout
)
@echo The project already exists!
set/p flag="Plese enter Y or y to confirm delete the project:  "
@echo flag:%flag%
if {%flag%} == {Y} (goto deleteProject)
if {%flag%} == {y} (goto deleteProject) else (goto interface)


:deleteProject
rmdir /s /q %FILE_PATH%
if errorlevel 1 goto errorMsg
@echo.
@echo Delete project success!
@echo.
goto checkout

:checkout
call %CHECK_OUT_PATH% %SVN_URL%
if errorlevel 1 goto errorMsg
@echo.
@echo Download success!
@echo.
goto interface

:update
if not exist %FILE_PATH% (
	@echo.
	@echo Project is not exist!
	@echo.
	goto interface
)
cd %FILE_PATH%
call %UPDATE_PROJECT_PATH%
if errorlevel 1 goto errorMsg
@echo.
@echo Update success!
@echo.
goto interface

:build
if not exist %FILE_PATH% (
	@echo.
	@echo Project is not exist!
	@echo.
	goto interface
)
cd %FILE_PATH%
call %BUILD_WAR_PATH% %PROFILE%
if errorlevel 1 goto errorMsg
goto interface

:deploy
if not exist %FILE_PATH%\target\%WAR_NAME%.war (
	@echo.
	@echo The ".war" format file does not exist!
	@echo.
	goto interface
)
copy /y %FILE_PATH%\target\%WAR_NAME%.war %GLASSFISH_PATH%
call %DEPLOY_APP_PATH% %GLASSFISH_PATH% %DOMAIN_NAME%
if errorlevel 1 goto errorMsg
goto interface

:execute
if exist %FILE_PATH% (
	rmdir /s /q %FILE_PATH%
	if errorlevel 1 goto errorMsg
)
@echo.
@echo Delete project success!
@echo.
call %CHECK_OUT_PATH% %SVN_URL%
if errorlevel 1 goto errorMsg
@echo.
@echo Download success!
@echo.

if not exist %FILE_PATH% (
	@echo.
	@echo Project is not exist!
	@echo.
	goto interface
)
cd %FILE_PATH%
call %BUILD_WAR_PATH% %PROFILE%
if errorlevel 1 goto errorMsg

if not exist %FILE_PATH%\target\%WAR_NAME%.war (
	@echo.
	@echo The ".war" format file does not exist!
	@echo.
	goto interface
)
copy /y %FILE_PATH%\target\%WAR_NAME%.war %GLASSFISH_PATH%
call %DEPLOY_APP_PATH% %GLASSFISH_PATH% %DOMAIN_NAME%
if errorlevel 1 goto errorMsg

goto interface

:errorMsg
@echo.
@echo Error occurred!
@echo.
goto interface

:over
@echo off