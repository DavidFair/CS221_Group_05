<?php
/**
 * TaskerMAN Delete User
 * @author Oliver Earl
 */
require_once('init.php');

// Check that the user is logged in first and redirect if they aren't
if (!($_SESSION['login_auth']))
{
    session_unset();
    session_destroy();
    header('Location: index.php');
}

// Sanitise the input, this could easily be dirty
if (filter_var($_GET['id'],FILTER_SANITIZE_EMAIL))
{
    // With this done we should still use prepared statements
    try
    {
        // First check if there are tasks belonging to the user
        $stmt = $pdo->prepare("SELECT * FROM tbl_tasks WHERE TaskOwner = :email");
        $stmt->bindParam(':email',$_GET['id']);
        $stmt->execute();

        // If any exist, time to stop
        $output = $stmt->fetch(PDO::FETCH_NUM);
        if ($output > 0) {
            $_SESSION['taskExists'] = true;
            header('Location: users.php');
        }

        // SQL Delete
        $stmt = $pdo->prepare("DELETE FROM tbl_users WHERE Email = :filteredID");
        $stmt->bindParam(':filteredID',$_GET['id']);
        $stmt->execute();
        // Redirect
        header('Location: users.php');

    } catch (PDOException $ex)
    {
        errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
        header('Location: users.php');
    }
}
else
{
    var_dump($_GET['id']);
    // Dirty input, log out and redirect for safety
    session_unset();
    session_destroy();
    header('Location: index.php');
}