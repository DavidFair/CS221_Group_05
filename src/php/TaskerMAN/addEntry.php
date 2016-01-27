<?php
require_once('init.php');
//Finding the last TaskID and adding one to it for the next task to be added
$stmt = $pdo->prepare("SELECT TaskID FROM tbl_tasks ORDER BY TaskID DESC LIMIT 1");
$stmt->execute();
$result = $stmt -> fetch(PDO::FETCH_ASSOC);
$taskID = $result['TaskID'];
$taskID++;
//echo $taskID;
echo $_POST["taskName"];
echo $_POST["taskAllocated"];
echo $_POST["startDate"];
echo $_POST["endDate"];
//preparing the statement to be added to the database and binding the variables
$stmt = $pdo->prepare("INSERT INTO tbl_tasks ('TaskID', 'TaskName', 'StartDate', 'EndDate', 'Status', 'TaskerOwner')
    VALUES (:taskID, :taskName, :startDate, :endDate, :status, :taskOwner)");
$stmt->bindParam(':taskID', $taskID);
$stmt->bindParam(':taskName', $_POST["taskName"]);