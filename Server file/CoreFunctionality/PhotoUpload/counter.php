<?php

//Updates the change in page Index

if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	$id0 = $_POST["pageno"];
	$id = (int)$id0;
	$id ++;
	$conn = mysqli_connect( 'localhost', 'nikhil', '160101047', 'webappdb' );
	$sql = "UPDATE pagecounter SET currentpage=$id WHERE id=1";
	$result = mysqli_query( $conn, $sql );

	mysqli_close( $conn );
}


?>