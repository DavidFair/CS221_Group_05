<header>
    <h1><?php echo APP_NAME; ?></h1>
</header>
<nav class="nav-bar">
    <ul class="nav nav-bar">
        <li>You are logged in as <?php echo retrieveNames($_SESSION['email'],$pdo); ?></li>
        <li class="rightNav"><a href="index.php?logout=true" class="button">Logout</a></li>
        <li class="rightNav"><a href="taskerman.php" class="button">Refresh</a></li>
        <li class="rightNav"><a href="taskerman.php#addView" class="button">Add Task</a></li>
    </ul>
    <hr/>
</nav>