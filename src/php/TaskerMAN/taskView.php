<?php
// First sanitise the input
if (isset($_GET['id']))
{
    if (filter_var($_GET['id'],FILTER_SANITIZE_NUMBER_INT))
    {
        // Clean, we can use that number and retrieve data
        $taskID = $_GET['id'];

        // SQL retrieve data
        $stmt = $pdo->prepare("SELECT * from tbl_tasks WHERE TaskID = :taskID");
        $stmt->bindParam(':taskID',$taskID);
        $stmt->execute();

        // Retrieve and prepare for output
        $output = $stmt->fetch(PDO::FETCH_ASSOC);
    } else
    {
        // Not a number or maybe something odd, refresh the page
        header('Location: taskerman.php');
    }
}
?>
<div id="openView" class="modalWindow">
    <div>
        <a href="#close" title="Close" class="close">X</a>
        <fieldset>
            <legend>View Task</legend>
            <div id="modalLeft">
                <label for="taskID">Task ID</label>
                <input name="taskID" type="text" value="<?php echo $output['TaskID']; ?>" readonly />

                <label for="taskName">Task Name</label>
                <input name="taskName" type="text" readonly />

                <label for="taskAllocated">Task Allocated</label>
                <input name="taskAllocated" type="text" readonly />

                <label for="assignedTaskMember">Allocated Task Member</label>
                <input name="assignedTaskMember" type="text" readonly />

                <label for="startDate">Start Date</label>
                <input name="startDate" type="text" readonly />

                <label for="endDate">Start Date</label>
                <input name="endDate" type="text" readonly />
            </div>
            <div id="modalRight">
                <label for="taskDescription">Task Description</label>
                <textarea name="taskDescription" rows=8 cols=20 readonly>
                </textarea>
            </div>
        </fieldset>
    </div>
</div>
