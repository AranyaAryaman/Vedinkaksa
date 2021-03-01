<?php

 
define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );

$temp = $_POST['attendance_status'];
  
$sql = "UPDATE attendance_table SET status = '.$temp.' WHERE class='CS'";

if ($conn->query($sql) === TRUE) {
  echo json_encode($temp);
} 

else {
  echo json_encode("no");
}
 
?>
