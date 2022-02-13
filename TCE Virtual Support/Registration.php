<?php 
  session_start();
  if(isset($_SESSION['unique_id'])){
    header("location: users.php");
  }
?>
<?php include_once "Header.php"; ?>
<body>
  <div class="wrapper">
    <section class="form signup">
      <header><center>TCEIAN REGISTRATION</center></header>
      <div class="slide-controls">
           <input type="radio" name="slide" id="Student"  onclick="change_register1()" checked>
           <input type="radio" name="slide" id="Staff"  onclick="change_register2()">
           <label for="Student" class="slide student_register">Student</label>
           <label for="Staff" class="slide staff_register">Staff</label>
           <div class="slider-tab"></div>
        </div>
      <form action="#" method="POST" enctype="multipart/form-data" autocomplete="off">
        <div class="error-text"></div>
        <div  id="student_register">
          <div class="name-details">
            <div class="field input">
              <label>Name</label>
              <input type="text" name="name" placeholder="Name" required>
            </div>
            <div class="field input">
              <label>Roll No</label>
              <input type="text" name="rollno" placeholder="Roll No" required>
            </div>
          </div>
          <div class="name-details">
              <div class="field input">
                  <label>Year</label>
                  <select name="year" id="year">
                      <?php
                          for($n=1950;$n<=2022;$n++) {
                              $m=$n+4;
                              echo "<option value='$n-$m'>$n - $m</option>";
                          }
                      ?>
                  </select>
              </div>
              <div class="field input">
                  <label>Department</label>
                  <select name="department" id="department">
                      <option value='Civil Engineering'>Civil Engineering</option>
                      <option value='Electric and Electronic Engineering'>Electric and Electronic Engineering</option>
                      <option value='Electronic and Communication Engineering'>Electronic and Communication Engineering</option>
                      <option value='Computer Science and Engineering'>Computer Science and Engineering</option>
                      <option value='Information Technology'>Information Technology</option>
                      <option value='Mechanical Engineering'>Mechanical Engineering</option>
                      <option value='Mechatronics'>Mechatronics</option>
                  </select>
              </div>
          </div>
          <div class="field input">
            <label>Email Address</label>
            <input type="text" name="email" placeholder="Enter your tce mail" required>
          </div>
          <div class="field input">
            <label>Password</label>
            <input type="password" name="password" placeholder="Enter new password" required>
            <i class="fas fa-eye"></i>
          </div>
        </div>
        <div id="staff_register">
          <div class="name-details">
            <div class="field input">
              <label>Name</label>
              <input type="text" name="s_name" placeholder="Name" required>
            </div>
            <div class="field input">
              <label>Staff Id</label>
              <input type="text" name="staffid" placeholder="Staff Id" required>
            </div>
          </div>
          <div class="field input">
              <label>Department</label>
              <select name="s_department" id="s_department">
                  <option value='Civil Engineering'>Civil Engineering</option>
                  <option value='Electric and Electronic Engineering'>Electric and Electronic Engineering</option>
                  <option value='Electronic and Communication Engineering'>Electronic and Communication Engineering</option>
                  <option value='Computer Science and Engineering'>Computer Science and Engineering</option>
                  <option value='Information Technology'>Information Technology</option>
                  <option value='Mechanical Engineering'>Mechanical Engineering</option>
                  <option value='Mechatronics'>Mechatronics</option>
                  <option value='Mathematical Department'>Mathematical Department</option>
                  <option value='Physics Department'>Physics Department</option>
                  <option value='Chemistry Department'>Chemistry Department</option>
                  <option value='English Department'>English Department</option>
                  <option value='Others'>Others</option>
              </select>
          </div>
          <div class="field input">
            <label>Email Address</label>
            <input type="text" name="s_email" placeholder="Enter your tce mail" required>
          </div>
          <div class="field input">
            <label>Password</label>
            <input type="password" name="s_password" placeholder="Enter new password" required>
            <i class="fas fa-eye"></i>
          </div>
        </div>
        <div class="field button">
          <input type="submit" name="submit" value="Register">
        </div>
      </form>
      <div class="link">Already signed up? <a href="Login.php"><b style="color: blue;">Login now</b></a></div>
    </section>
  </div>

  <script src="JavaScript/PasswordEye.js"></script>
  <script src="JavaScript/SignUp.js"></script>
  <script src="JavaScript/Register.js"></script>
</body>
</html>
