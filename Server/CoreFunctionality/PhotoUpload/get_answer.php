<?php

//Fetches answer of a qery from database

if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	$id0 = $_POST["q_id"];
	$id = (int)$id0;
	$conn = mysqli_connect( 'localhost', 'nikhil', '160101047', 'webappdb' );
	$sql = "SELECT * FROM doubts WHERE q_id='$id'";
	$result = mysqli_query( $conn, $sql );
	$response = array();
	$row = mysqli_fetch_array( $result );
	array_push( $response, array( "q_id" => $row[0], "query" => $row[1], "answer" => $row[2] ) );
	/*while($row=mysqli_fetch_array($result)){
		array_push($response,array("q_id"=>$row[0],"query"=>$row[1],"answer"=>$row[2]));
	}*/

	if ( $response != NULL ) {
		echo json_encode( array( "server_response" => $response ) );
	} else {
		echo "No queries yet";
	}
	mysqli_close( $conn );
}


?>