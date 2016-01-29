<?php
// Sanitise and only run if we have input
if (isset($_GET['edit']))
{
    if (filter_var($_GET['edit'],FILTER_SANITIZE_NUMBER_INT))
    {
        // Clean, retrieve
        $taskID = $_GET['edit'];

        try
        {
            $stmt = $pdo->prepare("SELECT * from tbl_tasks WHERE TaskID = :taskid");
            $stmt->bindParam(':taskid',$taskID);
            $stmt->execute();

            // Retrieve, prepare
            $output = $stmt->fetch(PDO::FETCH_ASSOC);

            // Store
            $taskID         = $output['TaskID'];
            $taskName       = $output['TaskName'];
            $startDate      = $output['StartDate'];
            $endDate        = $output['EndDate'];
            $status         = $output['Status'];
        }
        catch (PDOException $ex)
        {
            errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
            header('Location: taskerman.php');
        }
    } else
    {
        header('Location: taskerman.php');
    }
}
?>
<div id="editView" class="modalWindow">
    <div>
        <script src="js/entryValidation.js"></script>
        <form name="taskEdit" id="taskEdit" action="editTask.php" method="POST" onsubmit="return validateTaskEdit()">
            <fieldset class="info_box">
                <a href="taskerman.php" title="Close" class="close">X</a>
                <input type="hidden" id="edit_taskID" name="edit_taskID" value="<?php echo $taskID; ?>"/>
                <label for="edit_taskName" class="titles">Task Name:</label>
                <input value="<?php echo $taskName; ?>" name="edit_taskName" id="edit_taskName" type="text" class="addInput" maxlength="45" required />
                <br/>

                <label for="edit_taskAllocated" class="titles">Allocated User:</label>
                <?php
                // Fill users drop-down box using database query
                try
                {
                    $stmt = $pdo->prepare("SELECT Email FROM tbl_users");
                    $stmt->execute();

                    echo '<select name="edit_taskAllocated" id="edit_taskAllocated" class="addInput">';

                    // Loop and print users as options
                    while ($row = $stmt->fetch(PDO::FETCH_ASSOC))
                    {
                        echo '<option value="' . $row['Email'] . '">' . retrieveNames($row['Email'],$pdo) . '</option>';
                    }
                    echo '</select>';
                }
                catch (PDOException $ex)
                {
                    errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
                    die();
                } ?>
                <br/>

                <label for="edit_startDate" class="titles">Start Date:</label>
                <input value="<?php echo $startDate; ?>" name="edit_startDate" type="date" id="edit_startDate" class="addInput" required />
                <br/>

                <label for="edit_endDate" class="titles">End Date:</label>
                <input value="<?php echo $endDate; ?>"name="edit_endDate" type="date" id="edit_endDate" class="addInput" required />

                <label for="edit_taskStatus">Status</label>
                <select name="edit_taskStatus" class="addInput" id="edit_taskStatus">
                    <option <?php if ($status == 0) { echo 'selected'; }?> value="0">Abandoned</option>
                    <option <?php if ($status == 1) { echo 'selected'; }?> value="1">Allocated</option>
                    <option <?php if ($status == 1) { echo 'selected'; }?> value="2">Completed</option>
                </select><br/><br/>

                <input name="submit" class="modalButton" type="submit" value="submit"/>
                <input name="clear" class="modalButton" type="reset" value="clear" />
            </fieldset>
        </form>
    </div>
</div>
