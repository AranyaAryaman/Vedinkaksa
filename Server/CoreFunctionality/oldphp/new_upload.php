<?php
define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

$upload_path = 'uploads/';
//Getting the server ip
$server_ip = "192.168.1.6"; //gethostbyname(gethostname());
//creating the upload url
$upload_url = 'http://' . $server_ip . '/Sites/Server/CoreFunctionality/' . $upload_path;
//response array
$response = array();
if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
//checking the required parameters from the request
//  if(isset($_POST['name']) and isset($_FILES['upload_image']['name']) and isset($_POST['roll']) and isset($_POST['pass'])){
//connecting to the database
	$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );
//getting name from the request
//  $name = $_POST['name'];
//  $roll= $_POST['roll'];
//  $pass= $_POST['pass'];
//getting file info from the request
	$fileinfo = pathinfo( $_FILES['upload_image']['name'] );
//getting the file extension
	$extension = $fileinfo['extension'];
//file url to store in the database
	$file_url = $upload_url . getFileName() . '.' . $extension;
//file path to upload in the server
	$file_path = $upload_path . getFileName() . '.' . $extension;

//trying to save the file in the directory
	try {
//saving the file
		move_uploaded_file( $_FILES['upload_image']['tmp_name'], $file_path );
		chmod( $file_path, 0777 );

		$sql = "INSERT INTO slides ( image_url) VALUES ('$file_url')";
//  $sql1 = "CREATE TABLE `db_images`.`".$roll."` ( `Time` DATETIME NOT NULL,`Mental_State` TEXT NOT NULL ,`Derived_State` TEXT NOT NULL, `alert` INT  )";
		mysqli_query( $con, $sql );

		if ( mysqli_query( $con, $sql ) ) {
//filling response array with values
			$response['error'] = false;
			$response['image_url'] = $file_url;
//$response['name'] = $name;
		}
//if some error occurred
	}
	catch ( Exception $e ) {
		$response['error'] = true;
		$response['message'] = $e->getMessage();
	}
//displaying the response
	echo json_encode( $response );
//closing the connection
	mysqli_close( $con );
} else {
	$response['error'] = true;
	$response['message'] = 'No file found';
}
echo "CC";
/*
We are generating the file name
so this method will return a file name for the upload_image to be upload
*/
function getFileName() {
//  return $_POST['roll'];
}



