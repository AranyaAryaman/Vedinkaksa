<?php
// delete all the quiz questions

require_once 'quiz.php';

$quizObject = new Quiz();

$json_quiz_objects = $quizObject->deleteAllQuizQuestions();

echo json_encode( $json_quiz_objects );