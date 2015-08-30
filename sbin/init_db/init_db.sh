#!/bin/sh
cd `dirname $0` 

SQL_PATH=../../database/sql
USR_NAME=root
USR_PWD=Dz1wDyE8
DB_NAME=posmall
CODING_FORMAT=utf8

MENU_SCRIPT_PATH=menu.sh

sh $MENU_SCRIPT_PATH $SQL_PATH $USR_NAME $USR_PWD $DB_NAME $CODING_FORMAT