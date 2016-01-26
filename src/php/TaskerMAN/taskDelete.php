<?php
require_once('init.php');

// Check that the user is logged in first and redirect if they aren't
if (!($_SESSION['login_auth']))
{
    session_unset();
    session_destroy();
    header('Location: index.php');
}

// Sanitise the input, this could easily be dirty
if (filter_var($_POST['id'],FILTER_SANITIZE_NUMBER_INT))
{
    // With this done we should still use prepared statements
    try
    {
        $stmt = $pdo->prepare("DELETE FROM :tbltasks WHERE :id = :filteredID");
        
    } catch (PDOException $ex)
    {

    }
}
else
{
    // Dirty input, log out and redirect for safety
    session_unset();
    session_destroy();
    header('Location: index.php');
}