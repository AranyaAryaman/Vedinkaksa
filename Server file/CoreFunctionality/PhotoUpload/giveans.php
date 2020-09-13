<?php

//Used for posting answer to respective query

if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	$id0 = $_POST["q_id"];
	$id = (int)$id0;
	$ans = $_POST["ans"];
	/*if($ans=="")
		$ans='Khali hei';*/
	$conn = mysqli_connect( 'localhost', 'nikhil', '160101047', 'webappdb' );
	$sql = "UPDATE doubts SET answer='" . $ans . "' WHERE q_id=$id";
	$result = mysqli_query( $conn, $sql );

	mysqli_close( $conn );
}


?>