<?php
    session_start();
    include_once "Configuration.php";
    $outgoing_id = $_SESSION['unique_id'];
    $sql = "SELECT * FROM all_users WHERE NOT unique_id = '{$outgoing_id}' ORDER BY name ASC";
    $query = mysqli_query($conn, $sql);
    $output = "";
    if(mysqli_num_rows($query) == 0){
        $output .= "No users are available to chat";
    }
    elseif(mysqli_num_rows($query) > 0){
        include_once "Data.php";
    }
    echo $output;
?>