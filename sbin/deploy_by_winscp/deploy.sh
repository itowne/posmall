date=$(date +%Y%m%d%H%M)
ROOT_PASS=/home/nladmin
DOMAIN_NAME=posmall

$ROOT_PASS/glassfish4/bin/asadmin --user admin --passwordfile $ROOT_PASS/.passwd undeploy $DOMAIN_NAME
$ROOT_PASS/glassfish4/bin/asadmin --user admin --passwordfile $ROOT_PASS/.passwd stop-domain $DOMAIN_NAME

rm -rf $ROOT_PASS/glassfish4/glassfish/domains/$DOMAIN_NAME/applications/$DOMAIN_NAME
rm -rf $ROOT_PASS/glassfish4/glassfish/domains/$DOMAIN_NAME/applications/__internal
cd $ROOT_PASS/glassfish4/bin
$ROOT_PASS/glassfish4/bin/asadmin --user amdin --passwordfile $ROOT_PASS/.passwd start-domain $DOMAIN_NAME
$ROOT_PASS/glassfish4/bin/asadmin --user admin --passwordfile $ROOT_PASS/.passwd deploy --verify=false --name $DOMAIN_NAME --enabled=true --precompilejsp=true --contextroot $DOMAIN_NAME --keepstate=true $ROOT_PASS/webapp/$DOMAIN_NAME.war
