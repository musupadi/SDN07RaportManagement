<?php 
require_once "Connection.php";
$nis = $_POST['nis'];
$tanggal_masuk = $_POST['tanggal_masuk'];
$id_mapel = $_POST['id_mapel'];

$sql = "SELECT * FROM absensi_siswa WHERE nis = '$nis' AND tanggal_masuk = '$tanggal_masuk' AND id_mapel = '$id_mapel'";

$result = mysqli_query($conn,$sql);
$status = null;
if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$ress = "Tidak";
	$status = $row['status'];
}else{
	$ress = "Iya";
	$status = "Tidak Ada Keterangan";
}

echo json_encode(array("response"=>$ress,"status"=>$status));

mysqli_close($conn);

 ?>