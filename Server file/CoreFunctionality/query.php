<?php 
 define('HOST','localhost');
 define( 'USER', 'nikhil' );
 define( 'PASS', '160101047' );
 define('DB','student');
 
 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect...');

$sql = "SELECT phoneno from info where attendance<60";
$result = mysqli_query($con, $sql);

if (mysqli_num_rows($result) > 0) {
    // output data of each row
	$jso = [];
    while($row = mysqli_fetch_assoc($result)) {
		echo "no: " . $row["phoneno"];
		$no = ( $row["phoneno"] );
		$jso[] = $row;
	}
	// wrong line need to be changed
	print(json_encode(($jso)));
 } else {
	echo "hello";
}
mysqli_close($con);

?>