<!DOCTYPE HTML>
<html>
<title>Tollgate Management System</title>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="index.js"></script>
    <link rel="stylesheet" href="bootstrap.min.css">
    <link rel="stylesheet" href="style.css">
<script>
function Toast(colorName) {
    var x = document.getElementById("toast")
    x.style.backgroundColor = colorName;
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 5000);
}
</script>
</head>
<body>
<?php
    $user_id = $_GET["user_id"];
    $connection = oci_connect('system','Jaga12345','localhost/DuJa');
    if (!$connection) {
        echo "oops, Database not connected to server! ==>  ";
        $e = oci_error();
        trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
    }
    $sql = oci_parse($connection, 'SELECT * FROM tollgatesystem');
    oci_execute($sql);
    while (($row = oci_fetch_array($sql, OCI_BOTH)) != false) {
        if($row['USERID']==$user_id) {
            $branch_id =  $row['BRANCH'];
            $system_number =  $row['SYSTEMNUMBER'];
        }
    }

?>
<title1>
<t1><b>TOLL TAX MANAGEMENT SYSTEM</b></t1></br>
<t2 style="color:white;font-size:30px;position: absolute;top: 50px;left: 450px;right: 450px;">Department of Transport Service</t2>
</title1>
<div class="dropdown">
<profileBox>
<profileBoxIn1><img src="https://jagaganesh.github.io/TCE/TCE_IT_FOURTH%20SEMESTER_DBMSL/TollGate%20Management%20System/Photos/ProfileLogo.png" width=50 height=50></profileBoxIn1>
<profileBoxIn2 id="system_no"><b><?php echo $system_number; ?></b></profileBoxIn2>
</profileBox>
  <div class="dropdown-content">
    <a href="#">State : Tamil Nadu</a>
    <a href="#">District : Ramanathapuram</a>
    <a href="#">Branch : <?php echo $branch_id; ?></a>
  </div>
</div>

<div class="content">
    <div class="container">
        <div class="row align-items-stretch justify-content-center no-gutters">
            <div class="col-md-7">
                <div class="form h-100 contact-wrap p-5">
                    <h3 class="text-center">VEHICLES</h3></br>
                    <form class="mb-5" method="post" id="contactForm" name="contactForm">
                        <div class="row">
                            <div class="col-md-6 form-group mb-3">
                                <label for="" class="col-form-label"><b>Vehicle Id</b></label>
                                <input type="text" class="form-control" name="vehicle_id" id="vehicle_id" placeholder="Vehicle Id">
                            </div>
                            <div class="col-md-6 form-group mb-3">
                                <label for="" class="col-form-label"><b>Status</b></label>
                                <input list="status" id="status_io" class="form-control" name="status_io" placeholder="Choose In / Out">
                                <datalist id="status">
                                    <option value="IN">
                                    <option value="OUT">
                                </datalist>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 form-group mb-3">
                                <label for="budget" class="col-form-label"><b>Mode of Payment</b></label>
                                <input list="Mode-of-Payment" id="MoP" class="form-control" name="MoP" placeholder="Choose Mode of Payment">
                                <datalist id="Mode-of-Payment">
                                    <option value="Fast Tag">
                                    <option value="Cash">
                                    <option value="Card">
                                    <option value="UPI">
                                </datalist>
                            </div>
		            <div class="col-md-6 form-group mb-3">
                                <label for="" class="col-form-label"><b>Date</b></label>
                                <input type="date" class="form-control" name="DoR" id="DoR"  placeholder="Your email">
                            </div>
                        </div>
                        <div class="row mb-5">
                            <div class="col-md-12 form-group mb-3">
                                <label for="message" class="col-form-label"><b>Amount Paid</b></label>
                                <input type="number" class="form-control" name="amount_paid" id="amount_paid"  placeholder="0000">
                            </div>
                        </div>
                        <div class="row justify-content-center">
                            <div class="col-md-5 form-group text-center">
                                <input type="submit" value="Register Entry" name="submit" class="btn btn-block btn-primary rounded-0 py-2 px-4">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<?php
    if(isset($_POST['submit'])) {
       Register();
    }
    function Register() {
        if(isset($_POST['vehicle_id'])) {
            $VehicleId = $_POST['vehicle_id'];
        }
        if(isset($_POST['status_io'])) {
            $Status = $_POST['status_io'];
        }
        if(isset($_POST['MoP'])) {
            $ModeOfPayment = $_POST['MoP'];
        }
        if(isset($_POST['DoR'])) {
            $TodayDate = $_POST['DoR'];
            $RegisterDate = date('Y.m.d', strtotime($TodayDate));
            date_default_timezone_set("Asia/Kolkata");
            $RegisterDateTime = $RegisterDate."_".date("H.i");
        }
        if(isset($_POST['amount_paid'])) {
            $AmountPaid = $_POST['amount_paid'];
        }
        date_default_timezone_set("Asia/Kolkata");
        $PaymentId = date("YmdHis")."001";
        $SystemNumber = $GLOBALS['system_number'];  
        $connection = oci_connect('system','Jaga12345','localhost/DuJa');
        if (!$connection) {
            echo "oops, Database not connected to server! ==>  ";
            $e = oci_error();
            trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
        }
        else {
            $sql1 = oci_parse($connection, "INSERT INTO vehicles(VEHICLEID,SYSTEMNUMBER,STATUS,DATETIME) VALUES('$VehicleId','$SystemNumber','$Status','$RegisterDateTime')");
            $sql2 = oci_parse($connection, "INSERT INTO payment(VEHICLEID,PAYMENTID,MODEOFPAYMENT,AMOUNT) VALUES('$VehicleId','$PaymentId','$ModeOfPayment','$AmountPaid')");
            $result1 = oci_execute($sql1);
            $result2 = oci_execute($sql2);
            if ($result1 and $result2) {
                echo "<div id='toast'><div id='desc'><b>Succesfully Uploaded</b></div></div>";
		echo "<script>Toast('green');</script>";
		exit();
	    }
            else {
                echo "<div id='toast'><div id='desc'><b>Error! Not Uploaded</b></div></div>";
		echo "<script>Toast('red');</script>";
		exit();
	    }
        }
    }
?>

</body>
</html>