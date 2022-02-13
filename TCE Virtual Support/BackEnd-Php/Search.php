<?php
    session_start();
    include_once "Configuration.php";

    $outgoing_id = $_SESSION['unique_id'];
    $searchTerm = mysqli_real_escape_string($conn, $_POST['searchTerm']);

    $sql = "SELECT * FROM all_users WHERE NOT unique_id = {$outgoing_id} AND (name LIKE '%{$searchTerm}%' OR unique_id LIKE '%{$searchTerm}%') ";
    $output = "";
    $query = mysqli_query($conn, $sql);
    if(mysqli_num_rows($query) > 0 ) {
        include_once "Data.php";
    }
    else {
        $output .= 'No user found related to your search term';
    }
    echo $output;
?>