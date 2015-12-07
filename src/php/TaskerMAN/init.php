<?php
/**
 * Initialisation Module
 * @author Oliver Earl
 *
 * This file should be included in every file in the program.
 * It really ties the room together.
 *
 **/

/*
** Settings and Configuration
*/

// Session Location
// TODO Change this to a more suitable location when we have a working server
session_save_path("tmp");
session_start();

// Configuration file
require_once('resources/config.php');

// Establish database connection
require_once('connect.php');

// Don't suppress errors when in debug mode
if (!($debug))
{
    ini_set("display_errors",1);
}

/*
** Global Static Functions
*/

/**
 *
 * Error Handling Function
 *
 **/
function errorHandler($ex,$errorType,$logfile,$currentTime)
{
    // Prepare error statement using passed information
    $toPrint = "\n" . $currentTime . " " . $errorType . " " .
        $ex . "\n";
    // Print to logfile
    file_put_contents($logfile, $toPrint, FILE_APPEND);
}

/**
 *
 * Time printing function
 *
 **/
function timePrint()
{
    // Format time and date and store
    $date = date('d-m-Y') . " " . time();
    // Return
    return $date;
}