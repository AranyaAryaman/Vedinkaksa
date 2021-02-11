<?php

include_once 'db.php';

class Quiz {

	private $db;

	private $db_table = "quiz_questions";

	public function __construct() {

		$this->db = new DbConnect();

	}

	// return all the quiz questions
	public function getAllQuizQuestions() {

		$json_array = array();

		$query = "select * from quiz_questions";

		$result = mysqli_query( $this->db->getDb(), $query );

		if ( mysqli_num_rows( $result ) > 0 ) {

			while ( $row = mysqli_fetch_assoc( $result ) ) {

				$json_array[] = $row;

			}

		}
		return $json_array;
	}

	// delete all the quiz questions
	public function deleteAllQuizQuestions() {
		$query = "DELETE from quiz_questions";

		$result = mysqli_query( $this->db->getDb(), $query );

		return $result;
	}

}

?>