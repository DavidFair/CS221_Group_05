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
                <select name="taskAllocated">
                    <option value="0">Abandoned</option>
                    <option value="1">Allocated</option>
                    <option value="2">Completed</option>
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
           
                <label for="taskDescription">Task Description:</label><br/>
                <textarea name="taskDescription" rows=8 cols=20 required>
                </textarea>
                <br/>
				<input  name="submit" class="modalButton" type="submit" value="Submit" />
                <input name="clear" class="modalButton" type="reset" value="Clear" />
            </div>
            <input name="taskToAdd" type="hidden" />
	 </form>
        </fieldset>
    </div>
</div>
