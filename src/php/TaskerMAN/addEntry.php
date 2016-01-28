<?php
require_once('init.php');

// Task ID needs to be the newest ID available
$taskID             = generateNewID($pdo);
$taskName           = $_SESSION['add_taskName'];
$startDate          = $_SESSION['add_startDate'];
$endDate            = $_SESSION['add_endDate'];
$status             = $_SESSION['add_taskStatus'];
$taskAllocated      = $_SESSION['add_taskAllocated'];
$numberOfElements   = $_SESSION['add_numberOfElements'];

// Enter into Task table of database
try
{
    $stmt = $pdo->prepare("INSERT INTO tbl_tasks ('TaskID', 'TaskName','StartDate','EndDate','Status','TaskOwner') VALUES (:taskID, :taskName, :startDate, :endDate, :status, :taskOwner)");

// Bind parameters and run

    $stmt->bindParam(':taskID',    $taskID);
    $stmt->bindParam(':taskName',  $taskName);
    $stmt->bindParam(':startDate', $startDate);
    $stmt->bindParam(':endDate',   $endDate);
    $stmt->bindParam(':status',    $status);
    $stmt->bindParam(':taskOwner', $taskAllocated);

 var_dump($taskID, $taskName, $startDate, $endDate, $status, $taskAllocated);
    $stmt->execute();
}
catch (PDOException $ex)
{
    errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
}
// Add elements
try
{
    for ($i = 0; $i > $numberOfElements; $i++)
    {
        $stmt = $pdo->prepare("INSERT INTO tbl_elements ('TaskDesc', 'TaskComments', 'TaskID') VALUES (:description, :comment, :taskid);");

        $stmt->bindParam(':description',    $_SESSION['taskDesc_'.$i]);
        $stmt->bindParam(':comment',        $_SESSION['taskComment_'.$i]);
        $stmt->bindParam(':taskid',         $taskID);
        $stmt->execute();
    }
}
catch (PDOException $ex)
{
    errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
}


function generateNewID($db)
{
    $stmt = $db->prepare("SELECT * FROM tbl_tasks ORDER BY TaskID DESC LIMIT 1");
    $stmt->execute();
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    $taskID = $result['TaskID'];

    // Increment it
    $taskID++;
    return $taskID;
}
