<?php
// returns the current slide info

$fp = fopen( 'current.txt', 'r' );
if ( feof( $fp ) ) {

} else {
	$name = fgets( $fp );
	$name = trim( $name );
	$path = "";
	if ( $name != "" ) {
		$path = "http://localhost/Sites/Server/CoreFunctionality/uploads/" . $name;
	}
	$page = fgets( $fp );
	$page = trim( "$page" );
	$temp = array(
		"name" => $name,
		"path" => $path,
		"page" => $page,
	);

	$x = json_encode( $temp );
	echo( $x );
}
