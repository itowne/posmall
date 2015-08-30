@echo off
:: param:
:: %1:GLASSFISH_PATH
:: %2:DOMAIN_NAME
set GLASSFISH_PATH=%1
set DOMAIN_NAME=%2

call %GLASSFISH_PATH%\glassfish4\bin\asadmin --user admin --passwordfile %GLASSFISH_PATH%\passwd undeploy %DOMAIN_NAME%
call %GLASSFISH_PATH%\glassfish4\bin\asadmin --user admin --passwordfile %GLASSFISH_PATH%\passwd stop-domain %DOMAIN_NAME%

del /s /q %GLASSFISH_PATH%\glassfish4\glassfish\domains\%DOMAIN_NAME%\applications\%DOMAIN_NAME%
del /s /q %GLASSFISH_PATH%\glassfish4\glassfish\domains\%DOMAIN_NAME%\applications\__internal

call %GLASSFISH_PATH%\glassfish4\bin\asadmin --user amdin --passwordfile %GLASSFISH_PATH%\passwd start-domain %DOMAIN_NAME%
call %GLASSFISH_PATH%\glassfish4\bin\asadmin --user admin --passwordfile %GLASSFISH_PATH%\passwd deploy --verify=false --name %DOMAIN_NAME% --enabled=true --precompilejsp=true --contextroot %DOMAIN_NAME% --keepstate=true %GLASSFISH_PATH%\%DOMAIN_NAME%.war