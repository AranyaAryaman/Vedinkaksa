<?php

//Fetches q_id, query and their respective answers in a single table as well as the page counter

$conn = mysqli_connect( 'localhost', 'nikhil', '160101047', 'webappdb' );

$response = array();

$sql =
	"SELECT doubts.q_id,query,answer,count(like_id) AS likes,currentpage FROM pagecounter,like_query RIGHT OUTER JOIN doubts ON doubts.q_id=like_query.q_id GROUP BY q_id ORDER BY likes DESC";
$result = mysqli_query( $conn, $sql );
while ( $row = mysqli_fetch_array( $result ) ) {
	array_push( $response, array(
		"q_id" => $row[0],
		"query" => $row[1],
		"answer" => $row[2],
		"likes" => $row[3],
		"counter" => $row[4],
	) );
}


if ( $response != NULL ) {
	echo json_encode( array( "server_response" => $response ) );
} else {
	echo "No queries yet";
}
mysqli_close( $conn );

?>
