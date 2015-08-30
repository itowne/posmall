cd ../..
call mvn clean package -Pprod -Dmaven.test.skip=true
cd sbin/deploy_by_winscp
winscp /script=upload.scp /privatekey=id-rsa.ppk

