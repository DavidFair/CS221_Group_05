#!/bin/bash

# Copyright (c) 2015 Aberystwyth University
# All rights reserved
#
# Updates MySQL database to contain correct tables
# Provides wrapper for mysql and automates commands
# Author: David (daf5)


#Debug mode
#set -x

# Test for number of args


if [ $# -eq 0 ]
then
	echo "Note: No arguments specified, for a list of arguments which can be passed run with -? argument"
fi


display_help(){
	echo "Creates table structure for TaskerSRV on specified database."
	echo ""

	echo -e "\t -----ARGUMENT LIST-----"
	echo -e "\t -a \t Address of database"
	echo -e "\t -f \t *DANGEROUS!* Overwrites without user conformation"
	echo -e "\t -n \t Database Name"
	echo -e "\t -p \t Database Password"
	echo -e "\t -P \t Database Port (default is 3306)"
	echo -e "\t -u \t Database Username"

}
	
#Prep parameters	
force=FALSE
URL=""
userName=""
password=""
port=""
dbName=""
	
#Parse arguments
while getopts ":a:n:u:p:P:f" opt; do	
	#a - Address,n - DB Name, u - Username, p - Password, P - Port, f - Force
	case "$opt" in
		a) 
			URL=$OPTARG
			;; 
		n)
			dbName=$OPTARG
			;;
		u)
			userName=$OPTARG
			;;
		p)	
			password=$OPTARG
			;;
		f)	
			force=TRUE
			;;
		P)
			port=$OPTARG
			;;
		'?')
			display_help >&2 #Output to StdError
			exit 1
			;;
		
		\?) #Unexpected flag
			echo "Invalid option $OPTARG for help add -?" >&2
			exit 1
			;;
		
		:) 
			echo "No argument given for -$OPTARG" >&2
			exit 1
			;;
			
	esac
done

#Check we have all parameters needed
if [ -z "$URL" ] 
then
	printf "Database Address: " #Use printf so new newline is printed
	read URL
fi

if [ -z "$dbName" ]
then	
	printf "Database Name: "
	read dbName
fi

if [ -z "$userName" ]
then
	printf "Database Username: " 
	read userName
fi


if [ -z "$password" ]
then	
	printf "Enter database password \n"
	read -s password #-s flag stops password being echoed to console
	
fi

if [ -z "$port" ]
then
	printf "Database port (leave blank for 3306): "
	read port
	
	if [ -z "$port" ]
	then
		port=3306
	fi
	
fi

#Checked all parameters
#Now onto SQL - first open connection and test if alive
#echo mysql -B -h $URL --user="$userName" -D $dbName
echo "Testing connection...."
echo " "
mysql -B -h $URL --user="$userName" -D $dbName --password="$password" <<< "exit"


if [ $? -ne 0 ]
then
	echo "Could not connect to database - please check details and try again" >&2
	exit 2
fi

echo "Connection established"

do_query(){
	echo $1
	result=$(mysql -B -h $URL --user="$userName" -D $dbName --password="$password" <<< $1)
	echo "$result"
}

#DB connection established at this point next check for tables
string="show tables"
tables=$(do_query "$string")


# Adapted from http://superuser.com/questions/284187/bash-iterating-over-lines-in-a-variable
# Reads line by line using \n as field seperator

usersTableExists=FALSE
tasksTableExists=FALSE

while read -r table; do
	echo "Current Line: $table"
	
	#Checks if either tables exist in DB
	if [ "$table" = "tbl_tasks" ]
	then
		tasksTableExists=TRUE
		
	fi
	
	if [ "$table" = "tbl_users" ]
	then
	echo "found users table"
		usersTableExists=TRUE
	fi
	
done <<< "$tables"



#Inform and deal with this
if [ "$usersTableExists" = TRUE ]
then

	if [ "$force" = TRUE ] #If force is on skip question
	then
		echo "User table found - overwriting due to force flag"
		echo "Overwrite users - force"
	else
		#Prompt for conformation
		read -p "User table found - do you want to clear this? (THIS WILL DESTROY ALL USERS DATA) Y/N" -n 1 -r
	
		if [ $REPLY = "Y" ] || [ $REPLY = "y" ]
		then
			echo -e "\nTODO:Overwrite users"
		else 
			echo -e "\nSkipping users table"
		fi
	fi
	
else

	echo "TODO:Create users"
	
fi



if [ "$tasksTableExists" = TRUE ]
then

	if [ "$force" = TRUE ] #If force is on skip question
	then
		echo "Tasks table found - overwriting due to force flag"
		echo "Overwrite tasks - force"
		
	else
		#Prompt for conformation
		read -p "Tasks table found - do you want to clear this? (THIS WILL DESTROY ALL TASK DATA) Y/N" -n 1 -r
		if [ $REPLY = "Y" ] || [ $REPLY = "y" ]
		then
			echo -e "\nTODO: Overwrite tasks"
		else 
			echo -e "\nSkipping tasks table"
		fi
	fi

else 
	echo "TODO: Create tasks"
	
fi


	


	
			
		