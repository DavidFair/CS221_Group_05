<?php
require_once('init.php');

// Login check
if (!($_SESSION['login_auth']))
{
    session_unset();
    session_destroy();
    header('Location: index.php');
}

// Sanitise (assuming they've been validated)
$email =        filter_var($_POST['edit_email'],FILTER_SANITIZE_EMAIL);
$firstName =    filter_var($_POST['edit_firstName'],FILTER_SANITIZE_STRING);
$lastName =     filter_var($_POST['edit_lastName'],FILTER_SANITIZE_STRING);
$isManager =    filter_var($_POST['edit_isManager'],FILTER_SANITIZE_NUMBER_INT);
$originalID =   $_POST['edit_originalID'];

// Modify SQL
try
{
    $stmt = $pdo->prepare("UPDATE tbl_users SET FirstName = :firstname, LastName = :lastname, IsManager = :ismanager WHERE Email = :email");
    $stmt->bindParam(':email',      $email);
    $stmt->bindParam(':firstname',  $firstName);
    $stmt->bindParam(':lastname',   $lastName);
    $stmt->bindParam(':ismanager',  $isManager);
    $stmt->execute();
}
catch (PDOException $ex)
{
    errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
    header('Location. users.php');
}

header('Location: users.php');