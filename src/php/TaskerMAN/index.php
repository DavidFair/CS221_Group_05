<?php
/**
 * TODO Insert PHPDoc comment here
 * TODO Develop PHPUnit / custom code tests for login sequence
 * @author OLiver Earl, Liam Fitzgerald
 * @resources http://phpro.org/tutorials/Basic-Login-Authentication-with-PHP-and-MySQL.html
 * @resources https://www.reddit.com/r/PHP/comments/luprk/how_do_you_sanitize_input/
 *
 */
require('init.php');

/*
 * Login Codeblocks
 */

// First, check for logout token
if (isset($_GET['logout']))
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
    // $auth = authCheck(strip_tags($_POST['username']),$pdo); TODO RE-ENABLE
    $auth = "ole4@aber.ac.uk";
    if ($auth)
    {
        // Set flag and redirect
        $_SESSION['login_auth'] = true;
        $_SESSION['email'] = $auth;
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
    <title>Login - <?php echo APP_NAME . ' ' . APP_VER; ?></title>
</head>
<body>
    <main id="login_container">
        <h1>Welcome to <?php echo APP_NAME . ' ' . APP_VER; ?></h1>
        <div id="login">
            <script src="js/validation.js"></script>
            <form name="login" action="index.php" method="POST"
            onsubmit="return loginValidate()">
                <fieldset>
                    <legend>Login: </legend>
                    <input name="email" type="email"
                           placeholder="Email: " required />
                    <input name="login" type="hidden" />
                    <input name="submit" type="submit" value="Login" />
                </fieldset>
            </form>
        </div>
        <h2>DISCLAIMER</h2>
        <p>
            This is an alpha version of the software. Some things may not be
            working completely as intended.
        </p>
        <p>
            Functionality may be partially or completely unimplemented. By
            logging in, you have agreed to acknowledge this warning in its
            entirety.
        </p>
    </main>
</body>
</html>
