<?php
define( 'HOST', 'localhost' );
define( 'USER', 'nikhil' );
define( 'PASS', '160101047' );
define( 'DB', 'db_images' );

$upload_path = 'uploads/';

//Getting the server ip
$server_ip = gethostbyname( gethostname() );

//creating the upload url
$upload_url = 'http://localhost/Sites/Server/CoreFunctionality/' . $upload_path;

//response array
$response = array();


if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	echo( '$_FILES:' );
	print_r( $_FILES );
	//checking the required parameters from the request
	if ( isset( $_POST['name'] ) and isset( $_FILES['image']['name'] ) and
									 isset( $_POST['roll'] ) and isset( $_POST['pass'] ) ) {

		//connecting to the database
		$con = mysqli_connect( HOST, USER, PASS, DB ) or die( 'Unable to Connect...' );

		//getting name from the request
		$name = $_POST['name'];
		$roll = $_POST['roll'];
		$pass = $_POST['pass'];
		print_r( $name . ',' . $roll . ',' . $pass );
		//getting file info from the request
		$fileinfo = pathinfo( $_FILES['image']['name'] );

		//getting the file extension
		$extension = $fileinfo['extension'];

		//file url to store in the database
		$file_url = $upload_url . getFileName() . '.jpg';

		//file path to upload in the server
		$file_path = $upload_path . getFileName() . '.jpg';

		//trying to save the file in the directory
		try {
			//saving the file
			if ($extension == "jpg" || $extension == "jpeg" ){
				$imagetemp = imagecreatefromjpeg($_FILES['image']['tmp_name']);
				$valid_extension = 1;
			} elseif ($extension == "png" ){
				$imagetemp = imagecreatefrompng($_FILES['image']['tmp_name']);
				$valid_extension = 1;
			} elseif ($extension == "bmp" ){
				$imagetemp = imagecreatefrombmp($_FILES['image']['tmp_name']);
				$valid_extension = 1;
			} else {
				$valid_extension = 0;
			}
			if ($valid_extension == 1) {
				imagejpeg($imagetemp, $file_path, 80);
			} else {
				move_uploaded_file( $_FILES['image']['tmp_name'], $file_path);
			}

			$sql = "INSERT INTO images (id, url, name, roll, pass) VALUES ('$roll', '$file_url', '$name', '$roll', '$pass')";

			if ( mysqli_query( $con, $sql ) ) {

				//filling response array with values
				$response['error'] = false;
				$response['url'] = $file_url;
				$response['name'] = $name;
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
		$response['message'] = 'Please choose a file';
	}
}
echo "CC";
/*
We are generating the file name
so this method will return a file name for the image to be upload
*/
function getFileName() {
	return $_POST['roll'];
}
