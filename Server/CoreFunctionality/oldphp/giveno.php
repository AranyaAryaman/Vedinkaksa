<?php

$conn = mysqli_connect( 'localhost', 'nikhil', '160101047', 'webappdb' );
$sql = "SELECT currentpage FROM pagecounter WHERE id=1";
$result = mysqli_query( $conn, $sql );
$row = mysqli_fetch_array( $result );
echo $row[0];

?>