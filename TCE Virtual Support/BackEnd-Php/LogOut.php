<?php
    session_start();
    if(isset($_SESSION['unique_id'])) {
        include_once "Configuration.php";
        $logout_id = mysqli_real_escape_string($conn, $_GET['logout_id']);
        if(isset($logout_id)) {
            $status = "Offline now";
            $sql1 = mysqli_query($conn, "UPDATE students_data SET status = '{$status}' WHERE roll_no='{$_GET['logout_id']}'");
            $sql2 = mysqli_query($conn, "UPDATE staffs_data SET status = '{$status}' WHERE staff_id='{$_GET['logout_id']}'");
            $sql3 = mysqli_query($conn, "UPDATE all_users SET status = '{$status}' WHERE unique_id='{$_GET['logout_id']}'");
            if(($sql1 && $sql3) or ($sql2 && $sql3)){
                session_unset();
                session_destroy();
                header("location: ../Login.php");
            }
        }
        else {
            header("location: ../Home.php");
        }
    }
    else {  
        header("location: ../Login.php");
    }
?>