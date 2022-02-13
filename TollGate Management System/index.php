<?php
include("ConnectToDatabase.php");
?>
<!DOCTYPE HTML>
<html>
<title>Tollgate Management System</title>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="index.css">
<script src="index.js"></script>
<script>
function Toast() {
    var x = document.getElementById("toast")
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 5000);
}
</script>
</head>
<body>
<title1>
<t1><b>TOLL TAX MANAGEMENT SYSTEM</b></t1></br>
<t2 style="color:white;font-size:30px;">Department of Transport Service</t2>
</title1>
<div id="id01" class="modal">
  <div class="modal-content animate">
    <div class="imgcontainer">
      <img src="https://newsearchinnovation.github.io/NSI/Photos/nsi_logo.png" width="50" height="50">
    </div>
    <div class="container">
<form method="post">
      <label for="uname"><b>User Id</b></label>
      <input type="text" placeholder="Enter User Id" name="uname" id="uname" required>
      <label for="psw"><b>Password</b></label>
      <input type="password" placeholder="Enter Password" name="psw" id="psw" required>
      <button type="submit" name="submit">Login</button>
</form>
    </div>
    <div class="container" style="background-color:#f1f1f1">
      <a href="https://newsearchinnovation.github.io/NSI/registeruser.nsi.in.html"><button type="button" class="cancelbtn">Register</button></a>
      <span class="psw">Forgot <a href="#">password?</a></span>
    </div>
  </div>
</div>
<div id="toast"><div id="desc">User Id or Password are Incorrect!</div></div>
<?php
if(isset($_POST['submit'])) {
   authenticate();
}
function authenticate() {
    $connection = oci_connect('system','Jaga12345','localhost/DuJa');
    if (!$connection) {
        echo "oops, Database not connected to server! ==>  ";
        $e = oci_error();
        trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
    }
    $sql = oci_parse($connection, 'SELECT * FROM tollgatesystem');
    oci_execute($sql);
    while (($row = oci_fetch_array($sql, OCI_BOTH)) != false) {
        if(isset($_POST['uname'])) {
            $UserId = $_POST['uname'];
        }
        if(isset($_POST['psw'])) {
            $Password = $_POST['psw'];
        }
        if($row['USERID']==$UserId and $row['PASSWORD']==$Password) {
            header("Location: Home.php?user_id=$UserId");
            exit();
        }
    }
    echo '<script>Toast();</script>';
    oci_free_statement($sql);
    oci_close($connection);
}
?>
</body>
</html>
