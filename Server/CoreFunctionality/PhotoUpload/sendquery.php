<?php

//Used for posting queries as well as taking note taking timings

if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	$conn = mysqli_connect( 'localhost', 'nikhil', '160101047', 'webappdb' );
	$method = $_POST['method'];
	if ( $method == 'asking' ) {
		$doubt = $_POST['query'];
		$sql = "INSERT INTO doubts(query) VALUES ('$doubt')";
	} else {
		if ( $method == 'start' ) {
			$starttime = $_POST['query'];
			$sql =
				"INSERT INTO studentnotesact(s_id,takingnotesstart) VALUES ('CSE035','$starttime')";
		} else {
			if ( $method == 'stop' ) {
				$starttime = $_POST['stop'];
				$stoptime = $_POST['query'];
				//$sql="INSERT INTO studentnotesact(s_id,takingnotesstop) VALUES ('CSE035','$stop')";
				$sql =
					"UPDATE studentnotesact SET takingnotesstop='$stoptime' WHERE takingnotesstart='$starttime'";
			}
		}
	}
	$result = mysqli_query( $conn, $sql );
}

?>