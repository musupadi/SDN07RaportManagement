<?php 
	require_once 'Connection.php';

	$nama_kelas = $_POST['nama_kelas'];

	$query = "SELECT * FROM kelas a WHERE nama_kelas = '$nama_kelas'";

	$result = mysqli_query($conn,$query);
	$id_kelas= NULL;
	if(mysqli_num_rows($result)>0){
		$row = mysqli_fetch_assoc($result);
		$id_kelas = $row['id_kelas'];
		$status = "succes";
		echo json_encode(array("response"=>$status,"id_kelas"=>$id_kelas));
	}else{
		$status = "failed";
		echo json_encode(array("response"=>$status));
	}

	mysqli_close($conn);
 ?>