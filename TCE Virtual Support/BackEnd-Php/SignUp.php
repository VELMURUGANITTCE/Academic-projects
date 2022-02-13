<?php
    session_start();
    include_once "Configuration.php";
    if(isset($_POST['name'])) {
        $name = mysqli_real_escape_string($conn, $_POST['name']);
    }
    if(isset($_POST['rollno'])) {
        $roll_no = mysqli_real_escape_string($conn, $_POST['rollno']);
    }
    if(isset($_POST['year'])) {
        $year = mysqli_real_escape_string($conn, $_POST['year']);
    }
    if(isset($_POST['department'])) {
        $department = mysqli_real_escape_string($conn, $_POST['department']);
    }
    if(isset($_POST['email'])) {
        $email = mysqli_real_escape_string($conn, $_POST['email']);
    }
    if(isset($_POST['password'])) {
        $password = mysqli_real_escape_string($conn, $_POST['password']);
    }
    if(isset($_POST['s_name'])) {
        $s_name = mysqli_real_escape_string($conn, $_POST['s_name']);
    }
    if(isset($_POST['staffid'])) {
        $staff_id = mysqli_real_escape_string($conn, $_POST['staffid']);
    }
    if(isset($_POST['s_department'])) {
        $s_department = mysqli_real_escape_string($conn, $_POST['s_department']);
    }
    if(isset($_POST['s_email'])) {
        $s_email = mysqli_real_escape_string($conn, $_POST['s_email']);
    }
    if(isset($_POST['s_password'])) {
        $s_password = mysqli_real_escape_string($conn, $_POST['s_password']);
    }
    
    if(!empty($name) && !empty($roll_no) && !empty($year) && !empty($department) && !empty($email) && !empty($password)){
        if(filter_var($email, FILTER_VALIDATE_EMAIL)){
            $sql = mysqli_query($conn, "SELECT * FROM students_data WHERE email = '{$email}'");
            if(mysqli_num_rows($sql) > 0) {
                echo "$email - This email already exist!";
            }
            else {
                $status = "Active now";
                $encrypt_pass = md5($password);
                $insert_query1 = mysqli_query($conn, "INSERT INTO students_data (name, roll_no, year, department, email, password, status)
                VALUES ('{$name}','{$roll_no}', '{$year}','{$department}', '{$email}', '{$encrypt_pass}', '{$status}')");
                $insert_query2 = mysqli_query($conn, "INSERT INTO all_users (unique_id, name, department, year, email, password, status)
                VALUES ('{$roll_no}', '{$name}','{$department}', '{$year}', '{$email}', '{$encrypt_pass}', '{$status}')");                
                if($insert_query1 and $insert_query2) {
                    $select_sql2 = mysqli_query($conn, "SELECT * FROM all_users WHERE email = '{$email}'");
                    if(mysqli_num_rows($select_sql2) > 0) {
                        $result = mysqli_fetch_assoc($select_sql2);
                        $_SESSION['unique_id'] = $result['unique_id'];
                        echo "success";
                    }
                    else {
                        echo "This email address not Exist!";
                    }
                }
                else {
                    echo "Something went wrong. Please try again!";
                }
            }
        }
        else{
            echo "$email is not a valid email!";
        }
    }
    elseif(!empty($s_name) && !empty($staff_id) && !empty($s_department) && !empty($s_email) && !empty($s_password)){
        if(filter_var($s_email, FILTER_VALIDATE_EMAIL)) {
            $sql = mysqli_query($conn, "SELECT * FROM staffs_data WHERE email = '{$s_email}'");
            if(mysqli_num_rows($sql) > 0) {
                echo "$email - This email already exist!";
            }
            else {
                $status = "Active now";
                $encrypt_pass = md5($s_password);
                $insert_query1 = mysqli_query($conn, "INSERT INTO staffs_data (name, staff_id, department, email, password, status)
                VALUES ('{$s_name}','{$staff_id}','{$s_department}', '{$s_email}', '{$encrypt_pass}', '{$status}')");
                $insert_query2 = mysqli_query($conn, "INSERT INTO all_users (unique_id, name, department, email, password, status)
                VALUES ('{$staff_id}','{$s_name}','{$s_department}', '{$s_email}', '{$encrypt_pass}', '{$status}')");
                if($insert_query1 and $insert_query2) {
                    $select_sql2 = mysqli_query($conn, "SELECT * FROM all_users WHERE email = '{$s_email}'");
                    if(mysqli_num_rows($select_sql2) > 0) {
                        $result = mysqli_fetch_assoc($select_sql2);
                        $_SESSION['unique_id'] = $result['unique_id'];
                        echo "success";
                    }
                    else {
                        echo "This email address not Exist!";
                    }
                }
                else {
                    echo "Something went wrong. Please try again!";
                }
            }
        }
        else {
            echo "$email is not a valid email!";
        }
    }
    else {
        echo "All input fields are required!";
    }
?>