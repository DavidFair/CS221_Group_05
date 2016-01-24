<?php
/**
 *   PDO Connect module for TaskerMAN
 *   @author Oliver Earl
 *
 *   This file should be included into init.php to provide access to the database
 *   Accessible program-wide under $pdo
 *  ░░░░░░░░░░░░░░░░░░░░░
 *  ░░░░░░░░░░░░▄▀▀▀▀▄░░░
 *  ░░░░░░░░░░▄▀░░▄░▄░█░░
 *  ░▄▄░░░░░▄▀░░░░▄▄▄▄█░░
 *  █░░▀▄░▄▀░░░░░░░░░░█░░
 *  ░▀▄░░▀▄░░░░█░░░░░░█░░
 *  ░░░▀▄░░▀░░░█░░░░░░█░░
 *  ░░░▄▀░░░░░░█░░░░▄▀░░░
 *  ░░░▀▄▀▄▄▀░░█▀░▄▀░░░░░
 *  ░░░░░░░░█▀▀█▀▀░░░░░░░
 *  ░░░░░░░░▀▀░▀▀░░░░░░░░
 **/

// Activate database connection, available wherever $pdo is injected
$pdo = Database::connect();

class Database
{
    // TaskerSRV
    /* private static $hostname = "db.dcs.aber.ac.uk";
    private static $dbName = "csgp_5_15_16";
    private static $username = "csgpadm_5";
    private static $password = "906BnQjD"; */

    // Developer's MySQL
    private static $hostname = "db.dcs.aber.ac.uk";
    private static $dbName = "ole4";
    private static $username = "ole4";
    private static $password = "minicoopers123";


    /**
     * Database Establish Function utilising PDO for MySQL
     * TODO PHPDoc
     * @param $conf - The function is passed an array containing database connection information
     * @return PDO - Returns a PDO instance for connecting to the database
     * @resources http://www.stackoverflow.com/tags/pdo/info - Very useful
     * @resources CS25010 Advanced PHP slides provide a great overview of PDO
     */
    public static function connect()
    {
        try
        {
            // Data source name
            $dsn = 'mysql:host=' . self::$hostname . ';dbname=' . self::$dbName;
            // Establishing the database connection
            $pdo = new PDO($dsn,self::$username,self::$password);
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
}