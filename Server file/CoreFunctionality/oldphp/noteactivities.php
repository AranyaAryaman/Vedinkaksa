<?php

$conn = mysqli_connect( 'localhost', 'nikhil', '160101047', 'webappdb' );

$response = array();

$sql = "SELECT * FROM studentnotesact";
$result = mysqli_query( $conn, $sql );
while ( $row = mysqli_fetch_array( $result ) ) {
	array_push( $response, array(
		"notes_id" => $row[0],
		"s_id" => $row[1],
		"start_time" => $row[2],
		"stop_time" => $row[3],
	) );
}

if ( $response != NULL ) {
	echo json_encode( array( "server_response" => $response ) );
} else {
	echo "No queries yet";
}
mysqli_close( $conn );
?>