<?php 
require "Connection.php";

$username = $_POST['username'];
$password = $_POST["password"];

$sql = "SELECT * FROM guru WHERE nip = '$username' AND password  = '$password'";

$result = mysqli_query($conn,$sql);
$status = "";
$name= NULL;
if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$name = $row['nama'];
	$status = "succes";
	echo json_encode(array("response"=>$status,"Nama"=>$name));
}else{
	$status = "Failed";
	echo json_encode(array("response"=>$status));
}

mysqli_close($conn);

 ?>