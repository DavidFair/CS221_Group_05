<?php

// First sanitise the input
if (isset($_GET['id']))
{
    if (filter_var($_GET['id'],FILTER_SANITIZE_NUMBER_INT))
    {
        // Clean, we can use that number and retrieve data
        $taskID = $_GET['id'];

        // SQL retrieve data
        $stmt = $pdo->prepare("SELECT * from tbl_tasks WHERE TaskID = :taskID");
        $stmt->bindParam(':taskID',$taskID);
        $stmt->execute();

        // Retrieve and prepare for output
        $output = $stmt->fetch(PDO::FETCH_ASSOC);
    } else
    {
        // Not a number or maybe something odd, refresh the page
        header('Location: taskerman.php');
    }
}
?>
<div id="openView" class="modalWindow">
    <div>
        <a href="taskerman.php" title="Close" class="close">X</a>
        <fieldset id="info_box">
            <div class="modal">
                <label for="taskID">Task ID</label>
                <input name="taskID" type="text" value="<?php echo $output['TaskID']; ?>" readonly id="viewInput" /><br/>

                <label for="taskName">Task Name</label>
                <input name="taskName" type="text"  value="<?php echo $output['TaskName']; ?>" readonly id="viewInput" /><br/>

                <label for="taskStatus">Status</label>
                <input name="taskStatus" type="text"
                       value="<?php echo convertStatus($output['TaskName']); ?>" readonly id="viewInput" /><br/>

                <label for="assignedTaskMember">Allocated Task Member</label>
                <input name="assignedTaskMember" type="text"
                       value="<?php echo retrieveNames($output['TaskOwner'],$pdo); ?>" readonly id="viewInput" /><br/>

                <label for="startDate">Start Date</label>
                <input name="startDate" type="text" value="<?php echo $output['StartDate']; ?>" readonly id="viewInput" /><br/>

                <label for="endDate">End Date</label>
                <input name="endDate" type="text" value="<?php echo $output['EndDate']; ?>" readonly id="viewInput" /><br/>
            </div>
            <div class="modal">
                <table class="modal">
                    <thead>
                    <tr>
                        <th>Element ID</th>
                        <th>Description</th>
                        <th>Comments</th>
                    </tr>
                    </thead>
                    <tbody>
                    <?php
                    // Retrieve element data
                    try
                    {
                        $stmt = $pdo->prepare("SELECT * FROM tbl_elements WHERE TaskID = :id");
                        $stmt->bindParam(':id',$taskID);
                        $stmt->execute();

                        // Print
                        while($row = $stmt->fetch(PDO::FETCH_ASSOC))
                        {
                            echo '<tr>';
                            echo '<td>'.$row['Index'].'</td>';
                            echo '<td>'.$row['TaskDesc'].'</td>';
                            echo '<td>'.$row['TaskComments'].'</td>';
                            echo '</tr>';
                        }
                    } catch (PDOException $ex)
                    {
                        // Handle error first
                        errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
                        // Generate static objects
                        ?>
                        <tr>
                            <td>Test</td>
                            <td>Test</td>
                        </tr>
                    <?php } ?>
                    </tbody>
                </table>
            </div>
        </fieldset>
    </div>
</div>
