<?php
require_once('init.php');
    //Finding the last TaskID and adding one to it for the next task to be added
	$stmt = $pdo->prepare("SELECT TaskID FROM tbl_tasks ORDER BY TaskID DESC LIMIT 1");
	$stmt->execute();
	$result = $stmt -> fetch(PDO::FETCH_ASSOC);
    $taskID = $result['TaskID'];
    $taskID++;
    echo $taskID;
    //echo $_POST["taskName"];
    //echo $_POST["startDate"];
    //echo $_POST["endDate"];
    //echo convertStatus($_POST["taskAllocated"]);
    //echo $_POST["taskMember"];
    $taskDesclist = "";
    $more = true;
    $i = 1;
    while ($more){
        if ((isset($_POST['taskDesc_'.$i])) && ($_POST['taskDesc_'.$i] != "")){
            $taskDesclist .= $_POST['taskDesc_'.$i];
            $taskDesclist .= "<br />";
        }
        else { $more = FALSE;
        } $i++;
    }
    echo $taskDesclist;

    //preparing the statement to be added to the database and binding the variables
    $stmt = $pdo->prepare("INSERT INTO tbl_tasks ('TaskID', 'TaskName', 'StartDate', 'EndDate', 'Status', 'TaskerOwner')
    VALUES (:taskID, :taskName, :startDate, :endDate, :status, :taskOwner)");
    $stmt->bindParam(':taskID', $taskID);
    $stmt->bindParam(':taskName', $_POST["taskName"]);
    $stmt->bindParam(':startDate', $_POST["startDate"]);
    $stmt->bindParam(':endDate', $_POST["endDate"]);
    $stmt->bindParam(':status', $_POST["taskAllocated"]);
    $stmt->bindParam(':taskOwner', $_POST["taskMember"]);
    $stmt->execute();

    //$satement =$pdo->prepare("INSERT INTO tbl_elements ('TaskID, 'TaskDesc','TaskComments')")



// switching task status to a number so it can be added to the Database
function convertStatus($StatusNumber)
{
    switch ($StatusNumber) {
        case Adondoned:
            return '0';
            break;
        case Allocated:
            return '1';
            break;
        case Completed:
            return '2';
            break;
        default:
            return 'null';
    }
}