<header>
    <h1><?php echo APP_NAME; ?></h1>
</header>
<nav class="nav-bar">
    <ul class="nav nav-bar">
        <li>You are logged in as <?php echo retrieveNames($_SESSION['email'],$pdo); ?></li>
        <li class="rightNav"><a href="index.php?logout=true" class="button">Logout</a></li>
        <li class="rightNav"><a href="javascript:window.location.reload()" class="button">Refresh</a></li>
        <li class="rightNav"><a href="users.php#addView" class="button">Add User</a></li>
        <li class="rightNav"><a href="taskerman.php#addView" class="button">Add Task</a></li>
        <li class="rightNav"><a href="users.php" class="button">Users</a></li>
        <li class="rightNav"><a href="taskerman.php">Tasks</a></li>
    </ul>
    <hr/>
</nav>