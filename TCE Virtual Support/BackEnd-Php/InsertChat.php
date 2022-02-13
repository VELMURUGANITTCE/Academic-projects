<?php 
    session_start();
    if(isset($_SESSION['unique_id'])){
        include_once "Configuration.php";
        $outgoing_id = $_SESSION['unique_id'];
        $incoming_id = mysqli_real_escape_string($conn, $_POST['incoming_id']);
        $message = mysqli_real_escape_string($conn, $_POST['message']);
        date_default_timezone_set("Asia/Kolkata");
        $date_time = date("Y.m.d_H.i.s");
        if(!empty($message)){
            $sql = mysqli_query($conn, "INSERT INTO chats (incoming_message_id, outgoing_message_id, message, date_time)
                                        VALUES ('{$incoming_id}', '{$outgoing_id}', '{$message}', '{$date_time}')") or die();
        }
    }
    else{
        header("location: ../LogIn.php");
    }    
?>