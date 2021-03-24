<?php
$servername = "localhost";
$username = "nikhil";
$password = "160101047";
$dbname = "db_images";

if ($_SERVER['REQUEST_METHOD'] != 'POST') {
	die();
}
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
	die("false");
}

$student_id = $_POST['student_id'];
#$student_id = $_SESSION['user'];
$valence = $_POST['valence'];
$arousal = $_POST['arousal'];
$involvement = $_POST['involvement'];
$activity = $_POST['classroom_activity'];
$state = $_POST['emotional_state'];
$mental_state = $_POST['mental_state'];
$visualization_state = $_POST['visualisation_state'];

# FIXME: What is State here?
$sql = "INSERT INTO temp_states (student_id, Involvement, Activity, Arousal, Valence, Mental_State, Visualization_State, Alert, `State`) VALUES ('$student_id', $involvement, $activity, $arousal, $valence, $mental_state, $visualization_state, 1, $state)";


if(mysqli_query($conn, $sql)){
	echo("true");
} 

else {
	echo("false");
}

?>
