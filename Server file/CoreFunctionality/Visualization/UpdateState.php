<?php

// Update the student's mental and visualisation state in the database
require_once 'Visualization.php';

$visualization = new Visualization();

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$student_id = $_POST['student_id'];
	$valence = $_POST['Valence'];
	$arousal = $_POST['Arousal'];
	$involvement = $_POST['Involvement'];
	$activity = $_POST['Activity'];
	$mental_state = $_POST['Mental_state'];
	$visualization_state = $_POST['Visualization_state'];
	$alert = $_POST['Alert'];

	$result = $visualization->updateStates($student_id, $involvement, $activity, $arousal,
		$valence, $mental_state, $visualization_state, $alert);

	return $result;
}