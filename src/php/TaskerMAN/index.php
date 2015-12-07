<?php
/**
 * TODO Insert PHPDoc comment here
 * TODO Develop PHPUnit / custom code tests for login sequence
 */
require('init.php');

/*
 * Login Codeblocks
 */

// Initial login flag set on load and refresh
if (isset($_POST['login']))
{
    // Set the session and redirect
    $_SESSION['login'] = $_POST['login'];
    header('Location: taskerman.php');
}

// Check for login and redirect them back to TaskerMAN if they are
if (isset($_SESSION['login']))
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
