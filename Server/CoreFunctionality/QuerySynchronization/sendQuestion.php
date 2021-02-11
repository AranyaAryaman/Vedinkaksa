<?php

// Add the asked question to the database

define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );
function add_quotes($str) {
	return  "'" . $str . "'";
}
if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	if ( isset( $_POST['question'] ) && isset( $_POST['page'] ) && isset( $_POST['slide'] ) ) {
		$ques = add_quotes($_POST['question']);
		$page = add_quotes($_POST['page']);
		$slide = add_quotes($_POST['slide']);

		$sql = "INSERT INTO queries(query, page, slide) values ($ques, $page, $slide)";
		echo "Hey";
		if ($con->query($sql) === TRUE) {
			echo "New record created successfully";
		} else {
			echo "Error: " . $sql . "<br>" . $con->error;
		}

	}
}
mysqli_close( $con );
