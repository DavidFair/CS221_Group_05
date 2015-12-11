<header>
    <h1><?php echo APP_NAME . ' ' . APP_VER; ?>
</header>
<nav class="nav-bar">
    <ul class="nav nav-bar">
        <li>You are logged in as <?php echo $_SESSION['email']; ?></li>
        <div id="rightnav">
            <li id="sync"><a href="#">Synchronised</a></li>
            <li><a href="#openSettings">Settings</a></li>
            <li><a href="index.php?logout=true">Logout</a></li>
        </div>
    </ul>
    <hr/>
    <ul class="nav nav-navbar">
        <li><a href="#openView">View</a></li>
        <li><a href="#addView">Add</a></li>
        <li><a href="#editView">Edit</a></li>
        <li><a href="#deleteView">Delete</a></li>
        <li><a href="taskerman.php?refresh=yes">Refresh</a></li>
    </ul>
    <hr/>
</nav>
