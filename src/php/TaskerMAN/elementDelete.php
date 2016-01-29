<?php
/**
 * TaskerMAN Main Interface - Delete elements from a task without deleting the task itself
 * @author Oliver Earl
 */
require_once('init.php');

// Login check
if (!($_SESSION['login_auth']))
{
    session_unset();
    session_destroy();
    header('Location: index.php');
}

// Sanitise the input, this could easily be dirty
if (filter_var($_GET['id'],FILTER_SANITIZE_NUMBER_INT)) {
    // With this done we should still use prepared statements
    try {
        // First find all elements pertaining to that ID and delete
        $stmt = $pdo->prepare("DELETE FROM tbl_elements WHERE TaskID = :filteredID");
        $stmt->bindParam('filteredID', $_GET['id']);
        $stmt->execute();
        // Redirect
        header('Location: taskerman.php');
    } catch (PDOException $ex) {
        errorHandler($ex->getMessage(), "Fatal Database Error", LOGFILE, timePrint());
        header('Location: taskerman.php');
    }
} else
{
    // Dirty input, log out and redirect for safety
    session_unset();
    session_destroy();
    header('Location: index.php');
}