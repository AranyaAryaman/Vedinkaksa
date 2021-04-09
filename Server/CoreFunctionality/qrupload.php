<?php
// update the student's seat coordinates in the database.

define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );


$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );


$rolls = $_POST['name'];
$poss = $_POST['pos'];
$pox = (int)$_POST['posXX'];
$poy = (int)$_POST['posYY'];
$i_url = $_POST['iurl'];
error_log("\n >>> UPDATE image_tbl SET image_url='$i_url', roll='$rolls' WHERE PosX='$pox' and PosY='$poy'");
$sql = "SELECT * FROM image_tbl WHERE image_url='$i_url'";

$result = $con->query( $sql );

if ( $result->num_rows > 0 ) {
	$sql = "UPDATE image_tbl SET image_url='http://localhost/Sites/Server/CoreFunctionality/uploads/empty.jpg',roll='', State_in=0 WHERE image_url='$i_url'";
	mysqli_query( $con, $sql );

	$sql = "UPDATE image_tbl SET image_url='$i_url', roll='$rolls' WHERE PosX='$pox' and PosY='$poy'";

	if ( mysqli_query( $con, $sql ) ) {
		echo 'Data Inserted';
	} else {
		echo 'try again from data found';
	}

} else {
	$sql = "UPDATE image_tbl SET image_url='$i_url', roll='$rolls' WHERE PosX='$pox' and PosY='$poy'";


	if ( mysqli_query( $con, $sql ) ) {
		echo 'Data Inserted';
	} else {
		echo 'try again';
	}
}

mysqli_close( $con );


?>
 

 
