<?php
// change the current slide info
$servername = "localhost";
$username = "nikhil";
$password = "160101047";
$dbname = "db_images";

if ( $_SERVER['REQUEST_METHOD'] != 'POST' ) {
	die("false");
}

if ( !isset( $_POST['name'] ) ||  (!isset( $_POST['page'] ) )) {
	die("false");
}

$name = $_POST['name'];
$page = $_POST['page'];

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
	die("false");
}

$sql = 'UPDATE slide_sync SET `name`="'.$name.'", `page`='.$page;
if(mysqli_query($conn, $sql)){
	echo("true");
} else {
	echo("false");
}
mysqli_close($conn);
?>
