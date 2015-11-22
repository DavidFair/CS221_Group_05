<?php
//======================================================================
// Database Connect Module
//======================================================================
/** 
*	PDO Connect module for TaskerMAN
*	@author Oliver Earl
*	
*	This file should be included into init.php to provide access to the database
*	Variable should be accessible program wise under $pdo
*
*	errorHandler is a global error handling method that has been added to init.php
**/

// Begin configuring database information
$dbconf = parse_ini_file('database.ini');

// Configure PDO using parsed settings
$pdo = configureDatabase($dbconf['dbhost'],$dbconf['dbname'],$dbconf['username'],$dbconf['password']);

function configureDatabase($hostname,$dbname,$username,$password); {
	try {
		// Declare data source name
		$dsn = "mysql:host=".$hostname.";dbname=".$dbname;
		// Establish pdo
		$db = new PDO($dsn,$username,$password);
		// Set error mode exceptions
		$db -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		// Provide db access
		return $db;
	} catch (PDOException ex) {
		// Call error handling routine
		errorHandler($ex -> getMessage(),"Database Error");
		// Terminate execution
		die();
	}
}
