<?php
require_once('init.php');

// Task ID needs to be the newest ID available
$taskID         = generateNewID($pdo);
$taskName       = $_SESSION['add_taskName'];
$startDate      = $_SESSION['add_startDate'];
$endDate        = $_SESSION['add_endDate'];
$status         = $_SESSION['add_taskStatus'];
$taskAllocated  = $_SESSION['add_taskAllocated'];

// Enter into Task table of database
$stmt = $pdo->prepare("INSERT INTO tbl_tasks ('TaskID', 'TaskName','StartDate','EndDate','Status','TaskOwner')
    VALUES (:taskID, :taskName, :startDate, :endDate, :status, :taskOwner)");

// Bind parameters and run

$stmt->bindParam(':taskID',    $taskID);
$stmt->bindParam(':taskName',  $taskName);
$stmt->bindParam(':startDate', $startDate);
$stmt->bindParam(':endDate',   $endDate);
$stmt->bindParam(':status',    $status);
$stmt->bindParam(':taskOwner', $taskAllocated);

$stmt->execute();

// Add elements
$counter = 1;
while(isset($_SESSION['taskDesc_'.$counter]))
{

}

function generateNewID($db)
{
    $stmt = $db->prepare("SELECT TaskID FROM tbl_tasks ORDER BY TaskID LIMIT 1");
    $stmt->execute();
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    $taskID = $result['TaskID'];

    // Increment it
    $taskID++;
    return $taskID
}
