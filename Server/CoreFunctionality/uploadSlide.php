<?php
// upload slide

ini_set('display_errors', 1);
ini_set('log_errors',1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

ServerConfig();

$ServerURL = 'http://192.168.1.6/Sites/Server/CoreFunctionality/uploads';

if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {

	if ( isset( $_POST['name'] ) and isset( $_FILES['pdf']['name'] ) ) {

		$con = mysqli_connect( HostName, HostUser, HostPass, DatabaseName );

		$PdfName = $_POST['name'];

		$PdfInfo = pathinfo( $_FILES['pdf']['name'] );

		$PdfFileExtension = $PdfInfo['extension'];

		$PdfFileURL = $ServerURL . $PdfName;

		$PdfFileFinalPath = $PdfUploadFolder . $PdfName;

		try {

			move_uploaded_file( $_FILES['pdf']['tmp_name'], $PdfFileFinalPath );
			$InsertTableSQLQuery = "INSERT INTO slides (path, name) VALUES ('$PdfFileURL', '$PdfName') ;";

			mysqli_query( $con, $InsertTableSQLQuery );

		}
		catch ( Exception $e ) {
			print( "error in uploading" );
		}
		mysqli_close( $con );

	}
}

function ServerConfig() {

	define( 'HostName', 'localhost' );
	define( 'HostUser', 'nikhil' );
	define( 'HostPass', '160101047' );
	define( 'DatabaseName', 'db_images' );

}

function GenerateFileNameUsingID() {

	$con2 = mysqli_connect( HostName, HostUser, HostPass, DatabaseName );

	$GenerateFileSQL = "SELECT max(id) as id FROM slides";

	$Holder = mysqli_fetch_array( mysqli_query( $con2, $GenerateFileSQL ) );

	mysqli_close( $con2 );

	if ( $Holder['id'] == null ) {
		return 1;
	} else {
		return ++ $Holder['id'];
	}
}

