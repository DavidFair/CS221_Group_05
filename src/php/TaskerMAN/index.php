<?php
/**
 * TaskerMAN Login
 * @author Oliver Earl, Tim Anderson
 *
 * Resources helpful:
 * http://phpro.org/tutorials/Basic-Login-Authentication-with-PHP-and-MySQL.html
 * https://www.reddit.com/r/PHP/comments/luprk/how_do_you_sanitize_input/
 */
require('init.php');

/*
 * Login specific codeblocks
 */

// Check if the user logged out
if (isset($_GET['logout'])) { logout(); }

// Check if the user is authenticated
if ($_SESSION['login_auth']) { header('Location: taskerman.php'); }

/*
 * Login
 */
if (isset($_POST['login'])) { login($_POST['email'],$pdo); }

/**
 * @param $email
 * @param $pdo
 */
function login ($email, $db)
{
    // Sanitize the variable first
    if (filter_var($email, FILTER_VALIDATE_EMAIL)) {
        // Email is clean
        try {
            // Build SQL statement
            $stmt = $db->prepare("SELECT * FROM tbl_users WHERE Email = :email");
            $stmt->bindParam(':email', $email);
            $stmt->execute();

            // Retrieve data and check for a row returned by SQL
            $output = $stmt->fetch(PDO::FETCH_NUM);

            if ($output > 0) {
                // Check for manager status
                if (isManager($email, $db)) {
                    // Authorise login
                    $_SESSION['login_auth'] = true;
                    $_SESSION['email'] = $email;
                    // Redirect
                    header('Location: taskerman.php');
                }
                else {
                    // Authorisation failed due to insufficient rights
                    $_SESSION['login_auth'] = false;
                    $_SESSION['authFailed'] = true;
                }
            } else {
                // Authorisation failed due to no mismatching email
                $_SESSION['login_auth'] = false;
                $_SESSION['authFailed'] = true;
            }
        } catch (PDOException $ex) {
            errorHandler($ex->getMessage(), "Login Failed", LOGFILE, timePrint());
            die();
        }
    }
}
?>
    <!DOCTYPE HTML>
    <html lang="en">
    <head>
        <!-- TaskerMAN doesn't like older versions of IE much. -->
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
        <!-- Login -->
        <form name="login" action="index.php" method="POST" onsubmit="return loginValidate()">
            <fieldset id="login_box">
                <legend>Login: </legend>
                <input name="email" type="email" placeholder="Email" required /><br/>
                <div id="login">
                    <input name="login" type="hidden"/>
                    <input name="submit" type="submit" value="Login"/>
                </div>
            </fieldset>
            <?php if ($_SESSION['authFailed']) { echo '<strong>Login Unsuccessful.</strong>'; } ?>
        </form>
        <?php if($debug) { ?>
            <h2>Disclaimer</h2>
            <p>
                This software is in debug mode. Please disable debug mode before deployment.
            </p>
        <?php } ?>
    </main>
    </body>
    </html>
<?php
/**
 *
 */
function logout()
{
    session_unset();
    session_destroy();
    header('Location: index.php');
}

/**
 * @param $email
 * @param $db
 * @return bool
 */
function isManager ($email, $db)
{
    try
    {
        $stmt = $db->prepare("SELECT * FROM tbl_users WHERE Email = :email");
        $stmt->bindParam(':email',$email);
        $stmt->execute();

        // Retrieve data in an associative array
        $output = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($output['IsManager'] == 1)
        {
            // Manager
            return true;
        } else
        {
            // Not a manager
            return false;
        }
    }
    catch (PDOException $ex)
    {
        errorHandler($ex->getMessage(),"Database Error",LOGFILE,timePrint());
        die();
    }
}