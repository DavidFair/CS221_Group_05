#!/bin/bash

# Copyright (c) 2015 Aberystwyth University
# All rights reserved
#
# Updates MySQL database to contain correct tables
# Provides wrapper for mysql and automates commands
# Author: David (daf5)

#Debug mode
set -x

# Test for number of args


if [ $# -eq 0 ]
then
	echo "Note: No arguments specified, for a list of arguments which can be passed run with -? argument"
fi

display_help(){
	echo "Creates table structure for TaskerSRV on specified database."
	echo ""

	echo -e "\t -----ARGUMENT LIST-----"
	echo -e "\t -i \t Full Installation path"
	echo -e "\t -r \t *DANGEROUS!* Remove existing files"

}

#Prep parameters	
installPath=""
installSource=""
removeFiles=FALSE

#Parse arguments
while getopts ":i:r:" opt; do	
	case "$opt" in
		i)
			installPath=$OPTARG
			;;
		r)
			removeFiles=TRUE
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
if [ -z "$installPath" ] 
then
	echo "Please enter the full install path"
	read installPath
fi

if [ $removeFiles = "TRUE" ]
then
	echo "Removing files"
	rm -r $installPath
fi

echo "Making folder path"
mkdir -p $installPath 

echo "Copying files"
cp "TaskerMAN" $installPath -r

cd $installPath
find -name "*.php" -exec chmod 600 {} \;
find -name "*.css" -exec chmod 644 {} \;

