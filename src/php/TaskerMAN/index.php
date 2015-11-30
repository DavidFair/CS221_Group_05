<?php
//======================================================================
// Login Page
//======================================================================
require('init.php');
// Check for login and redirect them back to TaskerMan if they are
if (isset($_SESSION['login'])) {
	// Redirect
	header('Location: taskerman.php');
}
?>
<!DOCTYPE HTML>
<html>
<head>
	<?php include('meta.php');?>
	<title>TaskerMan Login</title>
</head>
<body>
	<!-- Insert body of login here -->
</body>
</html>
