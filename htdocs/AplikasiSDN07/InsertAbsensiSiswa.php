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
	$ress = "Update";
}else{
	$sqlInsert = "INSERT INTO absensi_siswa(nip,nis,id_mapel,status,tanggal_masuk,id_kelas) VALUES ('$nip','$nis','$id_mapel','$status','$tanggal_masuk','$id_kelas');";
	if(mysqli_query($conn,$sqlInsert)){
		$ress = "Insert";
	}else{
		$ress = "error";
	}
}

echo json_encode(array("response"=>$ress));

mysqli_close($conn);


 ?>