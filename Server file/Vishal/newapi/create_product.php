<?php
//getting the database connection
require_once 'configuration.php';

//an array to display response
$response = array();

if ( $_POST['name'] && $_POST['price'] && $_POST['description'] ) {
	$name = $_POST['name'];
	$price = $_POST['price'];
	$description = $_POST['description'];

	$stmt =
		$conn->prepare( "INSERT INTO `products`(`name`, `price`, `description`) VALUES (?,?,?)" );
	$stmt->bind_param( "sss", $name, $price, $description );
	if ( $stmt->execute() == TRUE ) {
		$response['error'] = false;
		$response['message'] = "product created successfully!";
	} else {
		$response['error'] = true;
		$response['message'] = "failed\n " . $conn->error;
	}
} else {
	$response['error'] = true;
	$response['message'] = "Insufficient parameters";
}
echo json_encode( $response );
