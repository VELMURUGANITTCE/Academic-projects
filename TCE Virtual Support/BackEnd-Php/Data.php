<?php
    while($row = mysqli_fetch_assoc($query)){
        $sql2 = "SELECT * FROM chats WHERE (incoming_message_id = '{$row['unique_id']}'
                OR outgoing_message_id = '{$row['unique_id']}') AND (outgoing_message_id = '{$outgoing_id}' 
                OR incoming_message_id = '{$outgoing_id}') ORDER BY message_id DESC LIMIT 1";
        $query2 = mysqli_query($conn, $sql2);
        $row2 = mysqli_fetch_assoc($query2);
        (mysqli_num_rows($query2) > 0) ? $result = $row2['message'] : $result ="No message available";
        (strlen($result) > 28) ? $msg =  substr($result, 0, 28) . '...' : $msg = $result;
        if(isset($row2['outgoing_messageg_id'])){
            ($outgoing_id == $row2['outgoing_message_id']) ? $you = "You: " : $you = "";
        }
        else{
            $you = "";
        }
        ($row['status'] == "Offline now") ? $offline = "offline" : $offline = "";
        ($outgoing_id == $row['unique_id']) ? $hid_me = "hide" : $hid_me = "";
    
        $output .= '<a href="Home.php?user_id='. $row['unique_id'] .'">
                    <div class="content">
                        <div class="dropdownDP">
                            <img src="Images/Profiles/'. $row['profile_photo'] .'" alt="My DP">
                            <div class="dropdownDP-content">
                                <h4 id="UserName">Name : '. $row['name'] .'</h4>
                                <h4 id="UserIdNo">Id : '. $row['unique_id'] .'</h4>
                                <h4 id="UserDept">Department : '. $row['department'] .'</h4>
                                <h4 id="UserYear">Year : '. $row['year'] .'</h4>
                            </div>
                        </div>
                        <div class="details">
                            <span>'. $row['name'].'</span>
                            <p>'. $you . $msg .'</p>
                        </div>
                    </div>
                    <div class="status-dot '. $offline .'"><i class="fas fa-circle"></i></div>
                </a>';
    }
?>