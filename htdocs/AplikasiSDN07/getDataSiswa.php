<?php 
require "Connection.php";

$nis = $_POST['nis'];

$sql = "SELECT * FROM siswa WHERE nis = '$nis'";

$result = mysqli_query($conn,$sql);
$status = "";
$nama_siswa= NULL;
if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$nama_siswa = $row['nama_siswa'];
	$profile_siswa = $row['profile_siswa'];
	$status = "succes";
	echo json_encode(array("response"=>$status,"nama_siswa"=>$nama_siswa,"profile_siswa"=>$profile_siswa));
}else{
	$status = "Failed";
	echo json_encode(array("response"=>$status));
}

mysqli_close($conn);

 ?>