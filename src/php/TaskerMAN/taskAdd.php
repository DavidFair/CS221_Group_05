<?php
/**
 * TaskerMAN Task Add Modal Window
 * @author Oliver Earl, Tim Anderson
 */
?>
<div id="addView" class="modalWindow">
    <div>
        <div class="modal">
            <script src="js/entryValidation.js"></script>
            <form name ="taskAdd" id="taskAdd" action="taskAddGateway.php" method="POST" onsubmit="return validateEntry()">
                <fieldset class="info_box">
                    <a href="taskerman.php" title="Close" class="close">X</a>
                    <label for="add_taskName" class="titles">Task Name:</label>
                    <input name="add_taskName" id="add_taskName" type="text" class="addInput" maxlength="45" required />
                    <br/>

                    <label for="add_taskAllocated" class="titles">Allocated User:</label>
                    <?php
                    // Fill users drop-down box using database query
                    try
                    {
                        $stmt = $pdo->prepare("SELECT Email FROM tbl_users");
                        $stmt->execute();

                        echo '<select name="add_taskAllocated" id="add_taskAllocated" class="addInput">';

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

                    <label for="add_startDate" class="titles">Start Date</label>
                    <input name="add_startDate" type="date" id="add_startDate" class="addInput" required />
                    <br/>

                    <label for="add_endDate" class="titles">End Date</label>
                    <input name="add_endDate" type="date" id="add_endDate" class="addInput" required />
                    <br/>

                    <label for="add_taskStatus">Status</label>
                    <select name="add_taskStatus" class="addInput" id="add_taskStatus">
                        <option value="0">Abandoned</option>
                        <option value="1">Allocated</option>
                        <option value="2">Completed</option>
                    </select><br/>

                    <label for="add_numberOfElements" class="titles">Number of Task Elements</label>
                    <input name="add_numberOfElements" class="addInput" type="text" id="add_numberOfElements" required />
                    <br/>

                    <input name="submit" class="modalButton" type="submit" value="Submit" />
                    <input name="clear" class="modalButton" type="reset" value="Clear" />

                </fieldset>
            </form>
        </div>
    </div>
</div>