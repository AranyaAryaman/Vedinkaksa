<?php
$servername = "localhost";
$username = "nikhil";
$password = "160101047";
$dbname = "db_images";

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
	die("");
}

// Class is hardcoded at the moment because there is only one(named `CS`).
// In the future, this should support multiple concurrent classes.
$sql = "SELECT assignment_name from assignment_status where class='CS'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) == 1) {
	$row = mysqli_fetch_assoc($result);
	echo($row['assignment_name']);
} else {
	echo("");
}
mysqli_close($conn);
?>
