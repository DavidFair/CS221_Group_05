<?php
/**
 * Configuration Module
 * @author Oliver Earl
 *
 * This file contains constants and other parameters used in the program.
 *
 * Created by PhpStorm
 * User: Oliver
 * Date: 16/11/2015
 * Time: 00:23
 *
 **/

/*
** Debug Mode
*/

// Is this version of the program in development mode?
$debug = true;

/*
** Global Constants
*/
define("APP_NAME",  "TaskerMAN");
define("APP_VER",   "0.1");
define("APP_DESC",  APP_NAME . " Task Managing Software");
define("GROUP",     "Group 5");
define("COPYRIGHT", "Aberystwyth University");

/*
** Timezone Settings
*/
date_default_timezone_set("Europe/London");

/*
** Error Reporting Settings
*/
define("LOGFILE",   "error.txt");
ini_set("error_log", LOGFILE);
ini_set("log_errors", 1);

// Disable errors caused by Aberystwyth University servers
error_reporting(E_ALL ^ E_NOTICE);

/*
** Path Array
*/
$paths = array
(
    "include"   =>  $_SERVER["DOCUMENT_ROOT"] . "/include",
    "resources" =>  $_SERVER["DOCUMENT_ROOT"] . "/resources",
    "css"       =>  $_SERVER["DOCUMENT_ROOT"] . "/css",
    "js"        =>  $_SERVER["DOCUMENT_ROOT"] . "/js",
    "img"       =>  $_SERVER["DOCUMENT_ROOT"] . "/img"
);
