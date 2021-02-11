<?php
//getting the database connection
require_once 'configuration.php';

//an array to display response
$response = array();

if ( $_POST['id'] && $_POST['price'] ) {
	$id = $_POST['id'];
	$price = $_POST['price'];
	$stmt = $conn->prepare( "UPDATE products SET price = ? WHERE id = ?" );
	$stmt->bind_param( "ss", $price, $id );
	if ( $stmt->execute() == TRUE ) {
		$response['error'] = false;
		$response['message'] = "Price Updated Successfully!";
	} else {
		$response['error'] = true;
		$response['message'] = "Incorrect id";
	}
} else {
	$response['error'] = true;
	$response['message'] = "Insufficient Parameters";
}
echo json_encode( $response );
?>