<div id="addView" class="modalWindow">
    <div>
        <a href="#close" title="Close" class="close">X</a>
        <?php echo'<h2>'.APP_NAME . ' ' . APP_VER . '</h2>';?>
        <fieldset>

	  <form action="addEntry.php" method="post">
            <div class="modalLeft">
                <label for="taskName">Task Name:</label>
                <input name="taskName" type="text" required />
                <label for="taskAllocated">Allocated:</label>
                <input name="taskAllocated" type="text" required />
                <label for="assignedTaskMember">Allocated Task Member</label>
                <select name="taskMember">
                    <option value="email1">David Fairbrother</option>
                    <option value="email2">Joshua Doyale</option>
                </select><br/>
                <label for="startDate">Start Date:</label>
                <input name="startDate" type="date" required />
                <label for="endDate">End Date:</label>
                <input name="endDate" type="date" required />
            </div>
            <div class="modalRight">
                <label for="taskDescription">Task Description:</label>
                <textarea name="taskDescription" rows=8 cols=20 required>
                </textarea>
                <br/>
                <input name="submit" class="modalButton" type="submit" value="Submit" />
                <input name="clear" class="modalButton" type="reset" value="Clear" />
            </div>
            <input name="taskToAdd" type="hidden" />
	 </form>
        </fieldset>
    </div>
</div>
