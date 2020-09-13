<?php

// get all the new queries (queries asked after the last question added timestamp)
define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );

if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	if ( isset( $_POST['page'] ) && isset( $_POST['slide']) && isset($_POST['lastQuestionAdded'])) {
		$page = $_POST["page"];
		$slide = "'" . $_POST["slide"] . "'";
		$lastAnswerAdded = "'" . $_POST["lastQuestionAdded"] . "'";

		$sql = "SELECT * FROM queries WHERE page = $page AND $slide LIKE $slide AND timestamp > $lastAnswerAdded";

		$result = $con->query( $sql );

		$res = array();
		while ( ($row = $result->fetch_assoc()) != null ) {
			$res[] = $row;
		}

		echo(json_encode($res));
	}
}
mysqli_close( $con );