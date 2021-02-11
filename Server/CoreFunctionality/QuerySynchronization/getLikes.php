<?php

// get the like count for all the questions on a page in the slide
define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );

if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	if ( isset( $_POST['page'] ) && isset( $_POST['slide'] ) ) {
		$page = $_POST["page"];
		$slide = "'" . $_POST["slide"] . "'";

		$sql = "SELECT q_id, likes FROM queries WHERE page = $page AND slide LIKE $slide";

		$result = $con->query( $sql );

		$res = array();
		while ( ($row = $result->fetch_assoc()) != null ) {
			$res[] = $row;
		}

		echo(json_encode($res));

	}
}
mysqli_close( $con );