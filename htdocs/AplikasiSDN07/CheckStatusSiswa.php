<?php 
require "Connection.php";

$nis = $_POST['nis'];
$tanggal_masuk = $_POST["tanggal_masuk"];
$id_mapel = $_POST['id_mapel'];

$sql = "SELECT * FROM absensi_siswa WHERE nis = '$nis' AND tanggal_masuk  = '$tanggal_masuk' AND id_mapel = '$id_mapel'";

$result = mysqli_query($conn,$sql);
$status = "";
$status= NULL;
if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$status = $row['status'];
	$ress = "succes";
	echo json_encode(array("response"=>$ress,"status"=>$status));
}else{
	$ress = "Failed";
	echo json_encode(array("response"=>$ress));
}

mysqli_close($conn);

 ?>