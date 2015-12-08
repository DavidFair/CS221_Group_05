<?php
/**
 * TODO Insert PHPDoc comment here
 * TODO Develop PHPUnit / custom code tests for login sequence
 * @resources http://phpro.org/tutorials/Basic-Login-Authentication-with-PHP-and-MySQL.html
 * @resources https://www.reddit.com/r/PHP/comments/luprk/how_do_you_sanitize_input/
 *
 */
require('init.php');

/*
 * Login Codeblocks
 */

// First, check for logout token
if (isset($_POST['logout']))
{
    // Unset, destroy, refresh
    session_unset();
    session_destroy();
    header('Location: index.php');
}

// Check for login token
if (isset($_POST['login']))
{
    // Authenticate user information, whilst also sanitising HTML tags
    $auth = authCheck(strip_tags($_POST['username']),$pdo);
    if ($auth)
    {
        // Set flag and redirect
        $_SESSION['login_auth'] = true;
        header('Location: taskerman.php');
    }
}

// Check for authenticated login and redirect if necessary
if ($_SESSION['login_auth'])
{
    header('Location: taskerman.php');
}


?>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <?php include('meta.php'); ?>
    <title>Login - <?php echo SITENAME . ' ' . SITEVER; ?></title>
</head>
<body>
    <main id="login">
        <div id="login">
            <script src="js/validation.js"></script>
            <form name="login" action="index.php" method="POST" onsubmit="return loginValidation()">
                <fieldset>
                    <legend>Email: </legend>
                    <input name="email" type="email" placeholder="Email: " required />
                    <input name="submit" type="submit" value="Login" />
                </fieldset>
            </form>
        </div>
    </main>
</body>
</html>
