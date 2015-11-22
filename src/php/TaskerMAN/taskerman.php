<?php
//======================================================================
// TaskerMan
//======================================================================
require('init.php');
// If the user isn't logged in, redirect them back to the login page
if !(isset($_SESSION['login'])) {
	// Redirect
	header('Location: index.php');
}
?>
<!DOCTYPE HTML>
<html lang = "en">
<head>
	<?php include('meta.php'); ?>
	<title>TaskerMan <?php echo $version; ?></title>
</head>
<body>
	<?php include('header.php'); ?>
	<?php include('nav.php'); ?>
	<main>
	<!-- Application -->
	</main>
	<?php include('footer.php');?>
</body>
</html>
