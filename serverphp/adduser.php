<?php
$user = $_GET['user'];
$password = $_GET['password'];
$tipe = $_GET['tipe'];
$nama = $_GET['nama'];
$nis = $_GET['nis'];
$email = $_GET['email'];
$kelas = $_GET['kelas'];

$link = mysql_connect('mysql12.000webhost.com', 'a2266320_mlearn', 'mlearning1.1') or die('Cannot connect to the DB');
mysql_select_db('a2266320_mlearn', $link) or die('Cannot select the DB');

/* grab the posts from the db */
$query = "insert into account (user,password,tipe,nama,nis,email,kelas) values('".$user."','".$password."','".$tipe."','".$nama."','".$nis."','".$email."','".$kelas."')";
$result = mysql_query($query, $link) or die('Error query:  '.$query);
echo "SUCCESS";

?>