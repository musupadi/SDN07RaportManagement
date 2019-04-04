<?php 

require_once "Connection.php";
$nip = $_POST["nip"];
$nis = $_POST['nis'];
$id_mapel = $_POST['id_mapel'];
$status = $_POST['status'];
$tanggal_masuk = $_POST['tanggal_masuk'];
$id_kelas = $_POST['id_kelas'];

$sql = "SELECT * FROM absensi_siswa WHERE nis = '$nis' AND tanggal_masuk = '$tanggal_masuk' AND id_mapel = '$id_mapel'";

$result = mysqli_query($conn,$sql);

if(mysqli_num_rows($result)>0){
	$sqlInsert = "UPDATE absensi_siswa SET status = '$status' WHERE nis = '$nis' AND tanggal_masuk = '$tanggal_masuk' AND id_mapel = '$id_mapel'";
	if(mysqli_query($conn,$sqlInsert)){
		$ress = "Update";
	}else{
		$ress = "error";
	}
}else{
	$ress = "Insert";
}

echo json_encode(array("response"=>$ress));

mysqli_close($conn);


 ?>