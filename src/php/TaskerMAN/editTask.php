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
$taskID =           filter_var($_POST['edit_taskID'],FILTER_SANITIZE_NUMBER_INT);
$taskName =         filter_var($_POST['edit_taskName'],FILTER_SANITIZE_STRING);
$taskAllocated =    filter_var($_POST['edit_taskAllocated'],FILTER_SANITIZE_EMAIL);
$startDate =        $_POST['edit_startDate'];
$endDate =          $_POST['edit_endDate'];
$taskStatus =       filter_var($_POST['edit_taskStatus'],FILTER_SANITIZE_NUMBER_INT);

// Modify SQL
try
{
    $stmt = $pdo->prepare("UPDATE tbl_tasks SET TaskName = :taskname, TaskOwner = :taskallocated, StartDate = :startdate, EndDate = :enddate, Status = :taskstatus WHERE TaskID = :taskid");
    $stmt->bindParam(':taskname',       $taskName);
    $stmt->bindParam(':taskallocated',  $taskAllocated);
    $stmt->bindParam(':startdate',      $startDate);
    $stmt->bindParam(':enddate',        $endDate);
    $stmt->bindParam(':taskstatus',     $taskStatus);
    $stmt->bindParam(':taskid',        $taskID);
    $stmt->execute();
}
catch (PDOException $ex)
{
    errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
    header('Location: taskerman.php');
}
header('Location: taskerman.php');