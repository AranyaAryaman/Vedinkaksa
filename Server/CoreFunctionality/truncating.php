<?php

$servername = "localhost";
$username = "nikhil";
$password = "160101047"; 
$database = "db_images";


if ($_SERVER['REQUEST_METHOD'] != 'POST') {
	die();
}

// Create connection
$con = new mysqli( $servername, $username, $password, $database ) or die( 'Connect failed: %s\n' );
// Check connection
if ( mysqli_connect_error( $con ) ) {
	echo "Connection failed: " . mysqli_connect_error;
}

$default_url = "http://192.168.1.6/Sites/Server/CoreFunctionality/uploads/empty.jpg";
$defroll = "empty";
$sql = "TRUNCATE TABLE image_tbl;";
$sqlq = "TRUNCATE TABLE queries;";
$column = $_POST['column'];
$row = $_POST['row'];
$defstate = "DS0";

mysqli_query( $con, $sql );
mysqli_query( $con, $sqlq );

for ( $i = 1; $i <= $row; $i ++ ) {
	for ( $j = 1; $j <= $column; $j ++ ) {

		$sql1 =
			"INSERT INTO image_tbl (image_url,PosX,PosY,State_in,roll) VALUES ('$default_url','$i','$j','$defstate','$defroll')";
		$result = mysqli_query( $con, $sql1 );
		echo json_encode( $result );
		echo $i;
		echo $j;
	}
}

if ( $result ) {
	print( json_encode( $result ) );
}


mysqli_close( $con );
?>
