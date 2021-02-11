<?php

// add a new question, options and answer to the quiz database

//getting the database connection
require_once 'configuration.php';

//an array to display response
$response = array();

if ( $_POST['question_text'] && $_POST['option1'] && $_POST['option2'] && $_POST['option3'] &&
	 $_POST['option4'] && $_POST['correct_answer'] ) {
	$question = $_POST['question_text'];
	$options = array();
	$options[] = $_POST['option1'];
	$options[] = $_POST['option2'];
	$options[] = $_POST['option3'];
	$options[] = $_POST['option4'];
	$correct_answer = $_POST['correct_answer'];

	$stmt =
		$conn->prepare( "INSERT INTO `quiz_questions` (`question`, `option1`, `option2`, `option3`, `option4`, `correct_answer`) VALUES (?,?,?,?,?,?)" );
	$stmt->bind_param( "ssssss", $question, $options[0], $options[1], $options[2], $options[3],
		$correct_answer );
	if ( $stmt->execute() == TRUE ) {
		$response['error'] = false;
		$response['message'] = "question added successfully!";
	} else {
		$response['error'] = true;
		$response['message'] = "failed\n " . $conn->error;
	}
} else {
	$response['error'] = true;
	$response['message'] = "Insufficient parameters";
}
echo json_encode( $response );
