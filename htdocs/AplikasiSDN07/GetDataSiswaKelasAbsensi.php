<?php 
	require_once 'Connection.php';

	$id_jadwal = $_POST['id_jadwal'];
	$query = "SELECT a.nama_kelas FROM jadwal b JOIN kelas a ON a.id_kelas=b.id_kelas WHERE b.id_jadwal = '$id_jadwal'";

	$result = mysqli_query($conn,$query);
	$status = "";
	$kelas= NULL;
	if(mysqli_num_rows($result)>0){
		$row = mysqli_fetch_assoc($result);
		$kelas = $row['nama_kelas'];
		$status = "succes";
		echo json_encode(array("response"=>$status,"kelas"=>$kelas));
	}else{
		$status = "Failed";
		echo json_encode(array("response"=>$status));
	}

	mysqli_close($conn);
 ?>