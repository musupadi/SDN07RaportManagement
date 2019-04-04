<?php 
require "Connection.php";

$nis = $_POST['nis'];

$sql = "SELECT * FROM raport_siswa WHERE nis = '$nis'";

$result = mysqli_query($conn,$sql);
$status = "";
$izin = null;
$alpa = null;
$sakit = null;
if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$status = "succes";
	$sakit = $row['sakit'];
	$izin = $row['izin'];
	$alpa = $row['alpa'];
	
	echo json_encode(array("response"=>$status,"sakit"=>$sakit,"alpa"=>$alpa,"izin"=>$izin));
}else{
	$status = "Failed";
	echo json_encode(array("response"=>$status));
}

mysqli_close($conn);

 ?>