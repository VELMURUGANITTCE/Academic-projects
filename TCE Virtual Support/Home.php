<?php 
  session_start();
  include_once "BackEnd-Php/Configuration.php";
  if(!isset($_SESSION['unique_id'])){
    header("location: LogIn.php");
  }
?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel = "icon" href = "Images/tce_logo.png" type = "image/x-icon"> 
  <title>TCE TVS</title>
  <link rel="stylesheet" href="Css/home.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
</head>

<body>

<div class="wrapper">
    <section class="users" id="section1">
        <header>
            <div class="content">
                <?php 
                    $sql = mysqli_query($conn, "SELECT * FROM all_users WHERE unique_id = '{$_SESSION['unique_id']}'");
                    if(mysqli_num_rows($sql) > 0){
                    $row = mysqli_fetch_assoc($sql);
                    }
                ?>
                <div class="dropdownDP">
                    <img src="Images/Profiles/<?php echo $row['profile_photo']; ?>" alt="My DP">
                    <div class="dropdownDP-content">
                        <h4 id="UserName">Name : <?php echo $row['name']; ?></h4>
                        <h4 id="UserIdNo">Id : <?php echo $row['unique_id']; ?></h4>
                        <h4 id="UserDept">Department : <?php echo $row['department']; ?></h4>
                    </div>
                </div>
                <div class="details">
                    <span><?php echo $row['name']; ?></span>
                    <p><?php echo $row['status']; ?></p>
                </div>
            </div>
            <a href="BackEnd-Php/LogOut.php?logout_id=<?php echo $row['unique_id']; ?>" class="logout">Logout</a>
        </header>
        <div class="users_area">
            <div class="all_user_list" id="all_user_list">   
                <div class="search">
                    <div class="back-icon" onclick="HideAllUsersList()"><i class="fas fa-arrow-left"></i></div>
                    <span class="text">Select an user to start chat</span>
                    <input type="text" placeholder="Enter name to search...">
                    <button><i class="fas fa-search"></i></button>
                </div>
                <div class="users-list">
                </div>
            </div>
            <div class="recent_user_list" id="recent_user_list">
                <div class="users-list">
                </br>
                    <?php
                        /*$sql_rul = "SELECT * FROM all_users WHERE unique_id IN (SELECT DISTINCT incoming_message_id FROM chats WHERE outgoing_message_id = '{$_SESSION['unique_id']}' ORDER BY date_time DESC)";*/
                        $sql_rul = "SELECT DISTINCT chats.incoming_message_id, all_users.* FROM chats LEFT JOIN all_users ON chats.incoming_message_id = all_users.unique_id WHERE chats.outgoing_message_id = '{$_SESSION['unique_id']}' ORDER BY chats.date_time DESC";
                        $query_rul = mysqli_query($conn, $sql_rul);
                        if(mysqli_num_rows($query_rul) == 0) {
                            echo "<center>No Recent Users</center>";
                        }
                        elseif(mysqli_num_rows($query_rul) > 0) {
                            while($row = mysqli_fetch_assoc($query_rul)) {
                                $sql2 = "SELECT * FROM chats WHERE (incoming_message_id = '{$row['unique_id']}'
                                OR outgoing_message_id = '{$row['unique_id']}') AND (outgoing_message_id = '{$_SESSION['unique_id']}' 
                                OR incoming_message_id = '{$_SESSION['unique_id']}') ORDER BY message_id DESC LIMIT 1";
                                $query2 = mysqli_query($conn, $sql2);
                                $row2 = mysqli_fetch_assoc($query2);
                                (mysqli_num_rows($query2) > 0) ? $result = $row2['message'] : $result ="No message available";
                                (strlen($result) > 28) ? $msg =  substr($result, 0, 28) . '...' : $msg = $result;
                                if(isset($row2['outgoing_messageg_id'])){
                                    ($outgoing_id == $row2['outgoing_message_id']) ? $you = "You: " : $you = "";
                                }else{
                                    $you = "";
                                }
                                ($row['status'] == "Offline now") ? $offline = "offline" : $offline = "";
                                ($_SESSION['unique_id'] == $row['unique_id']) ? $hid_me = "hide" : $hid_me = "";
                                echo '<a href="Home.php?user_id='. $row['unique_id'] .'">
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
                        }
                    ?>
                </div>
                <div id="plus_to_add" class="plus_to_add" onclick="ShowAllUsersList()">
                    <p>+</p>
                </div>
            </div>
        </div>
    </section>
    <section class="chat_box" id="section2">
        <div class="chat-area">
            <header>
                <?php 
                    if(isset($_GET['user_id'])) {
                        $user_id = mysqli_real_escape_string($conn, $_GET['user_id']);
                        $sql = mysqli_query($conn, "SELECT * FROM all_users WHERE unique_id = '{$user_id}'");
                        if(mysqli_num_rows($sql) > 0){
                            $row = mysqli_fetch_assoc($sql);
                        }
                        else{
                            header("location: Home.php");
                        }
                    }
                    elseif(isset($_SESSION['unique_id'])) {
                        $sql = mysqli_query($conn, "SELECT * FROM all_users WHERE unique_id = '{$_SESSION['unique_id']}'");
                        if(mysqli_num_rows($sql) > 0){
                            $row = mysqli_fetch_assoc($sql);
                        }
                    }
                ?>
                <a href="Home.php" class="back-icon"><i class="fas fa-arrow-left"></i></a>
                <div class="dropdownDP">
                    <img src="Images/Profiles/<?php echo $row['profile_photo']; ?>" alt="Selected User DP">
                    <div class="dropdownDP-content">
                        <h4 id="UserName">Name : <?php echo $row['name']; ?></h4>
                        <h4 id="UserIdNo">Id : <?php echo $row['unique_id']; ?></h4>
                        <h4 id="UserDept">Department : <?php echo $row['department']; ?></h4>
                        <h4 id="UserYear">Year : <?php echo $row['year']; ?></h4>
                    </div>
                </div>
                <div class="details">
                    <span><?php echo $row['name']; ?></span>
                    <p><?php echo $row['status']; ?></p>
                </div>
            </header>
            <div class="chat-box">
                <div class="text" id="WelcomeMessage">Welcome to TCE Virtual Support</div>
            </div>
            
            <form action="#" class="typing-area">
                <input type="text" class="incoming_id" name="incoming_id" value="<?php echo $user_id; ?>" hidden>
                <input type="text" name="message" class="input-field" placeholder="Type a message here..." autocomplete="off">
                <button><i class="fab fa-telegram-plane"></i></button>
            </form>
        </div>
    </section>
</div>

<script src="JavaScript/Home.js"></script>
<script src="JavaScript/ChatBox.js"></script>
<script>
    if(screen.width < screen.height) {
        const queryString = window.location.search;
        console.log(queryString);
        const urlParams = new URLSearchParams(queryString);
        const user_id = urlParams.get('user_id');
        if(user_id != null) {
            var x = document.getElementById("section1");
            var y = document.getElementById("section2");
            x.style.display = "none";
            y.style.display = "block";
        }
    }
</script>
</body>
</html>
