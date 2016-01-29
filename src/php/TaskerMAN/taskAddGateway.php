<?php
require_once('init.php');
// Login check
if (!($_SESSION['login_auth']))
{
    session_unset();
    session_destroy();
    header('Location: index.php');
}

// Receive posted information - it has been validated already

$_SESSION['add_taskName']           = filter_var($_POST['add_taskName'],FILTER_SANITIZE_STRING);
$_SESSION['add_taskAllocated']      = filter_var($_POST['add_taskAllocated'], FILTER_SANITIZE_EMAIL);
$_SESSION['add_startDate']          = $_POST['add_startDate'];
$_SESSION['add_endDate']            = $_POST['add_endDate'];
$_SESSION['add_taskStatus']         = filter_var($_POST['add_taskStatus'],FILTER_SANITIZE_NUMBER_INT);
$_SESSION['add_numberOfElements']   = filter_var($_POST['add_numberOfElements'],FILTER_SANITIZE_NUMBER_INT);

// Set and redirect
header('Location: taskerman.php#addElements');