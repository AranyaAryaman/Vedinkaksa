<?php
//getting the database connection
require_once 'configuration.php';

//an array to display response
$response = array();
if ( $_POST['id'] ) {
	$id = $_POST['id'];
	$stmt = $conn->prepare( "DELETE FROM products WHERE id = ?" );
	$stmt->bind_param( "s", $id );
	if ( $stmt->execute() ) {
		$response['error'] = false;
		$response['message'] = "Product Deleted Successfully!";
	} else {
		$response['error'] = true;
		$response['message'] = "Product Deleted Failed!";
	}
} else {
	$response['error'] = true;
	$response['message'] = "Insufficient Parameters";
}
echo json_encode( $response );
?>