<?php 
require "Connection.php";

$id_kelas = $_POST['id_kelas'];

$sql = "SELECT * FROM kelas WHERE id_kelas = '$id_kelas'";

$result = mysqli_query($conn,$sql);
$status = "";
$nama_kelas= NULL;
if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$nama_kelas = $row['nama_kelas'];
	$status = "succes";
	echo json_encode(array("response"=>$status,"nama_kelas"=>$nama_kelas));
}else{
	$status = "Failed";
	echo json_encode(array("response"=>$status));
}

mysqli_close($conn);

 ?>