<?php
// Sanitise and only run if we have input
if (isset($_GET['edit']))
{
    if (filter_var($_GET['edit'],FILTER_SANITIZE_EMAIL))
    {
        // Clean, retrieve
        $email = $_GET['edit'];

        // Retrieve
        try
        {
            $stmt = $pdo->prepare("SELECT * from tbl_users WHERE Email = :email");
            $stmt->bindParam(':email',$email);
            $stmt->execute();

            // Retrieve, prepare
            $output = $stmt->fetch(PDO::FETCH_ASSOC);

            // Store data
            $email          = $output['Email'];
            $firstName      = $output['FirstName'];
            $lastName       = $output['LastName'];
            $isManager      = $output['IsManager'];
        }
        catch (PDOException $ex)
        {
            errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
            header('Location: users.php');
        }
    } else
    {
        // Refresh the page
        header('Location: users.php');
    }
}
?>
<div id="editView" class="modalWindow">
    <div>
        <div class="modal">
            <script src="js/entryValidation.js"></script>
            <form name="userEdit" id="userEdit" action="editUser.php" method="POST" onsubmit="return validateUserEdit()">
                <fieldset class="info_box">
                    <a href="users.php" title="Close" class="close">X</a>
                    <label for="edit_email" class="titles">Email:</label>
                    <input value="<?php echo $email; ?>" name="edit_email" id="edit_email" type="email" class="addInput" maxlength="45" readonly />
                    <br/>

                    <label for="edit_firstName" class="titles">First Name:</label>
                    <input value="<?php echo $firstName; ?>" name="edit_firstName" id="add_firstName" type="text" class="addInput" maxlength="15" required />
					<br/>
					
                    <label for="edit_lastName" class="titles">Last Name:</label>
                    <input value="<?php echo $lastName; ?>" name="edit_lastName" id="edit_lastName" type="text" class="addInput" maxlength="15" required />
					<br/>
					
                    <label for="edit_isManager" class="titles">Is Manager?</label>
                    <select name="edit_isManager" class="addInput" id="edit_isManager">
                        <option <?php if ($isManager == 1) { echo 'selected'; }?> value="1">Yes</option>
                        <option <?php if ($isManager == 0) { echo 'selected'; }?> value="0">No</option>
                    </select><br/><br/>

                    <input name="submit" class="modalButton" type="submit" value="submit"/>
                    <input name="clear" class="modalButton" type="reset" value="clear" />
                </fieldset>
            </form>
        </div>
    </div>
</div>