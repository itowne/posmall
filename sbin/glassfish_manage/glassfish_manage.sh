#!/bin/sh
cd `dirname $0`

ASADMIN_PATH="glassfish4/bin"
DOMAIN_NAME="posmall"
USR_NAME="admin"
PWD_PATH="glassfish4/.."


function interface() {
echo ""
echo ""
echo "----------------------------"
echo " 1) Create domain"
echo " 2) Delete domain"
echo " 3) Start domain"
echo " 4) Stop domain"
echo " 5) Restart domain"
echo " 6) Change admin password"
echo " 7) Enable secure admin"
echo " 8) Disable secure admin"
echo " q) Quit"
echo "----------------------------"
echo ""
}

function decideAction() {
read -p "Please input step number:  " action
	while [ "$action"x != "1x" ] && [ "$action"x != "2x" ] && [ "$action"x != "3x" ] && [ "$action"x != "4x" ] && [ "$action"x != "5x" ] && [ "$action"x != "6x" ] && [ "$action"x != "7x" ] && [ "$action"x != "8x" ] && [ "$action"x != "qx" ]
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
		createDomain
	elif [ "$1"x = "2x" ]
		then
		confirmDelete
	elif [ "$1"x = "3x" ]
		then 
		startDomain
	elif [ "$1"x = "4x" ]
		then
		stopDomain
	elif [ "$1"x = "5x" ]
		then
		restartDomain
	elif [ "$1"x = "6x" ]
		then
		changePassword
	elif [ "$1"x = "7x" ]
		then
		enableSecure
	elif [ "$1"x = "8x" ]
		then
		disableSecure
	fi
	echo " "
}

function confirmDelete() {
read -p "Plese enter Y or y to confirm delete the domain $DOMAIN_NAME:  " flag
if [ "$flag"x = "yx" ]
	then
	deleteDomain
elif [ "$flag"x = "Yx" ]
	then
	deleteDomain
fi
}

function createDomain() {
$ASADMIN_PATH/asadmin --user $USR_NAME --passwordfile $PWD_PATH/passwd create-domain $DOMAIN_NAME
}

function deleteDomain() {
$ASADMIN_PATH/asadmin --user $USR_NAME --passwordfile $PWD_PATH/passwd delete-domain $DOMAIN_NAME
}

function startDomain() {
$ASADMIN_PATH/asadmin --user $USR_NAME --passwordfile $PWD_PATH/passwd start-domain $DOMAIN_NAME
}

function stopDomain() {
$ASADMIN_PATH/asadmin --user $USR_NAME --passwordfile $PWD_PATH/passwd stop-domain $DOMAIN_NAME
}

function restartDomain() {
$ASADMIN_PATH/asadmin --user $USR_NAME --passwordfile $PWD_PATH/passwd restart-domain $DOMAIN_NAME
}

function changePassword() {
$ASADMIN_PATH/asadmin --user $USR_NAME --passwordfile $PWD_PATH/passwd change-admin-password --domain_name $DOMAIN_NAME
}

function enableSecure() {
$ASADMIN_PATH/asadmin --user $USR_NAME --passwordfile $PWD_PATH/passwd enable-secure-admin
}

function disableSecure() {
$ASADMIN_PATH/asadmin --user $USR_NAME --passwordfile $PWD_PATH/passwd disable-secure-admin
}

while true
do 
	interface
	decideAction
	if [ $action = "q" ]
 	then
 		exit
 	fi
	execute $action
done