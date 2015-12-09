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
$pdo = configureDatabase($db_config);

/**
 * Database Establish Function utilising PDO for MySQL
 * TODO PHPDoc
 * @param $conf - The function is passed an array containing database connection information
 * @return PDO - Returns a PDO instance for connecting to the database
 * @resources http://www.stackoverflow.com/tags/pdo/info - Very useful
 * @resources CS25010 Advanced PHP slides provide a great overview of PDO
 */
function configureDatabase($conf)
{
    try
    {
        // Declare data source name
        $dsn = "mysql:host=" . $conf['hostname'] . ";dbname=" . $conf['dbname'];
        // Instantiate PDO object
        $pdo = new PDO($dsn, $conf['username'], $conf['password']);
        // Set error mode exceptions for PDO
        $pdo -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        // Return PDO for access
        return $pdo;
    }
    catch (PDOException $ex)
    {
        // Call error handler and die
        errorHandler($ex->getMessage(), "PDO Database Error", LOGFILE, timePrint());
        die();
    }
}
