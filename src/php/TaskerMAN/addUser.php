<?php
/**
 * TaskerMAN - Logic used for adding Users to User table
 *
 * @author Oliver Earl
 */
require_once('init.php');

// Login check
if (!($_SESSION['login_auth']))
{
    session_unset();
    session_destroy();
    header('Location: index.php');
}

// Sanitise (assuming they've been validated)
$email =        filter_var($_POST['add_email'],FILTER_SANITIZE_EMAIL);
$firstName =    filter_var($_POST['add_firstName'],FILTER_SANITIZE_STRING);
$lastName =     filter_var($_POST['add_lastName'],FILTER_SANITIZE_STRING);
$isManager =    filter_var($_POST['add_isManager'],FILTER_SANITIZE_NUMBER_INT);

// Check if the $email value exists already in the database
try
{
    $stmt = $pdo->prepare("SELECT * FROM tbl_users WHERE Email = :email");
    $stmt->bindParam(':email', $email);
    $stmt->execute();

    // Check, if it exists, error flag and give up
    $output = $stmt->fetch(PDO::FETCH_NUM);
    if ($output > 0) {
        $_SESSION['emailExists'] = true;
        header('Location: users.php');
    }
}
catch (PDOException $ex)
{
    errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
    header('Location: users.php');
}

// Time to enter these into the database
try
{
    var_dump($email, $firstName, $lastName, $isManager);

    // $stmt = $pdo->prepare("INSERT INTO tbl_users (Email,FirstName,LastName,IsManager) VALUES (:email, :firstname, :lastname, :ismanager);");
    $stmt = $pdo->prepare("INSERT INTO tbl_users (Email,FirstName,LastName,IsManager) VALUES (:email, :firstName, :lastName, :isManager)");
    $stmt->bindParam(':email',      $email);
    $stmt->bindParam(':firstName',  $firstName);
    $stmt->bindParam(':lastName',   $lastName);
    $stmt->bindParam(':isManager',  $isManager);

    $stmt->execute();
}
catch (PDOException $ex)
{
    errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
    header('Location: users.php');
}

// Redirect
header('Location: users.php');