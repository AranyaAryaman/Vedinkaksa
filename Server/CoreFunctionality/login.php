<?php
// Authenticate the user

define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );

$roll_check = $_GET['rollcheck'];

$sql = "SELECT roll, name, pass,url FROM images where roll ='$roll_check'";

$result = $con->query( $sql );

if ( $result->num_rows > 0 ) {	
	$row[] = $result->fetch_assoc();
	$_SESSION['user'] = $row; 
	print( json_encode( ( $row ) ) );
} else {
	echo "hello";
}

mysqli_close( $con );

?>

