#!/bin/sh  
# param:
# $1:SEARCH_PATH
# $2:EXEC_SQL_SCRIPT_PATH
# $3:USR_NAME
# $4:USR_PWD
# $5:DB_NAME
# $6:CODING_FORMAT
function search(){ 
for file in ` ls $1 `  
do  
	FILE_PATH="$1/$file"
    if [ -d $FILE_PATH ]  
    then  
		search $FILE_PATH $2 $3 $4 $5 $6
    else  
		if [ "${file##*.}" = "sql" ]
		then
			sh $2 ${file%.*} $FILE_PATH $3 $4 $5 $6
        fi   
    fi  
done  
}

search $1 $2 $3 $4 $5 $6