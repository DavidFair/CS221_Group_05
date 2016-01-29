<?php
// Sanitise, run only if we can
if (isset($_GET['extraElement']))
{
    if (filter_var($_GET['extraElement'],FILTER_SANITIZE_NUMBER_INT))
    {
        $taskID = $_GET['extraElement'];

        // Check for existence of actual task, just in case
        try
        {
            $stmt = $pdo->prepare("SELECT * FROM tbl_tasks WHERE TaskID = :taskid");
            $stmt->bindParam(':taskid', $taskID);
            $stmt->execute();

            $output = $stmt->fetch(PDO::FETCH_NUM);
            if ($output < 1)
            {
                // invalid id, refresh
                header('Location: taskerman.php');
            }
        }
        catch (PDOException $ex)
        {
            errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
            header('Location: taskerman.php');
        }
    }
    else
    {
        // Refresh
        header('Location: taskerman.php');
    }
} ?>
<div id="extraElement" class="modalWindow">
    <div>
        <script src="js/entryValidation.js"></script>
        <form name="extraElement" id="extraElement" action="addExtraElement.php" method="POST" onsubmit="return validateExtraElement()">
            <fieldset class="info_box">
                <a href="taskerman.php" title="Close" class="close">X</a>
                <label for="taskDesc" class="titles">Task Description:</label>
                <input name="taskDesc" id="taskDesc" type="text" class="addInput" maxlength="45" required />
                <br/>

                <label for="taskComment" class="titles">Task Comment:</label>
                <input name="taskComment" id="taskComment" type="text" class="addInput" maxlength="45" required />
                <br/>

                <input type="hidden" name="extra_taskID" id="extra_taskID" value="<?php echo $taskID; ?>" />
                <input name="submit" class="modalButton" type="submit" value="Submit" />
                <input name="clear" class="modalButton" type="reset" value="Clear" />
            </fieldset>
        </form>
    </div>
</div>
