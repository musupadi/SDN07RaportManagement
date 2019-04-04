<?php 
require_once "Connection.php";
$nis = $_POST['nis'];
$id_mapel = $_POST['id_mapel'];

$sql = "SELECT * FROM nilai_siswa WHERE nis = '$nis' AND id_mapel = '$id_mapel'";

$result = mysqli_query($conn,$sql);
$status = null;
if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$ress = "Update";
}else{
	$ress = "Insert";
}

echo json_encode(array("response"=>$ress));

mysqli_close($conn);

 ?>