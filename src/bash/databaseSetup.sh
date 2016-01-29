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
	echo -e "\t -t \t Populate table with test data"

}
	
#Prep parameters	
force=FALSE
URL=""
userName=""
password=""
port=""
dbName=""
testData=FALSE
	
#Parse arguments
while getopts ":a:n:u:p:P:ft" opt; do	
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
		t)
			testData=TRUE
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

if [ "$testData" = FALSE ]
then 
	read -p "Do you want to populate database with test data Y/N" -n 1 -r
	if [ $REPLY = "Y" ] || [ $REPLY = "y" ]
	then 
		testData=TRUE
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

echo -e "Connection established\n"

do_query(){
	#echo $1
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
	#echo "Current Line: $table"
	
	#Checks if either tables exist in DB
	if [ "$table" = "tbl_tasks" ]
	then
		tasksTableExists=TRUE
		
	fi
	
	if [ "$table" = "tbl_users" ]
	then
		usersTableExists=TRUE
	fi
	
	if [ "$table" = "tbl_elements" ]
	then
		elementsTableExists=TRUE
	fi
	
done <<< "$tables"





#Inform and deal with this
#First tasks table due to FK

if [ "$elementsTableExists" = TRUE ]
then
	dropElements=FALSE

	if [ "$force" = TRUE ] #If force is on skip question
	then
		echo "Task Elements table found - overwriting due to force flag"
		dropElements=TRUE
		
	else
		#Prompt for conformation
		read -p "Task Elements table found - do you want to clear this? (THIS WILL DESTROY ALL TASK DATA) Y/N" -n 1 -r
		if [ $REPLY = "Y" ] || [ $REPLY = "y" ]
		then
			dropElements=TRUE
		else 
			echo -e "\nSkipping tasks table"
			skipElements=TRUE
		fi
	fi
	
	if [ "$dropElements" = TRUE ]
	then
		string="DROP TABLE \`tbl_elements\`;"
		echo -e "\nDropping Task Elements Table"
		do_query "$string"
	fi
	
fi




if [ "$tasksTableExists" = TRUE ]
then
	dropTasks=FALSE

	if [ "$force" = TRUE ] #If force is on skip question
	then
		echo "Tasks table found - overwriting due to force flag"
		dropTasks=TRUE
		
	else
		#Prompt for conformation
		read -p "Tasks table found - do you want to clear this? (THIS WILL DESTROY ALL TASK DATA) Y/N" -n 1 -r
		if [ $REPLY = "Y" ] || [ $REPLY = "y" ]
		then
			dropTasks=TRUE
		else 
			echo -e "\nSkipping tasks table"
			skipTasks=TRUE
		fi
	fi
	
	if [ "$dropTasks" = TRUE ]
	then
		string="DROP TABLE \`tbl_tasks\`;"
		echo -e "\nDropping Tasks Table"
		do_query "$string"
	fi
	
fi



if [ "$usersTableExists" = TRUE ]
then
	dropUsers=FALSE

	if [ "$force" = TRUE ] #If force is on skip question
	then
		echo "User table found - overwriting due to force flag"
		dropUsers=TRUE
	else
		#Prompt for conformation
		read -p "User table found - do you want to clear this? (THIS WILL DESTROY ALL USERS DATA) Y/N" -n 1 -r
	
		if [ $REPLY = "Y" ] || [ $REPLY = "y" ]
		then
			dropUsers=TRUE
		else 
			echo -e "\nSkipping users table"
			skipUsers=TRUE
		fi
	fi
	
	
	if [ "$dropUsers" = TRUE ]
	then
		string="DROP TABLE \`tbl_users\`;"
		echo -e "\nDropping Users Table"
		do_query "$string"
	fi

fi




#Finally create tables

#Users table

if [ "$skipUsers"  != TRUE ]
then
string="CREATE TABLE \`tbl_users\` ( \`Email\` VARCHAR(45) NOT NULL, \`FirstName\` VARCHAR(15) NOT NULL, \`LastName\` VARCHAR(15) NOT NULL, \`IsManager\` INT NOT NULL, PRIMARY KEY (\`Email\`));"
  echo "Creating Users Table"
  do_query "$string"
  
 string="INSERT INTO \`tbl_users\` (\`Email\`, \`FirstName\`, \`LastName\`, \`isManager\`) VALUES ('manager@example.com', 'Aa', 'Bb', '1');"
 echo "Adding manager@example to users table"
 do_query "$string"
  
fi
  
#Tasks Table

if [ "$skipTasks" != TRUE ]
then
string="CREATE TABLE \`tbl_tasks\` ( \`TaskID\` INT NOT NULL AUTO_INCREMENT, \`TaskName\` VARCHAR(45) NOT NULL, \`StartDate\` DATE NOT NULL, \`EndDate\` DATE NOT NULL, \`Status\` INT NOT NULL, \`TaskOwner\` VARCHAR(45) NULL, PRIMARY KEY (\`TaskID\`), INDEX \`TaskOwner_idx\` (\`TaskOwner\` ASC), CONSTRAINT \`TaskAssigned\` FOREIGN KEY (\`TaskOwner\`) REFERENCES \`tbl_users\` (\`Email\`) ON DELETE NO ACTION ON UPDATE NO ACTION);"
	echo "Creating Tasks Table"
	do_query "$string"
fi

#TaskElements
 
if [ "$skipElements" != TRUE ]
then 
 string="CREATE TABLE \`tbl_elements\` (\`Index\` INT NOT NULL AUTO_INCREMENT,\`TaskDesc\` VARCHAR(45) NOT NULL,\`TaskComments\` VARCHAR(45) NULL,\`TaskID\` INT NULL,PRIMARY KEY (\`Index\`),INDEX \`TaskRef_idx\` (\`TaskID\` ASC),CONSTRAINT \`TaskRef\`FOREIGN KEY (\`TaskID\`)REFERENCES \`tbl_tasks\` (\`TaskID\`)ON DELETE NO ACTION ON UPDATE NO ACTION);"
	echo "Creating Task Elements Table"
	do_query "$string"
fi

#Populate with test data if set
if [ "$testData" = TRUE ]
then
	
	echo "Populating users table with test data"
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('ole4@aber.ac.uk', 'Oliver', 'Earl', '1');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('daf5@aber.ac.uk', 'David', 'Fairbrother', '1');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('jod32@aber.ac.uk', 'Joshua', 'Doyle', '1');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('bed19@aber.ac.uk', 'Ben', 'Dudley', '1');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('tma1@aber.ac.uk', 'Tim', 'Anderson', '1');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('mac81@aber.ac.uk', 'Maurice', 'Corriette', '1');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('jee17@aber.ac.uk', 'Jonothan ', 'Englund', '1');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('lif5@aber.ac.uk', 'Liam ', 'Fitzgerald', '1');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('alw21@aber.ac.uk', 'Alex', 'Webb', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('kir12@aber.ac.uk', 'Kimmit', 'Rai', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('joc13@aber.ac.uk', 'Johnny', 'Chan', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('sar14@aber.ac.uk', 'Sanjaya', 'Ranasinghe', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('mal15@aber.ac.uk', 'Max', 'Limbu', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('yil16@aber.ac.uk', 'Yiota', 'Laperta', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('jec17@aber.ac.uk', 'Jesse ', 'Chen', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('dia18@aber.ac.uk', 'Dionysis ', 'Arvanitis', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('wej19@aber.ac.uk', 'Wei', 'Jen', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('kog20@aber.ac.uk', 'Kostas', 'Gasteratos', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('ali21@aber.ac.uk', 'Alvin', 'Itawad', '0');"
	do_query "$string"
	
	string="INSERT INTO tbl_users (Email, FirstName, LastName, IsManager) VALUES ('anc22@aber.ac.uk', 'Atastasia', 'Chatzeli', '0');"
	do_query "$string"
	
	string="UPDATE tbl_users SET FirstName='Nigel', LastName='Hardy' WHERE Email='manager@example.com';"
	do_query "$string"
	
	
	echo "Populating tasks table with test data"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('3', 'Design specifications', '2015-11-20', '2015-11-27', '1', 'jee17@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('4', 'Test specification', '2015-11-20', '2015-11-29', '0', 'mac81@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('5', 'JDBC spike work', '2015-11-01', '2015-11-07', '1', 'bed19@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('6', 'Mysql spikework', '2015-11-01', '2015-11-08', '1', 'lif5@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('7', 'Swing spikework', '2015-11-02', '2015-11-08', '0', 'jod32@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('8', 'prototype CLI implementation', '2015-12-01', '2015-12-16', '1', 'jod32@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('9', 'Taskerman prototype implementation', '2015-12-01', '2015-12-16', '1', 'ole4@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('10', 'QA documentatoin', '2015-11-01-01', '2015-11-09', '1', 'ole4@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('11', 'Group report', '2016-01-20', '2016-02-15', '2', 'daf5@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('12', 'Test report ', '2016-01-10', '2016-02-15', '1', 'mac81@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('13', 'Status report', '2016-02-22', '2016-02-29', '1', 'ali21@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('14', 'Analysis report', '2016-02-18', '2016-02=28', '0', 'anc22@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('15', 'Risk analysis', '2015-12-05', '2015-12-10', '1', 'daf5@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('16', 'PHP implementation', '2016-01-25', '2016-01-29', '1', 'ole4@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('17', 'CLI/MAN testing', '2016-01-27', '2016-01-29', '0', 'mac81@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('19', 'Java gui debugging', '2016-01-27', '2016-01-29', '1', 'jod32@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('20', 'Java db debugging', '2016-01-27', '2016-01-29', '0', 'daf5@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('21', 'Spreadsheet analysis', '2016-02-10', '2016-02-18', '1', 'ali21@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('22', 'Junit testing', '2016-01-25', '2016-01-29', '0', 'kog20@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('23', 'Javadoc commenting', '2016-01-25', '2016-01-29', '1', 'tma1@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('24', 'Phpunit testing', '2016-02-01', '2016-02-10', '0', 'joc13@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('25', 'Javascript validation', '2016-01-27', '2016-01-29', '1', 'alw21@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('26', 'Maintenance manual', '2016-01-29', '2016-02-15', '1', 'dia18@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('27', 'QA inspection of testing documents', '2016-02-01', '2016-02-01', '1', 'mal15@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('28', 'Project log documentation', '2016-02-05', '2016-02-15', '0', 'wej19@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('29', 'Shell scripting', '2015-12-21', '2016-01-29', '1', 'yil16@aber.ac.uk');"
	do_query "$string"
	
	string="INSERT INTO tbl_tasks (TaskID, TaskName, StartDate, EndDate, Status, TaskOwner) VALUES ('30', 'Project packaging', '2016-01-25', '2016-01-29', '2', 'kir12@aber.ac.uk');"
	do_query "$string"
	
fi
	
	
	
	