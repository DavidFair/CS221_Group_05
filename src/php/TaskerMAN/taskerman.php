<?php
/**
 * TaskerMAN Main Interface - Tasks View
 * @author Oliver Earl, Tim Anderson
 *
 * This is the main Tasks view of the program. It uses modal windows stored in includes to provide functionality.
 * From here, it is possible to view the contents of the Tasks in the database, and have access to other Task
 * functionality, including viewing task elements, etc.
 *
 */
require('init.php');

/*
 * Authentication and Behaviours
 */

// Access only if logged in
if (!(isset($_SESSION['login_auth']))) { header('Location: index.php'); }

// Adding Elements After Gateway Redirection
if (isset($_POST['taskAdd'])) { header('Location: taskerman.php#addElements'); }
?>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <?php include('meta.php'); ?>
    <title><?php echo APP_NAME . ' ' . APP_VER; ?></title>
</head>
<body>
<?php
// Easter Egg
if ($debug)
{
    // Even if marquee isn't HTML5 compliant, we had fun with this
    echo '<marquee><img class="nigel" src="img/nigel.jpg" alt="Debug!"></marquee>';
}
?>
<!-- Container -->
<div class="container">
    <!-- Header -->
    <?php include('header.php'); ?>
    <!-- Main -->
    <table id="tasks">
        <thead>
        <tr>
            <th>Options</th>
            <th>Task ID</th>
            <th>Task Name</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Status</th>
            <th>Task Owner</th>
        </tr>
        </thead>
        <tbody>
        <?php
        try
        {
            // Retrieve all tasks
            $stmt = $pdo->prepare("SELECT * FROM tbl_tasks ORDER BY TaskID");
            $stmt->execute();

            // Print
            while($row = $stmt->fetch(PDO::FETCH_ASSOC))
            {
                $taskID = $row['TaskID']; // helps prevent some code repetition; primary key
                echo '<tr>'; ?>
                <td>
                    <select title="options" onChange="window.location.href=this.value" id="optionBar">
                        <option value="taskerman.php"> </option>
                        <option value="taskerman.php?id=<?php echo $taskID; ?>#openView">View Elements</option>
                        <option value="taskerman.php?extraElement=<?php echo $taskID; ?>#extraElement">Add Element</option>
                        <option value="elementDelete.php?id=<?php echo $taskID; ?>#deleteElement">Delete Elements</option>
                        <option value="taskerman.php?edit=<?php echo $taskID; ?>#editView">Edit Task</option>
                        <option value="taskDelete.php?id=<?php echo $taskID; ?>">Delete Task</option>
                    </select>
                </td> <?php

                echo '<td>'.$row['TaskID'].'</td>';
                echo '<td>'.$row['TaskName'].'</td>';
                echo '<td>'.$row['StartDate'].'</td>';
                echo '<td>'.$row['EndDate'].'</td>';
                echo '<td>'.convertStatus($row['Status']).'</td>';
                echo '<td>'.retrieveNames($row['TaskOwner'],$pdo).'</td>';
                echo '</tr>';
            }
        }
        catch (PDOException $ex)
        {
            errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
            die();
        }
        ?>
        </tbody>
    </table>
    <!-- Footer -->
    <?php include('footer.php'); ?>
</div>

<!-- Modal Windows -->
<!-- View Task -->
<?php include('taskView.php'); ?>

<!-- Add Task -->
<?php include('taskAdd.php'); ?>

<!-- Add Elements -->
<?php include('addElements.php'); ?>

<!-- Edit Task -->
<?php include('taskEdit.php'); ?>

<!-- Add Additional Element -->
<?php include('additionalElement.php') ?>

</body>
</html>

