<?php 
	require_once 'Connection.php';

	$id_kelas = $_POST['id_kelas'];


	$query = "SELECT a.nama_mapel,b.tingkat_kelas FROM mata_pelajaran a JOIN kelas b ON a.id_kelas=b.id_kelas WHERE a.id_kelas = '$id_kelas'";

	$result = mysqli_query($conn,$query);

	$array = array();

	while($row = mysqli_fetch_assoc($result)){
		$array[] = $row;
	}


	echo ($result) ?
	json_encode(array("kode" => 1,"result" => $array)) :
	json_encode(array("kode" => 0,"result" => "Data Tidak Ditemukan"));
 ?>