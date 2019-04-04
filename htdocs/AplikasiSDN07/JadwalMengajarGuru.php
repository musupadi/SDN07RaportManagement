<?php 
	require_once 'connection.php';

	$username=$_POST['username'];
	$hari_ini=$_POST['hari_ini'];
	$query = "SELECT a.id_jadwal,a.id_kelas,d.nip,b.nama_kelas,c.nama_mapel,a.dari_jam,a.sampai_jam,a.hari,a.id_mapel FROM jadwal a JOIN kelas b ON a.id_kelas=b.id_kelas JOIN mata_pelajaran c ON a.id_mapel=c.id_mapel JOIN guru d ON a.nip=d.nip WHERE d.nip = '$username' AND a.hari = '$hari_ini' ORDER BY a.dari_jam";

	$result = mysqli_query($conn,$query);

	$array = array();

	while($row = mysqli_fetch_assoc($result)){
		$array[] = $row;
	}


	echo ($result) ?
	json_encode(array("kode" => 1,"result" => $array)) :
	json_encode(array("kode" => 0,"result" => "Data Tidak Ditemukan"));
 ?>