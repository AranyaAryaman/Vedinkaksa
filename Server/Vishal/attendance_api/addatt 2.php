<?php
//getting the database connection
require_once 'configuration.php';


//an array to display response
$response = array();

if ( $_POST['roll'] && $_POST['last_login'] ) {
	$roll = $_POST['roll'];
	$last_login = $_POST['last_login'];

	$stmt = $conn->prepare( "INSERT INTO `attendance_activity`(`roll`,`last_login`) VALUES (?,?)" );
	$stmt->bind_param( "ss", $roll, $last_login );
	if ( $stmt->execute() == TRUE ) {
		$response['error'] = false;
		$response['message'] = "Today's attendance added successfully!";
	} else {
		$response['error'] = true;
		$response['message'] = "failed\n " . $conn->error;
	}
} else {
	$response['error'] = true;
	$response['message'] = "Insufficient parameters";
}
echo json_encode( $response );
 
