#!/bin/sh
cd `dirname $0` 

FILE_PATH=$(pwd)"/testfile"
FILE_TYPE="sh"
TEMP_FOLDER_NAME="TemporaryStorage"

function show() {
	echo " "
	echo "-------------------------------------------------"
	echo "--------- 1    Change files format --------------"
	echo "--------- q    Exit the program    --------------"
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
			createFolder
			changefileformat $FILE_PATH
			removeFolder
	fi
	echo " "
}

function changefileformat(){ 
for file in ` ls $1 `  
do  
	F_PATH="$1/$file"
    if [ -d $F_PATH ]  
    then
		changefileformat $F_PATH
    else  
		if [ "${file##*.}" = $FILE_TYPE ]
		then
			FILE_NAME=${file%.*}
			mv $F_PATH $TEMP_FOLDER_NAME
			sed 's/^M//' $TEMP_FOLDER_NAME/${file##*/} > $F_PATH
        fi   
    fi  
done  
}

function createFolder() {
	mkdir $TEMP_FOLDER_NAME
}

function removeFolder() {
	rm -rf $TEMP_FOLDER_NAME
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
