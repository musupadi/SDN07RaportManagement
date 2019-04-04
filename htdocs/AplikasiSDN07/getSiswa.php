<?php 
require "Connection.php";

$nis = $_POST['nis'];

$sql = "SELECT a.nis,a.nama_siswa,b.nama_kelas,a.profile_siswa FROM siswa a JOIN kelas b ON a.id_kelas=b.id_kelas WHERE nis = '$nis'";

$result = mysqli_query($conn,$sql);
$status = "";
$nis = null;
$nama_siswa = null;
$nama_kelas = null;
$profile_siswa = null;
if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$nis = $row['nis'];
	$nama_siswa = $row['nama_siswa'];
	$nama_kelas = $row['nama_kelas'];
	$profile_siswa = $row['profile_siswa'];
	$status = "succes";
	echo json_encode(array("response"=>$status,"nis"=>$nis,"nama_siswa"=>$nama_siswa,"nama_kelas"=>$nama_kelas,"profile_siswa"=>$profile_siswa));
}else{
	$status = "Failed";
	echo json_encode(array("response"=>$status));
}

mysqli_close($conn);

 ?>