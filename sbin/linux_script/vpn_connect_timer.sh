#!/bin/sh
cd `dirname $0` 
PING_ADDRESS="192.168.30.33"
START_FLAG="false"
ROUTE_FLAG="false"
SLEEP_FLAG="false"
CONNECT_COUNT=0

function startConnect() {
	SLEEP_FLAG="false"
	if [ "$START_FLAG" = "false" ]
	then 
		echo $(date +%Y-%m-%d)' '$(date +%H:%M) "Start to connect vpn ..."
		START_FLAG="true"
		CONNECT_COUNT=1
		pon newlandcomputer
	else
		CONNECT_COUNT=$[$CONNECT_COUNT+1]
		echo $(date +%Y-%m-%d)' '$(date +%H:%M) "Wait for connect to vpn ..."
		if [ "$CONNECT_COUNT" = "10" ] 
		then
			START_FLAG="false"
			CONNECT_COUNT=0
			poff newlandcomputer
		fi
	fi
}

function addRoute() {
	route add -net 192.168.30.0 netmask 255.255.255.0 gw 10.10.10.1 dev ppp0
	route add -net 192.168.5.0 netmask 255.255.255.0 gw 10.10.10.1 dev ppp0
}

function testConnect() {
	echo $(date +%Y-%m-%d)' '$(date +%H:%M) "Begin to ping ..."
	ping -c 5 $PING_ADDRESS && SLEEP_FLAG="true" && echo $(date +%Y-%m-%d)' '$(date +%H:%M) "Ping success!" || SLEEP_FLAG="false"
	if [ "$SLEEP_FLAG" = "false" ] 
	then
		echo $(date +%Y-%m-%d)' '$(date +%H:%M) "Ping failed!"
		poff newlandcomputer
	fi
}

function gotoSleep() {
if [ "$SLEEP_FLAG" = "true" ]
then
	echo $(date +%Y-%m-%d)' '$(date +%H:%M) "Begin to sleep 60 seconds ..."
	sleep 60
else 
	sleep 10
fi
}

while true
do
echo ""
echo $(date +%Y-%m-%d)' '$(date +%H:%M) "Begin to check the VPN status ..."
VPN=`ifconfig | grep ppp0` 
if [ -z "$VPN" ]
then
	echo $(date +%Y-%m-%d)' '$(date +%H:%M) "VPN status: Not connected!"
	startConnect
else 
	echo $(date +%Y-%m-%d)' '$(date +%H:%M) "VPN status: In the connection!"
	START_FLAG="false"
	addRoute
	testConnect
fi
gotoSleep
done
