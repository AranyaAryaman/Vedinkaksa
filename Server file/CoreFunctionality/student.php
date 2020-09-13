<?php
// get student's state

$servername = "localhost";
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
$database = "db_images";

// Create connection
$con = new mysqli( $servername, $username, $password, $database ) or die( 'Connect failed: %s\n' );
// Check connection
if ( mysqli_connect_error( $con ) ) {
	echo "Connection failed: " . mysqli_connect_error;
}

$sql = "SELECT id, State_in, image_url,PosX, PosY FROM image_tbl";
$result = mysqli_query( $con, $sql );

if ( $result ) {
	// output data of each row
	while ( $row = mysqli_fetch_array( $result ) ) {
		$flag[] = $row;
	}
	print( json_encode( $flag ) );
}
mysqli_close( $con );

?>
