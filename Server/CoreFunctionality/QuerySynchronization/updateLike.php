<?php

// Update the like count for a specific question

define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );
function add_quotes($str) {
	return  "'" . $str . "'";
}
if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	if ( isset( $_POST['q_id'] ) ) {
		$qid = $_POST['q_id'];
//		$page = add_quotes($_POST['page']);
//		$slide = add_quotes($_POST['slide']);

		$sql = "UPDATE queries SET likes = likes + 1 WHERE q_id=$qid";
		if ($con->query($sql) === TRUE) {
			echo "New record created successfully";
		} else {
			echo "Error: " . $sql . "<br>" . $con->error;
		}
	}
}
mysqli_close( $con );