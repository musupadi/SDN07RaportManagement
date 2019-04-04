<?php 
	require_once 'Connection.php';

	$id_kelas = $_POST['id_kelas'];
	$id_mapel = $_POST['id_mapel'];

	$query = "SELECT a.nis,a.nama_siswa,a.profile_siswa,a.id_kelas,c.nip,b.id_mapel FROM siswa a JOIN jadwal b ON a.id_kelas = b.id_kelas JOIN guru c ON b.nip=c.nip WHERE a.id_kelas = '$id_kelas' AND b.id_mapel = '$id_mapel' ORDER BY a.nis";

	$result = mysqli_query($conn,$query);

	$array = array();

	while($row = mysqli_fetch_assoc($result)){
		$array[] = $row;
	}


	echo ($result) ?
	json_encode(array("kode" => 1,"result" => $array)) :
	json_encode(array("kode" => 0,"result" => "Data Tidak Ditemukan"));
 ?>