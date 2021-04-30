<?php
$servername = "localhost";
$username = "nikhil";
$password = "160101047";
$dbname = "db_images";

// get current name
ob_start();
include 'getAssignmentsStatus.php';
$old_name = ob_get_clean();
// end: get ***

if (!isset($_POST['assignment_name'])){
	die($old_name);
}

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
	die($old_name);
}


$new_name = $_POST["assignment_name"];

// Class is hardcoded at the moment because there is only one(named `CS`).
// In the future, this should support multiple concurrent classes.
$sql = 'UPDATE assignment_status SET assignment_name="'.$new_name.'" where class="CS"';
if(mysqli_query($conn, $sql)){
	echo($new_name);
} else {
	echo($old_name);
}
mysqli_close($conn);
?>
