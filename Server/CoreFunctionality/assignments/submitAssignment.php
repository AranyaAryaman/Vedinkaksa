<?php
$servername = "localhost";
$username = "nikhil";
$password = "160101047";
$dbname = "db_images";
$PdfUploadFolder = "../uploads/";

// get current name
ob_start();
include 'getAssignmentsStatus.php';
$name = ob_get_clean();
// end: get ***

// attendance not started
if ($name == ''){
	die("");
}

if ( (!isset( $_POST['roll'] ) )|| (!isset( $_FILES['pdf']['name']))) {
	die("");
}

$roll = $_POST['roll'];

$PdfFileFinalPath = $PdfUploadFolder . $name . "_" . $roll . ".pdf";

if (file_exists($PdfFileFinalPath)) {
   unlink($PdfFileFinalPath);
}

move_uploaded_file( $_FILES['pdf']['tmp_name'], $PdfFileFinalPath );

?>
