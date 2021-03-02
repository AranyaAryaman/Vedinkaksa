<?php
$servername = "localhost";
$username = "nikhil";
$password = "160101047";
$dbname = "db_images";

// get current status
ob_start();
include 'get_attendance_status.php';
$old_status = ob_get_clean();
// end: get ***

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
	die("false");
}

if (!isset($_POST['attendance_status']) || ($_POST['attendance_status'] != 'true' && $_POST['attendance_status'] != 'false')){
	die($old_status);
}

$new_status = $_POST["attendance_status"];

// Class is hardcoded at the moment because there is only one(named `CS`).
// In the future, this should support multiple concurrent classes.
$sql = 'UPDATE attendance_status SET status="'.$new_status.'" where class="CS"';
if(mysqli_query($conn, $sql)){
	echo($new_status);
} else {
	echo($old_status);
}
mysqli_close($conn);
?>
