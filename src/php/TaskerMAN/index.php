<?php
/**
 * TODO Insert PHPDoc comment here
 * TODO Develop PHPUnit / custom code tests for login sequence
 * @author Oliver Earl, Liam Fitzgerald, Tim Anderson, Maurice Corriette
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
    if (filter_var($_POST['email'], FILTER_VALIDATE_EMAIL))
    {
        // Email is clean, safe to use
        $stmt = $pdo->prepare("SELECT * FROM tbl_users WHERE Email = :email");
        $stmt->bindParam(':email', $_POST['email']);
        $stmt->execute();

        // Retrieve output from database and check
        $output = $stmt->fetch(PDO::FETCH_NUM);

        if ($output > 0)
        {
            $_SESSION['login_auth'] = true;
            $_SESSION['email'] = $_POST['email'];
            header('Location: taskerman.php');
        }
        else
        {
            // No match in db
            $_SESSION['login_auth'] = false;
            $_SESSION['authFailed'] = false;
            header('Location: index.php');
        }
    }
    else
    {
        // Dirty input
        // TODO Refactor login, smells bad / code repetition
        $_SESSION['login_auth'] = false;
        $_SESSION{'authFailed'} = false;
        header('Location; index.php');
    }
}
?>
    <!DOCTYPE HTML>
    <html lang="en">
    <head>
        <!-- App doesn't work in older versions of IE -->
        <!--[if IE]>
        <meta HTTP-EQUIV="REFRESH" content="0; url=http://www.mozilla.com/firefox">
        <![endif]-->
        <?php include('meta.php'); ?>
        <title>Login - <?php echo APP_NAME . ' ' . APP_VER; ?></title>
    </head>
    <body>
    <main id="login_container">
        <h1>Welcome to <?php echo APP_NAME . ' ' . APP_VER; ?></h1>
            <script src="js/validation.js"></script>
            <form name="login" action="index.php" method="POST"
                  onsubmit="return loginValidate()">
                <fieldset id="login_box">
                    <legend>Login: </legend>
                    <input name="email" type="email" placeholder="Email"
                           required /><br/>
                    <div id="login"><input name="login" type="hidden" /></div>
                    <input name="submit" type="submit" value="Login" />
                </fieldset>
                <?php if ($_SESSION['authFailed'] = false)
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

// TODO Reimplement dedicated login
/*function login($email, $pdo)
{
    // First sanitise the email input and ensure it's safe
    if (filter_var($email, FILTER_VALIDATE_EMAIL))
    {
        // Safe - check against database
        $result = $pdo->prepare("SELECT * FROM tbl_users WHERE Email = :email");
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
}*/

function logout()
{
    session_unset();
    session_destroy();
    header('Location: index.php');
}
