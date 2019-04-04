<?php 
require_once "Connection.php";
$nis = $_POST["nis"];
$nilai = $_POST['nilai'];
$id_mapel = $_POST['id_mapel'];

$sql = "SELECT * FROM nilai_siswa WHERE nis = '$nis' AND id_mapel = '$id_mapel'";

$result = mysqli_query($conn,$sql);

if(mysqli_num_rows($result)>0){
	$sqlUpdate = "UPDATE nilai_siswa SET nilai = '$nilai' WHERE nis = '$nis' AND id_mapel = '$id_mapel'";
	if(mysqli_query($conn,$sqlUpdate)){
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