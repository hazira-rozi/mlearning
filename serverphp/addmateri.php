<?php
$kelas = $_GET['kelas'];
$pelajaran = $_GET['pelajaran'];
$judul = $_GET['judul'];
$diskripsi = $_GET['diskripsi'];
$kategori = $_GET['kategori'];
$url = $_GET['url'];
$user = $_GET['user'];

$link = mysql_connect('mysql12.000webhost.com', 'a2266320_mlearn', 'mlearning1.1') or die('Cannot connect to the DB');
mysql_select_db('a2266320_mlearn', $link) or die('Cannot select the DB');

/* grab the posts from the db */
$query = "insert into materi (kelas,pelajaran,judul,diskripsi,kategori,url,user) values('".$kelas."','".$pelajaran."','".$judul."','".$diskripsi."','".$kategori."','".$url."','".$user."')";
$result = mysql_query($query, $link) or die('Error query:  '.$query);
echo "SUCCESS";

?>