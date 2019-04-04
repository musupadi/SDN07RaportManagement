<?php 
require "Connection.php";

$username = $_POST['username'];

$sql = "SELECT * FROM guru WHERE nip = '$username'";

$result = mysqli_query($conn,$sql);
$status = "";
$password=NULL;
$nama= NULL;
$tempatlahir = NULL;
$tanggalahir = NULL;
$agama = NULL;
$notelp = NULL;
$jabatan = NULL;
$pendidikan = NULL;
$jk = NULL;
$pictureguru = NULL;
$alamat = NULL;
if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$password = $row['password'];
	$nama = $row['nama'];
	$tempatlahir = $row['tempatlahir'];
	$tanggalahir = date("d-m-Y", strtotime($row['tanggalahir']));
	$agama = $row['agama'];
	$notelp = $row['notelp'];
	$jabatan = $row['jabatan'];
	$pendidikan = $row['pendidikan'];
	$jk = $row['jk'];
	$pictureguru = $row['pictureguru'];
	$alamat = $row['alamat'];
	$status = "succes";
	echo json_encode(array("response"=>$status,"NIP"=>$username,"password"=>$password,"nama"=>$nama,"tempatlahir"=>$tempatlahir,"tanggalahir"=>$tanggalahir,"agama"=>$agama,"notelp"=>$notelp,"jabatan"=>$jabatan,"pendidikan"=>$pendidikan,"jk"=>$jk,"pictureguru"=>$pictureguru,"alamat"=>$alamat));
}else{
	$status = "Failed";
	echo json_encode(array("response"=>$status));
}

mysqli_close($conn);

 ?>