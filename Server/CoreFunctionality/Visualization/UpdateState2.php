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
$valence = $_POST['Valence'];
$arousal = $_POST['Arousal'];
$involvement = $_POST['Involvement'];
$activity = $_POST['Activity'];
$mental_state = $_POST['Mental_state'];
$visualization_state = $_POST['Visualization_state'];
$alert = $_POST['Alert'];

# FIXME: What is State here?
$sql = "INSERT INTO temp_states (student_id, Involvement, Activity, Arousal, Valence, Mental_State, Visualization_State, Alert, `State`) VALUES ('$student_id', $involvement, $activity, $arousal, $valence, $mental_state, $visualization_state, $alert, 1)";
echo $sql;
if(mysqli_query($conn, $sql)){
	echo("true");
} else {
	echo("\nfalse");
	echo mysqli_error($conn);
}



return updateStates($student_id, $involvement, $activity, $arousal,	$valence, $mental_state, $visualization_state, $alert);
?>
