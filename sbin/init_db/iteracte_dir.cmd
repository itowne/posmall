@echo off
:: parma:
:: %1:SEARCH_PATH
:: %2:EXEC_SQL_SCRIPT_PATH
:: %3:USR_NAME
:: %4:USR_PWD
:: %5:DB_NAME
:: %6:CODING_FORMAT

set flag="false"

for /r %1 %%s in ("*.sql") do (
	set flag="true"
	call %2 %%s %3 %4 %5 %6
	if errorlevel 1 goto errorOccurred
	@echo Import Success!
)
if %flag% == "false" goto nullError
goto success

:errorOccurred
@echo.
@echo Error occurred!
goto out

:nullError
@echo SQL file does not exist!
goto out

:success
@echo.
@echo Successfully!
goto out

:out
@echo.