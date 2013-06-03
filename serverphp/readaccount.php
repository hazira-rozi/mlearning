<?php
$link = mysql_connect('mysql12.000webhost.com', 'a2266320_mlearn', 'mlearning1.1') or die('Cannot connect to the DB');
mysql_select_db('a2266320_mlearn', $link) or die('Cannot select the DB');

/* grab the posts from the db */
$query = "SELECT user, password, nama, tipe, nis, email, kelas FROM account";
$result = mysql_query($query, $link) or die('Errorquery:  '.$query);

$rows = array();
while ($r = mysql_fetch_assoc($result)) {
    $rows[] = $r;
}
$data = "{account:".json_encode($rows)."}";
echo $data;
?>