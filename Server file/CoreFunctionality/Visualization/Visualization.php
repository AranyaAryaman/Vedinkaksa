<?php

require_once 'db.php';

class Visualization {

	private $db;
	public function __construct() {
		$this->db = new DbConnect();
	}

	// update the mental and visualization states of the student in the database
	function updateStates($student_id, $involvement, $activity, $arousal, $valence, $mental_state,
						  $visualization_state, $alert){
		$sql = "INSERT INTO temp_states (student_id, Involvement, Activity, Arousal, Valence, Mental_State, Visualization_State, Alert) VALUES ('$student_id', '$involvement', '$activity', '$arousal', '$valence', '$mental_state', '$visualization_state', '$alert')";

		$result = mysqli_query($this->db->getDb(), $sql);

		return $result;
	}

	// return visualization states of all the students in the class
	function getAllVisaualizationStates() {
		$sql = "SELECT Seat_arrangement.student_id, Seat_arrangement.seat_row, Seat_arrangement.seat_column, temp_states.Visualization_State FROM Seat_arrangement INNER JOIN temp_states on Seat_arrangement.student_id = temp_states.student_id";

		$result = mysqli_query($this->db->getDb(), $sql);

		return $result;
	}

}