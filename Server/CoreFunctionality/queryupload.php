<?php
// Add asked questions from the student in the db

define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );

$que = $_POST["query"];
$v = 0;

$sql = "INSERT INTO queries(query, votes) VALUES('$que','$v')";

//$result=$con->query($sql);


if ( mysqli_query( $con, $sql ) ) {
	echo 'Data Inserted';
} else {
	echo 'try again from data found';
}

mysqli_close( $con );
?>
 

 
