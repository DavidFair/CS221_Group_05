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

// Check if the user logged out
if (isset($_GET['logout']))
{
    logout();
}

// Check the user has already been fully authenticated
if ($_SESSION['login_auth'])
{
    header('Location: taskerman.php');
}

// Check if the user has attempted to login
if (isset($_POST['login']))
{
    if (login($_POST['email'],$pdo))
    {
        // Email matches database user
        $_SESSION['login_auth'] = true;
        header('Location: taskerman.php');
    }
    else
    {
        $_SESSION['login_auth'] = false;
        $authFailed = true;
    }
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
                    <input name="email" type="email" placeholder="Email"
                           required />
                    <input name="login" type="hidden" />
                    <input name="submit" type="submit" value="Login" />
                </fieldset>
                <?php if ($authFailed)
                echo '<strong>Login unsuccessful.</strong>';
                ?>
            </form>
        </div>
        <?php if($debug)
        { ?>
            <h2>DISCLAIMER</h2>
            <p>
                This software is working in debug mode. Some things may not be
                working completely as intended.
            </p>
            <p>
                Functionality may be partially or completely unimplemented. By
                logging in, you have agreed to acknowledge this warning in its
                entirety.
            </p>
        <?php } ?>
    </main>
    </body>
    </html>
<?php

function login($email, $db)
{
    // First sanitise the email input and ensure it's safe
    if (filter_var($email, FILTER_VALIDATE_EMAIL))
    {
        // Safe - check against database
        $result = $db->prepare("SELECT * FROM tbl_users WHERE Email = :email");
        $result->bindParam(':email', $email);
        $result->execute();

        $output = $result->fetch(PDO::FETCH_NUM);
        if($output > 0)
        {
            // Match
            return true;
        }
    }
    // Bad input or email not found in database
    return false;
}

function logout()
{
    session_unset();
    session_destroy();
    header('Location: index.php');
}
