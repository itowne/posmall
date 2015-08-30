#!/bin/sh

cd `dirname $0`
USR_NAME=root
USR_PWD=Dz1wDyE8
DB_NAME=posmall
FILE_PATH=/root/bin/dbbackup
CODING_FORMAT=utf8

function show() {
	echo " "
	echo "-------------------------------------------------"
	echo "--------- 1    Backup database    ---------------"
	echo "--------- q    Exit the program   ---------------"
	echo "-------------------------------------------------"
	echo " "
}

function decideAction() {
	read -p "Please Input step number:  " action
	while [ "$action"x != "1x" ] && [ "$action"x != "qx" ]
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
	if [ "$1"x = "1x" ]
		then
			executeBackup
	fi
	echo " "
}

function executeBackup() {
	Now=$(date +%Y-%m-%d)'-'$(date +%H:%M)
	File=$DB_NAME-$Now.sql
	mysqldump -u$USR_NAME -p$USR_PWD $DB_NAME --default-character-set=$CODING_FORMAT > $FILE_PATH/$File
	gzip -v $FILE_PATH/$File
}

while true
do 
	show
	decideAction
	if [ $action = "q" ]
 	then
 		exit
 	fi
	execute $action
done