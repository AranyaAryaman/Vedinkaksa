<?php


//Upadates the number of likes for a query
if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {

	$id = $_POST['q_id'];
	$id1 = (int)$id;


	$con = mysqli_connect( 'localhost', 'nikhil', '160101047', 'webappdb' );
	$sql = "INSERT INTO like_query(s_id,q_id) VALUES ('CSE035',$id1)";

	if ( mysqli_query( $con, $sql ) ) {
		//file_put_contents($path,base64_decode($image));
		echo "Successfully Uploaded";
	}

	mysqli_close( $con );
} else {
	echo "Error";
}