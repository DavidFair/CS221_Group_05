<?php
require_once('init.php');

	$result = $pdo->prepare("select * from tbl_tasks");
	$pdo->execute;
	$num_rows = $pdo->prepare("mysql_num_rows($result)");
	$pdo->execute;
	$row = $num_rows->fetch(PDO::FETCH_OBJ);
	echo $row;
