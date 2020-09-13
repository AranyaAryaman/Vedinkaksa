<?php

require_once 'Visualization.php';

// return the current visualization states of all the students
$visualization = new Visualization();

$result =  $visualization->getAllVisaualizationStates();

$res = array();
while ( $row = $result->fetch_assoc() ) {
	$res[] = $row;
}

echo json_encode($res);