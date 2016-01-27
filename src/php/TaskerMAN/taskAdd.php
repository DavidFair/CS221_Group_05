<div id="addView" class="modalWindow">
    <div>
        <a href="#close" title="Close" class="close">X</a>
        <?php echo'<h2>'.APP_NAME . ' ' . APP_VER . '</h2>';?>
        <fieldset id="info_box">

	  <form action="addEntry.php" method="post">
            <div class="modal">
                <label for="taskName">Task Name:</label>
                <input name="taskName" type="text" required /><br/>
				
                <label for="taskAllocated">Allocated:</label>
<<<<<<< HEAD
                <input name="taskAllocated" type="text" required />
                <label for="assignedTaskMember">Allocated Task Member</label>
                <!--TODO, this needs to be hooked into the db

		 insert into tbl_tasks(TaskID,TaskName,StartDate,EndDate,Status,TaskOwner)
		 values('taskName','taskAllocated','assignedTaskMember', 'startDate', 'endDate');
		 -->
                <select>
                    <option value="email1">David Fairbrother</option>
                    <option value="email2">Joshua Doyale</option>
=======
                <select name="taskAllocated">
                    <option value="0">Abandoned</option>
                    <option value="1">Allocated</option>
                    <option value="2">Completed</option>
>>>>>>> a9298906d9e531db2fd99fca1bc6be6dfece3fe4
                </select><br/>
				
                <label for="assignedTaskMember">Allocated Task Member</label>
                <?php
                $statement = $pdo->prepare("SELECT Email, FirstName, LastName FROM tbl_users ORDER BY FirstName");
                $statement -> execute();
                echo '<select name="taskMember">';
                // Loop through and print
                while ($row = $statement->fetch(PDO::FETCH_ASSOC)) {
                    echo '<option value="' . $row['Email'] . '">' . $row['FirstName'] ." ". $row['LastName'] . '</option>';
                }
                echo '</select><br/>';
                ?>
				
                <label for="startDate">Start Date:</label>
                <input name="startDate" type="date" required /><br/>
				
                <label for="endDate">End Date:</label>
                <input name="endDate" type="date" required /><br/>
                <script>
                    var i = 1;
                    function addTaskDesc(){
                        i++;
                        var div = document.createElement('div');
                        div.innerHTML = 'Task Description :<br/><input type="text" name="taskDesc_'+i+'" ><input type="button" id="add_taskDesc()" onClick="addTaskDesc()" value="+" /><input type="button" value="-" onclick="removeTaskDesc(this)">';
                        document.getElementById('taskDesc').appendChild(div);
                    }
                    function removeTaskDesc(div) {
                        document.getElementById('taskDesc').removeChild( div.parentNode );
                        i--;
                    }
                </script>
                <div id="taskDesc">
                <label for="taskDescription">Task Description:</label><br/>
                    <input name="taskDesc_1" type="text" required /><input type="button" id="add_taskDesc()" onClick="addTaskDesc()" value="+" />
                </div>
				<input  name="submit" class="modalButton" type="submit" value="Submit" />
                <input name="clear" class="modalButton" type="reset" value="Clear" />
            </div>
            <input name="taskToAdd" type="hidden" />
	 </form>
        </fieldset>
    </div>
</div>
