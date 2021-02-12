<?php

include_once 'db.php';

class Attendance{
    private $db;
    private $db_table = "attendance_start";

    public function __construct() {
		$this->db = new DbConnect();
    }
    
    public function start_attendance(){
        $json_array = array();
        $current_id = $_SESSION['user']['id'];
        $query = "insert into attendance_start ('id') VALUES (current_id)";
        $result = mysqli_query( $this->db->getDb(), $query );
        return $result;
    }
}

?>