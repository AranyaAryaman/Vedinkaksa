<?php
$servername = "localhost";
$username = "nikhil";
$password = "160101047";
$dbname = "db_images";

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
	die();
}

$sql = "SELECT image_tbl.roll, image_tbl.PosX, image_tbl.PosY, temp_states.Visualization_State FROM image_tbl INNER JOIN temp_states on image_tbl.roll = temp_states.student_id";

$result = mysqli_query($conn, $sql);
$res = array();
while ( $row = mysqli_fetch_assoc($result) ) {
	$res[] = $row;
}

echo json_encode($res);
mysqli_close($conn)
?>
