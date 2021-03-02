<?php
$servername = "localhost";
$username = "nikhil";
$password = "160101047";
$dbname = "db_images";

// get current status
ob_start();
include 'get_attendance_status.php';
$status = ob_get_clean();
// end: get ***

// attendance not started
if ($status === 'false'){
	die("false");
}

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
	die("false");
}

if (!isset($_POST['username'])){
	die("false");
}
$username = mysqli_escape_string($conn, $_POST["username"]);
$classname = "CS";
// Class is hardcoded at the moment because there is only one(named `CS`).
// In the future, this should support multiple concurrent classes.
$sql = 'INSERT into attendance_records (username, class) values ("'.$username.'","'.$classname.'")';
if(mysqli_query($conn, $sql)){
	echo("true");
} else {
	echo("false");
}
mysqli_close($conn);
?>
