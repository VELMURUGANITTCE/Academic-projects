<?php 
    session_start();
    if(isset($_SESSION['unique_id'])) {
        include_once "Configuration.php";
        $outgoing_id = $_SESSION['unique_id'];
        $incoming_id = mysqli_real_escape_string($conn, $_POST['incoming_id']);
        $output = "";
        if($_POST['incoming_id']=="TCE") {
            $sql = "SELECT * FROM chats LEFT JOIN all_users ON all_users.unique_id = chats.outgoing_message_id WHERE incoming_message_id = 'TCE' ORDER BY message_id";
        }
        else {
        $sql = "SELECT * FROM chats LEFT JOIN all_users ON all_users.unique_id = 'chats.outgoing_message_id'
                WHERE (outgoing_message_id = '{$outgoing_id}' AND incoming_message_id = '{$incoming_id}')
                OR (outgoing_message_id = '{$incoming_id}' AND incoming_message_id = '{$outgoing_id}') ORDER BY message_id";
        }
        $query = mysqli_query($conn, $sql);
        if($query==false) {
            $output .= '<div class="text">Welcome to TCE Virtual Support</div>';
        }
        else {
            if(mysqli_num_rows($query) > 0){
                if($_POST['incoming_id']=="TCE") {
                    while($row = mysqli_fetch_assoc($query)){
                        if($row['outgoing_message_id'] === $outgoing_id){
                            $output .= '<div class="chat outgoing">
                                        <div class="details">
                                            <p>'. $row['message'] .'</p>
                                        </div>
                                        </div>';
                        }
                        else{
                            $output .= '<div class="chat incoming">
                                            <div class="groupchat-userinfo">
                                                <userid><b>'.$row['name'][0].'</b></userid>
                                                <div class="groupchat-userinfo_dd">
                                                    <h4>'. $row['name'].' '.$row['unique_id'] .'</h4>
                                                </div>
                                            </div>
                                        <div class="details">
                                            <p>'. $row['message'] .'</p>
                                        </div>
                                        </div>';
                        }
                    }
                }
                else {
                    while($row = mysqli_fetch_assoc($query)){
                        if($row['outgoing_message_id'] === $outgoing_id){
                            $output .= '<div class="chat outgoing">
                                        <div class="details">
                                            <p>'. $row['message'] .'</p>
                                        </div>
                                        </div>';
                        }
                        else{
                            $output .= '<div class="chat incoming">
                                        <div class="details">
                                            <p>'. $row['message'] .'</p>
                                        </div>
                                        </div>';
                        }
                    }
                }
            }
            else{
                $output .= '<div class="text">No messages are available. Once you send message they will appear here.</div>';
            }
        }
        echo $output;
    }
    else{
        header("location: ../LogIn.php");
    }
?>