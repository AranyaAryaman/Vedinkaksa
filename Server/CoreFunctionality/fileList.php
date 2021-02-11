<?php
define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

//Return all the slides
$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );

$sql = "SELECT id, name, path FROM slides";

$result = $con->query( $sql );

$res = array();
while ( $row = $result->fetch_assoc() ) {
	$res[] = $row;
}
print( json_encode( ( $res ) ) );
mysqli_close( $con );