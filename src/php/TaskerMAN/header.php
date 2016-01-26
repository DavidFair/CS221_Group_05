<header>
    <h1><?php echo APP_NAME . ' ' . APP_VER; ?>
</header>
<nav class="nav-bar">
    <ul class="nav nav-bar">
        <li>You are logged in as <?php echo retrieveNames($_SESSION['email'],$pdo); ?></li>
        <div id="rightnav">
            <li><a href="taskerman.php?addView" class="button">Add Task</a></li>
            <li><a href="taskerman.php" class="button"Refresh</a></li>
            <li><a href="#openSettings" class="button">Settings</a></li>
            <li><a href="index.php?logout=true" class="button">Logout</a></li>
        </div>
    </ul>
    <hr/>
</nav>
