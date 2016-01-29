<?php
/**
 * TaskerMAN Add Element
 *
 * Logic used for adding an extra element to a task.
 *
 * @author Oliver Earl, Jonathan Englund
 */
require_once('init.php');

// Login check
if (!($_SESSION['login_auth']))
{
    session_unset();
    session_destroy();
    header('Location: index.php');
}

// Some sanitisation but not much is necessary
$description =      filter_var($_POST['taskDesc'],FILTER_SANITIZE_STRING);
$comment =          filter_var($_POST['taskComment'],FILTER_SANITIZE_STRING);
$taskID =           filter_var($_POST['extra_taskID'],FILTER_SANITIZE_NUMBER_INT);


// You don't have to worry about the autokey, just add to the database
try
{
    $stmt = $pdo->prepare("INSERT INTO tbl_elements (TaskDesc, TaskComments, TaskID) VALUES (:description, :comments, :taskid)");
    $stmt->bindParam(':description',$description);
    $stmt->bindParam(':comments',$comment);
    $stmt->bindParam(':taskid',$taskID);
    $stmt->execute();
}
catch (PDOException $ex)
{
    errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
    header('Location: taskerman.php');
}
// Redirect
header('Location: taskerman.php');