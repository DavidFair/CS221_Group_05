<?php
//======================================================================
// Login Page
//======================================================================
/**
 * TODO Insert PHPDoc comment here
 * TODO Develop PHPUnit / custom code tests for login sequence
 */

session_save_path("tmp");
session_start();

require('init.php');

// Check for login and redirect them back to TaskerMan if they are
if (isset($_SESSION['login'])) {
    // Redirect
    header('Location: taskerman.php');
}

// Check for login
if (isset($_POST['login'])) {
    // Set session
    $_SESSION['login'] = $_POST['login'];
    // Redirect
    header('Location: taskerman.php');
}
?>
<!DOCTYPE HTML>
<html>
<head>
    <?php include('meta.php');?>
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
