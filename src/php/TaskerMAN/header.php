<header>
    <h1><?php echo APP_NAME . ' ' . APP_VER; ?>
</header>
<nav class="nav-bar">
    <ul class="nav nav-bar">
        <li>You are logged in as <?php echo retrieveNames($_SESSION['email'],$pdo); ?></li>
        <div id="rightnav">
            <li><a href="#openSettings" class="button">Settings</a></li>
            <li><a href="index.php?logout=true" class="button">Logout</a></li>
        </div>
    </ul>
    <hr/>
    <ul class="nav nav-navbar">
        <li><a href="#openView" class="button">View</a></li>
        <li><a href="#addView" class="button">Add</a></li>
        <li><a href="#editView" class="button">Edit</a></li>
        <li><a href="#deleteView" class="button">Delete</a></li>
        <li><a href="taskerman.php?refresh=yes" class="button">Refresh</a></li>
    </ul>
    <hr/>
</nav>
