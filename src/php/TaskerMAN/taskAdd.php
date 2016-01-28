<div id="addView" class="modalWindow">
    <div>
        <a href="taskerman.php" title="Close" class="close">X</a>
        <div class="modal">
            <form action="addEntry.php" method="POST">
                <fieldset id="info_box">
                    <label for="taskName">Task Name:</label>
                    <input name="taskName" type="text" required />
                    <br/>

                    <label for="taskAllocated">Allocated User:</label>
                    <?php
                    // Fill users dropdown box
                    try
                    {
                        $stmt = $pdo->prepare("SELECT Email FROM tbl_users");
                        $stmt->execute();
                        echo '<select name="user_select">';
                        // Loop and print users into the dropdown box as options
                        while ($row = $stmt->fetch(PDO::FETCH_ASSOC))
                        {
                            echo '<option value="' . $row['Email'] . '">'
                                . retrieveNames($row['Email'],$pdo) . '</option>';
                        }
                        echo '</select>';
                    }
                    catch (PDOException $ex)
                    {
                        errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
                        die();
                    } ?>
                    <br/>

                    <label for="startDate">Start Date</label>
                    <input name="startDate" type="date" required />
                    <br/>

                    <label for="endDate">End Date</label>
                    <input name="endDate" type="date" required />
                    <br />

                    <script>
                        // Set global counter - it starts counting as soon as we add a second element!
                        var counter = 1;

                        function addTaskDescElement()
                        {
                            // If the counter's currently more than 10 (9 plus the first), don't allow more to be added
                            if (counter > 9)
                            {
                                alert("You cannot add more than 10 elements currently!");
                            }
                            else
                            {
                                // When called, increment first
                                counter++;
                                // Generate new div element
                                var div = document.createElement('div');

                                // Fill its HTML content, smelly
                                div.innerHTML = '<label for="taskDesc_' + counter + '">Task Description '
                                    + counter + '</label></br>' +
                                    '<input type="text" name="taskDesc_' + counter + '">' +
                                    '<input type="button" id="add_taskDesc" onclick="addTaskDescElement()" value="+"/>' +
                                    '<input type="button" id="remove_taskDesc" value="-" onclick="removeTaskDescElement(this)"/>';

                                // Append as child to first div, which follows this script
                                document.getElementById('taskDesc').appendChild(div);
                            }
                        }
                        function removeTaskDescElement(div)
                        {
                            // Remove node
                            document.getElementById('taskDesc').removeChild(div.parentNode);
                            // Decrement counter
                            counter--;
                        }
                    </script>
                    <div id="taskDesc">
                        <label for="taskDesc_1">Task Description 1</label>
                        <input name="task_Desc1" type="text" required />
                        <input type="button" id="add_taskDesc" onclick="addTaskDescElement()" value="+" />
                    </div>

                    <input name="taskToAdd" type="hidden"/>
                    <input name="submit" class="modalButton" type="submit" value="Submit" />
                    <input name="clear" class="modalButton" type="reset" value="Clear" />


                </fieldset>
            </form>
        </div>
    </div>
</div>