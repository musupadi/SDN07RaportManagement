<?php 
	require_once 'Connection.php';

	$nama_mapel = $_POST['nama_mapel'];

	$query = "SELECT * FROM mata_pelajaran a WHERE nama_mapel = '$nama_mapel'";

	$result = mysqli_query($conn,$query);
	$id_mapel= NULL;
	if(mysqli_num_rows($result)>0){
		$row = mysqli_fetch_assoc($result);
		$id_mapel = $row['id_mapel'];
		$status = "succes";
		echo json_encode(array("response"=>$status,"id_mapel"=>$id_mapel));
	}else{
		$status = "failed";
		echo json_encode(array("response"=>$status));
	}

	mysqli_close($conn);
 ?>