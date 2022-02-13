<?php 
  session_start();
  if(isset($_SESSION['unique_id'])){
    header("location: Home.php");
  }
?>

<?php include_once "Header.php"; ?>
<body>
  <div class="wrapper">
    <section class="form login">
      <header><center>TCEIAN LOGIN</center></header>
      <form action="#" method="POST" enctype="multipart/form-data" autocomplete="off">
        <div class="error-text"></div>
        <div class="field input">
          <label>Email Address</label>
          <input type="text" name="email" placeholder="Enter your email" required>
        </div>
        <div class="field input">
          <label>Password</label>
          <input type="password" name="password" placeholder="Enter your password" required>
          <i class="fas fa-eye"></i>
        </div>
        <div class="field button">
          <input type="submit" name="submit" value="Login">
        </div>
      </form>
      <div class="link">Not yet signed up? <a href="Registration.php"><b style="color: blue;">Signup now</b></a></div>
    </section>
  </div>
  
  <script src="JavaScript/PasswordEye.js"></script>
  <script src="JavaScript/LogIn.js"></script>

</body>
</html>
