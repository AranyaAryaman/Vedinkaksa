<?php
//getting the database connection
require_once 'configuration.php';


//an array to display response
$response = array();


if ( $_POST['roll_id'] && $_POST['last_login'] ) {
	$roll_id = $_POST['roll_id'];
	$last_login = $_POST['last_login'];

	$stmt = $conn->prepare( "INSERT INTO `student`(`roll_id`,`last_login`) VALUES (?,?)" );
	$stmt->bind_param( "ss", $roll_id, $last_login );

	if ( $stmt->execute() == TRUE ) {
		$response['error'] = false;
		$response['message'] = "Today's attendance added successfully!";
	} else {
		$response['error'] = true;
		$response['message'] = "today's attendance added";
	}
}
//echo "login successfull"
//$date=date("l jS \of F Y h:i:s A");
//$q=mysqli_query($conn, "UPDATE student set roll_id='$date'")

else {
	$response['error'] = true;
	$response['message'] = "Insufficient parameters";
}
echo json_encode( $response );
//ON DUPLICATE KEY UPDATE `student` SET last_login = ? WHERE roll_id = ?
