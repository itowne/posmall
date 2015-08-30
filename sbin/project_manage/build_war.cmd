@echo off
:: param:
:: %1:PROFILE
call mvn clean package -P%1 -Dmaven.test.skip=true