<?php
/**
 *   PDO Connect module for TaskerMAN
 *   @author Oliver Earl
 *
 *   This file should be included into init.php to provide access to the database
 *   Accessible program-wide under $pdo (injected where possible)

 **/
// TaskerSRV -- Not good practice but not a huge deal in this particular scenario
$hostname = "db.dcs.aber.ac.uk";
$dbName = "csgp_5_15_16";
$username = "csgpadm_5";
$password = "906BnQjD";

// Activate database connection, available wherever $pdo is injected
$pdo = connect($hostname,$dbName,$username,$password);

function connect($hostname,$dbName,$username,$password)
{
    try
    {
        // Data source name
        $dsn = 'mysql:host=' . $hostname . ';dbname=' . $dbName;
        // Establishing the database connection
        $pdo = new PDO($dsn,$username,$password);
        // Set PDO modes
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        // Pass database back to whatever called it
        return $pdo;
    }
    catch (PDOException $ex)
    {
        errorHandler($ex,"PDO Database Error",LOGFILE,timePrint());
        die();
    }
}