<div id="addView" class="modalWindow">
    <div>
        <a href="taskerman.php" title="Close" class="close">X</a>
        <div class="modal">
            <script src="js/entryValidation.js"></script>
            <form action="taskAddGateway.php" method="POST">
                <fieldset id="info_box">

                    <label for="add_taskName">Task Name:</label>
                    <input name="add_taskName" type="text" required />
                    <br/>

                    <label for="add_taskAllocated">Allocated User:</label>
                    <?php
                    // Fill users drop-down box using database query
                    try
                    {
                        $stmt = $pdo->prepare("SELECT Email FROM tbl_users");
                        $stmt->execute();

                        echo '<select name="add_taskAllocated">';

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

                    <label for="add_startDate">Start Date</label>
                    <input name="add_startDate" type="date" required />
                    <br/>

                    <label for="add_endDate">End Date</label>
                    <input name="add_endDate" type="date" required />
                    <br/>

                    <label for="add_noOfElements">Number of Task Elements</label>
                    <input name="add_numberOfElements" type="text" required />

                    <input name="submit" class="modalButton" type="submit" value="Submit" />
                    <input name="clear" class="modalButton" type="reset" value="Clear" />

                </fieldset>
            </form>
        </div>
    </div>
</div>