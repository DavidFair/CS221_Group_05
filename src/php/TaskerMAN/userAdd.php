<div id="addView" class="modalWindow">
    <div>
        <div class="modal">
            <script src="js/entryValidation.js"></script>
            <form name="userAdd" id="userAdd" action="addUser.php" method="POST" onsubmit="return validateUserEntry()">
                <fieldset class="info_box">
                    <a href="users.php" title="Close" class="close">X</a>
                    <label for="add_email" class="titles">Email:</label>
                    <input name="add_email" id="add_email" type="email" class="addInput" maxlength="45" required />
                    <br/>

                    <label for="add_firstName" class="titles">First Name:</label>
                    <input name="add_firstName" id="add_firstName" type="text" class="addInput" maxlength="15" required />
                    <br/>

                    <label for="add_lastName" class="titles">Last Name:</label>
                    <input name="add_lastName" id="add_lastName" type="text" class="addInput" maxlength="15" required />

                    <label for="add_isManager" class="titles">Is Manager?</label>
                    <select name="add_isManager" class="addInput" id="add_isManager">
                        <option value="1">Yes</option>
                        <option value="0">No</option>
                    </select><br/><br/>
                    
                    <input name="submit" class="modalButton" type="submit" value="submit" />
                    <input name="clear" class="modalButton" type="reset" value="clear" />
                </fieldset>
            </form>
        </div>
    </div>
</div>