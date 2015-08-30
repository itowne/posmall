@echo off
:: parma:
:: %1:FILE_NAME
:: %2:USR_NAME
:: %3:USR_PWD
:: %4:DB_NAME
:: %5:CODING_FORMAT
@echo.
@echo.
@echo Import sql:  %1
mysql -u%2 -p%3 %4 --default-character-set=%5 < %1