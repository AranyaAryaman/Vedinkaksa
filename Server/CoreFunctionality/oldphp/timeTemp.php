<?php

if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	$time = $_POST['time'];
	$conn = mysqli_connect( 'localhost', 'nikhil', '160101047', 'webappdb' );
	$sql = "INSERT INTO temp1 VALUES('$time')";
	$result = mysqli_query( $conn, $sql );
}
$conn = mysqli_connect( 'localhost', 'nikhil', '160101047', 'webappdb' );
$sql = "INSERT INTO temp1 VALUES('$time')";
$result = mysqli_query( $conn, $sql );

?>