<?php 
require "Connection.php";

$id_mapel = $_POST['id_mapel'];
$nis = $_POST['nis'];

$sql = "SELECT * FROM nilai_siswa WHERE nis = '$nis' AND id_mapel = '$id_mapel'";

$result = mysqli_query($conn,$sql);
$status = "";
$nilai= NULL;
if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$nilai = $row['nilai'];
	$status = "Succes";
	echo json_encode(array("response"=>$status,"nilai"=>$nilai));
}else{
	$status = "Failed";
	echo json_encode(array("response"=>$status));
}

mysqli_close($conn);

 ?>