<?php
//======================================================================
// TaskerMAN Initialisation Module
//======================================================================
/** 
*	PDO Initialisation Module
*	@author Oliver Earl
*	
*	This file should be included into every file in the program.
*	It really ties the room together.
*
**/

/*
** Settings and Configuration
*/
// Session Location
session_save_path("tmp");
// Start session
session_start();

// Program Version
$version = '0.1'

// Is this version of the program in development?
$development = false;

// Set timezone for the program - without this we can get errors
date_default_timezone_set("Europe/London");

// Disable errors caused by Aberystwyth University servers
error_reporting(E_ALL ^ E_NOTICE);

// Declare logfile to be used by TaskerMAN
ini_set("error_log","error.log");

// Error suppression when program is not in development
// You do not want users to be able to see errors, fail silently
if ($development) {
	ini_set("display_errors",0);
	ini_set("log_errors", 1);
}

// Establish link to PDO
require_once('connect.php');

/*
** Global Functions
*/
// Error handling function
function errorHandler($ex, $errorType) {
	// Logfile
	$log = "error.log";
	// Prepare log statement
	$toPrint = "\n".timePrint()." ".$errorType." "$ex."\n";
	// Log
	file_put_contents($log, $toPrint, FILE_APPEND);
}

// Time printing function
function timePrint() {
	// Time and date
	$date = date('d-m-Y') . " " . time();
	// Return concatenated string
	return $date;
}
