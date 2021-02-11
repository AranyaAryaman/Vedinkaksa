<?php
// change the current slide info

if ( $_SERVER['REQUEST_METHOD'] == 'POST' ) {
	if ( isset( $_POST['name'] ) && isset( $_POST['page'] ) ) {
		$name = $_POST['name'];
		$page = $_POST['page'];
		$fp = fopen( 'current.txt', 'w' );
		fwrite( $fp, $name . PHP_EOL . $page );
		fclose( $fp );
	}
}