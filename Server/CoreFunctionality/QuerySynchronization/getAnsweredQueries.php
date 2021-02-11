<?php

// get all the answers added after last answer added  timestamp

define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );

if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	if ( isset( $_POST['page'] ) && isset( $_POST['slide']) && isset($_POST['lastAnswerAdded'])) {
		$page = $_POST["page"];
		$slide = "'" . $_POST["slide"] . "'";
		$lastAnswerAdded = "'" . $_POST["lastAnswerAdded"] . "'";

		$sql = "SELECT q_id, is_answered, answer, answer_added FROM queries WHERE page = $page AND $slide LIKE $slide AND is_answered=1 and answer_added > $lastAnswerAdded";

		$result = $con->query( $sql );

		$res = array();
		while ( ($row = $result->fetch_assoc()) != null ) {
			$res[] = $row;
		}

		echo(json_encode($res));

	}
}
mysqli_close( $con );