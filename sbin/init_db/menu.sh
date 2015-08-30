#!/bin/sh
# parma:
# $1:SQL_PATH
# $2:USR_NAME
# $3:USR_PWD
# $4:DB_NAME
# $5:CODING_FORMAT
cd `dirname $0` 

PATH0=$(pwd)/$1
PATH1="1_create_db"
PATH2="2_init_db"
PATH3="3_test_db"

USR_NAME=$2
USR_PWD=$3
DB_NAME=$4
CODING_FORMAT=$5

ITERACTE_DIR_SCRIPT_PATH="iteracte_dir.sh"
EXEC_SQL_SCRIPT_PATH="exec_sql.sh"

function show() {
	echo " "
	echo "-------------------------------------------------"
	echo "--------- 1    Create database    ---------------"
	echo "--------- 2    Init database      ---------------"
	echo "--------- 3    Insert test date   ---------------"
	echo "--------- 0    Execute 1 2 3      ---------------"
	echo "--------- q    Exit the program   ---------------"
	echo "-------------------------------------------------"
	echo " "
}

function decideAction() {
	read -p "Please Input step number:  " action
	while [ "$action"x != "1x" ] && [ "$action"x != "2x" ] && [ "$action"x != "3x" ] && [ "$action"x != "0x" ] && [ "$action"x != "qx" ]
	do
		echo " "
		echo "The input step number is not correct!"
		echo " "
		read -p "Please Input step number:  " action
	done
	return
}

function execute() {
    echo " "
	echo    "The start of execution-----------------------------"
	if [ "$1"x = "1x" ]
		then
			sh $ITERACTE_DIR_SCRIPT_PATH $PATH0/$PATH1 $EXEC_SQL_SCRIPT_PATH $USR_NAME $USR_PWD $DB_NAME $CODING_FORMAT
	elif [ "$1"x = "2x" ]
		then
			sh $ITERACTE_DIR_SCRIPT_PATH $PATH0/$PATH2 $EXEC_SQL_SCRIPT_PATH $USR_NAME $USR_PWD $DB_NAME $CODING_FORMAT
	elif [ "$1"x = "3x" ]
		then
			sh $ITERACTE_DIR_SCRIPT_PATH $PATH0/$PATH3 $EXEC_SQL_SCRIPT_PATH $USR_NAME $USR_PWD $DB_NAME $CODING_FORMAT
	elif [ "$1"x = "0x" ]
		then
			sh $ITERACTE_DIR_SCRIPT_PATH $PATH0 $EXEC_SQL_SCRIPT_PATH $USR_NAME $USR_PWD $DB_NAME $CODING_FORMAT
	fi
	echo    "-------------------------------The end of execution"
	echo " "
}

while true
do 
	show
	decideAction
	if [ $action = "q" ]
 	then
 		exit
 	fi
	execute $action $2 $3 $4 $5
done
