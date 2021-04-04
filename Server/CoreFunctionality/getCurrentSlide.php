<?php
$servername = "localhost";
$username = "nikhil";
$password = "160101047";
$dbname = "db_images";


$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
	die("false");
}

$sql = "SELECT `name`, `page` from slide_sync";

$result = $conn->query( $sql );
if ( $result->num_rows == 1 ) {
	$row = $result->fetch_assoc();
	$res = array(
		"name" => $row['name'],
		"page" => $row['page'],
		"path" => "http://localhost/Sites/Server/CoreFunctionality/uploads/" . $row['name'],
	);
	echo (json_encode($res));
} else {
	die("false");
}
mysqli_close($conn);
?>
