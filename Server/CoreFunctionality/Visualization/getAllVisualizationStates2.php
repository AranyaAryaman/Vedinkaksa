<?php
$servername = "localhost";
$username = "nikhil";
$password = "160101047";
$dbname = "db_images";

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
	die();
}

$sql = "SELECT Seat_arrangement.student_id, Seat_arrangement.seat_row, Seat_arrangement.seat_column, temp_states.Visualization_State FROM Seat_arrangement INNER JOIN temp_states on Seat_arrangement.student_id = temp_states.student_id";

$result = mysqli_query($conn, $sql);
$res = array();
while ( $row = mysqli_fetch_assoc($result) ) {
	$res[] = $row;
}

echo json_encode($res);
mysqli_close($conn)
?>
