<?php

require('init.php');

if(!isset($_SESSION['login_auth']))
{
    header('Location: index.php');
}
?>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <?php include('meta.php'); ?>
    <title><?php echo APP_NAME . ' ' . APP_VER; ?></title>
    <?php
    if ($_SESSION['emailExists'])
    {
        echo "<script>alert('Email already exists! Please try another one.');</script>";
        unset($_SESSION['emailExists']);
    }
    ?>
</head>
<body>
<!-- Container -->
<div class="container">
    <!-- Header -->
    <?php include('header.php'); ?>
    <!-- Main -->
    <table id="users">
        <thead>
        <tr>
            <th>Options</th>
            <th>Email Address</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Manager?</th>
        </tr>
        </thead>
        <tbody>
        <?php
        try
        {
            // Retrieve all tasks
            $stmt = $pdo->prepare("SELECT * FROM tbl_users ORDER BY Email");
            $stmt->execute();

            // Print
            while($row = $stmt->fetch(PDO::FETCH_ASSOC))
            {
                $emailPK = $row['Email'];
                echo '<tr>'; ?>
                <td>
                    <select onChange="window.location.href=this.value">
                        <option value="users.php"> </option>
                        <option value="users.php?id=<?php echo $emailPK; ?>#editView">Edit</option>
                        <option value="userDelete.php?id=<?php echo $emailPK; ?>">Delete</option>
                    </select>
                </td> <?php
                echo '<td>'.$emailPK.'</td>';
                echo '<td>'.$row['FirstName'].'</td>';
                echo '<td>'.$row['LastName'].'</td>';
                echo '<td>'.convertIsManager($row['IsManager']).'</td>';
            }
        }
        catch (PDOException $ex)
        {
            errorHandler($ex->getMessage(),"Fatal Database Error",LOGFILE,timePrint());
        }
        ?>
        </tbody>
    </table>
    <!--Footer-->
    <?php include('footer.php');?>
</div>
<!-- Add Window -->
<?php include('userAdd.php'); ?>
<!-- Edit Window -->
<?php include('userEdit.php'); ?>
</body>
</html>

