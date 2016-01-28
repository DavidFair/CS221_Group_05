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
// TODO add sanitisation

$_SESSION['add_taskName']           = $_POST['add_taskName'];
$_SESSION['add_taskAllocated']      = $_POST['add_taskAllocated'];
$_SESSION['add_startDate']          = $_POST['add_startDate'];
$_SESSION['add_endDate']            = $_POST['add_endDate'];
$_SESSION['add_taskStatus']         = $_POST['add_taskStatus'];
$_SESSION['add_numberOfElements']   = $_POST['add_numberOfElements'];

// Set and redirect
header('Location: taskerman.php#addElements');