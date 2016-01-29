<?php
/**
 * Modal Window - Adding Elements
 * @author Oliver Earl, Jonathan Englund, Tim Anderson
 *
 * Contains presentational elements and is crucial for adding elements to the database.
 */
?>
<div id="addElements" class="modalWindow">
    <div>
        <div class="modal">
            <script src="js/entryValidation.js"></script>
            <form id="addElements" action="addEntry.php" method="POST" onsubmit="return validateElements()">
                <fieldset class="info_box">
                    <a href="taskerman.php" title="Close" class="close">X</a>
                    <?php
                    // var_dump($_SESSION['add_numberOfElements']);
                    // echo '<input type="text" name="taskDesc_1" required />';
                    for ($i = 1; $i <= ($_SESSION['add_numberOfElements']); $i++)
                    {

                        // Print a box for task description and comment
                        echo '<label for="taskDesc_' . $i . '">Task Description ' . $i .
                            '</label><br/>';
                        echo '<input class="elementInput" name="taskDesc_' . $i . '" type="text" id="taskDesc_' . $i . '" maxlength="45" required /><br/>';

                        echo '<label for="taskComment_' . $i . '">Task Comment ' . $i .
                            '</label><br/>';
                        echo '<input class="elementInput" name="taskComment_' . $i . '" type="text" id="taskComment_' . $i . '" maxlength="45" required /><br/>';
                    } ?>
                    <input name="submit" class="modalButton" type="submit" value="Submit" />
                    <input name="clear" class="modalButton" type="reset" value="Clear" />
                </fieldset>
            </form>
        </div>
    </div>
</div>
