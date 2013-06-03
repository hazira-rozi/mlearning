<?php
$link = mysql_connect('mysql12.000webhost.com', 'a2266320_mlearn', 'mlearning1.1') or die('Cannot connect to the DB');
mysql_select_db('a2266320_mlearn', $link) or die('Cannot select the DB');

/* grab the posts from the db */
$query = "SELECT kelas, pelajaran, judul, diskripsi, kategori, url, user FROM materi";
$result = mysql_query($query, $link) or die('Errorquery:  '.$query);

$rows = array();
while ($r = mysql_fetch_assoc($result)) {
    $rows[] = $r;
}
$data = "{materi:".json_encode($rows)."}";
echo $data;
?>