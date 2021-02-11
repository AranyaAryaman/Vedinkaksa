<?php
//getting the database connection
require_once 'configuration.php';

//an array to display response
$response = array();
if ( $_POST['id'] ) {
	$id = $_POST['id'];
	$stmt = $conn->prepare( "SELECT name,price,description FROM products WHERE id = ?" );
	$stmt->bind_param( "s", $id );
	$result = $stmt->execute();
	if ( $result == TRUE ) {
		$response['error'] = false;
		$response['message'] = "Retrieval Successful!";
		$stmt->store_result();
		$stmt->bind_result( $name, $price, $description );
		$stmt->fetch();
		$response['name'] = $name;
		$response['price'] = $price;
		$response['description'] = $description;
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