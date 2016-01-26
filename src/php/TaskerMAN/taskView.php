<div id="openView" class="modalWindow">
    <div>
        <a href="#close" title="Close" class="close">X</a>
        <?php echo'<h2>'.APP_NAME . ' ' . APP_VER . '</h2>';?>
        <fieldset>
            <legend>View Task</legend>
            <div id="modalLeft">
                <label for="taskName">Task Name:</label>
                <input name="taskName" type="text" required />
                <label for="taskAllocated">Allocated:</label>
                <input name="taskAllocated" type="text" required />
                <label for="assignedTaskMember">Allocated Task Member</label>
                <!--TODO, this needs to be hooked into the db -->
                <select>
                    <option value="email1">David Fairbrother</option>
                    <option value="email2">Joshua Doyale</option>
                </select><br/>
                <label for="startDate">Start Date:</label>
                <input name="startDate" type="date" required />
                <label for="endDate">End Date:</label>
                <input name="endDate" type="date" required />
            </div>
            <div id="modalRight">
                <label for="taskDescription">Task Description:</label>
                <textarea name="taskDescription" rows=8 cols=20 required>
                </textarea>
            </div>
        </fieldset>
    </div>
</div>