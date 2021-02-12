<?php
//start attendance

require_once 'quiz.php';

$quizObject = new Quiz();

$json_quiz_objects = $quizObject->deleteAllQuizQuestions();

echo json_encode( $json_quiz_objects );

?>