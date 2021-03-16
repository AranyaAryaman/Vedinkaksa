<?php
// Authenticate the user

require_once 'configuration.php'; 
  
$sql = "SELECT status FROM attendance_table where class='CS'";
$result = mysqli_query( $this->db->getDb(), $sql );
return json_encode($result);

 
 
?>
