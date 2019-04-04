<?php 
require_once "Connection.php";
$nis = $_POST["nis"];
$nip = $_POST['nip'];
$nilai = $_POST['nilai'];
$id_mapel = $_POST['id_mapel'];

$sql = "SELECT * FROM nilai_siswa WHERE nis = '$nis' AND id_mapel = '$id_mapel'";

$result = mysqli_query($conn,$sql);

if(mysqli_num_rows($result)>0){
	$ress = "Update";
}else{
	$sqlInsert = "INSERT INTO nilai_siswa(nis,nip,nilai,id_mapel) VALUES ('$nis','$nip','$nilai','$id_mapel');";
	if(mysqli_query($conn,$sqlInsert)){
		$ress = "Insert";
	}else{
		$ress = "Error";
	}
}

echo json_encode(array("response"=>$ress));

mysqli_close($conn);


 ?>

 