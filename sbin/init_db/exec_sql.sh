#!/bin/sh
# $1:FILE_NAME
# $2:FILE_PATH/FILE_NAME
# $3:USR_NAME
# $4:USR_PWD
# $5:DB_NAME
# $6:CODING_FORMAT
set -e
echo    "--------------------$1--------------------"
mysql -u$3 -p$4 $5 --default-character-set=$6 < $2
# mysql -v -uroot -proot posmall --default-character-set=utf8 < $2