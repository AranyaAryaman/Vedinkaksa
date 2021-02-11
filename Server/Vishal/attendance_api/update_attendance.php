<?php
//getting the database connection
require_once 'configuration.php';

//an array to display response
$response = array();

if ( $_POST['roll_id'] && $_POST['attendance'] ) {
	$roll_id = $_POST['roll_id'];
	$attendance = $_POST['attendance'];
	$stmt = $conn->prepare( "UPDATE student SET attendance = ? WHERE roll_id = ?" );
	$stmt->bind_param( "ss", $attendance, $roll_id );
	if ( $stmt->execute() == TRUE ) {
		$response['error'] = false;
		$response['message'] = "Attendance Updated Successfully!";
	} else {
		$response['error'] = true;
		$response['message'] = "Incorrect roll_id";
	}
} else {
	$response['error'] = true;
	$response['message'] = "Insufficient Parameters";
}
echo json_encode( $response );
?>