<?php
/**
 * TODO Insert PHPDoc comment here
 * TODO PHPUnit tests for various sequences
 * @author OLiver Earl
 */
require('init.php');
// If the user isn't logged in, redirect them back to the login page
if (!isset($_SESSION['login_auth']))
{
    header('Location: index.php');
}
?>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <?php include('meta.php'); ?>
    <title><?php echo APP_NAME . ' ' . APP_VER; ?></title>
</head>
<body>
<!-- Container -->
<div class="container">
    <!-- Header -->
    <?php include('header.php'); ?>
    <!-- Main -->
    <table id="name">
        <thead>
        <tr>
            <th>Selected</th>
            <th>Task Name</th>
            <th>Status</th>
            <th>Assigned Task Member(s)</th>
            <th>Start Date</th>
            <th>End Date</th>
        </tr>
        </thead>
        <tbody>
        <!-- TODO Database retrieval of tasks -->
        <tr>
            <td>
                <!-- TODO Checkbox for tasks -->
                <form id="taskidgoeshere" method="POST" target="taskerman.php">
                    <input name="taskidgoeshere" type="checkbox">
                </form>
            </td>
            <td>CS25010 Task</td>
            <td>In Progress</td>
            <td>Tim Anderson</td>
            <td>11/12/15</td>
            <td>25/12/15</td>
        </tr>
        </tbody>
    </table>
    <!-- Footer -->
    <?php include('footer.php'); ?>
</div>
<!-- Modal Windows -->
<!-- View Task -->
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
<!-- Add Task -->
<div id="addView" class="modalWindow">
    <div>
        <a href="#close" title="Close" class="close">X</a>
        <?php echo'<h2>'.APP_NAME . ' ' . APP_VER . '</h2>';?>
        <fieldset>
            <legend>Add Task</legend>
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
                <br/>
                <input name="submit" class="modalButton" type="submit" value="Submit" />
                <input name="clear" class="modalButton" type="reset" value="Clear" />
            </div>
            <input name="taskToAdd" type="hidden" />
        </fieldset>
    </div>
</div>
</body>
</html>
