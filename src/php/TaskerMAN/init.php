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

// Database connection
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
 * Error Handling Function - The reason we have this is because debugging in PHP is a massive pain in the behind
 *
 **/
function errorHandler($ex,$errorType,$logfile,$currentTime)
{
    // Prepare error statement using passed information
    $toPrint = "\n" . $currentTime . " " . $errorType . " " .
        $ex . "\n";
    // Print to logfile
    file_put_contents($logfile, $toPrint, FILE_APPEND);
    echo '<p>' . $errorType . '</p><br/>';
    if ($debug = true)
    {
        echo '<p><em>' . $ex . '</em></p>';
    }
    else
    {
        echo '<p><em>Check the log for more details.</em></p>';
    }
    echo '<hr/>';
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

function convertStatus($retrievedInteger)
{
    switch($retrievedInteger)
    {
        case 0:
            return 'Abandoned';
            break;
        case 1:
            return 'Allocated';
            break;
        case 2:
            return 'Completed';
            break;
        default:
            return 'Unknown';
    }
}

function retrieveNames($email, $database)
{
    try
    {
        $stmt = $database->prepare("SELECT FirstName, LastName from tbl_users WHERE Email = :email");
        $stmt->bindParam(':email', $email);
        $stmt->execute();

        // Retrieve output and check
        $output = $stmt->fetch(PDO::FETCH_ASSOC);

        if (isset($output['FirstName']) & isset($output['LastName'])) // Smelly
        {
            $fullName = $output['FirstName'] . ' ' . $output['LastName'];
            return $fullName;
        }
        else
        {
            return $email;
        }
    } catch (PDOException $ex)
    {
        errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
        return $email;
    }
}

function convertIsManager($managerInteger)
{
    switch($managerInteger)
    {
        case 0:
            return 'No';
            break;
        case 1:
            return 'Yes';
            break;
        default:
            return 'Unknown';
    }
}