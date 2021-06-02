$('.delete-btn').on('click', function() {
	if(confirm("本当に削除しますか？")) {
		const deleteid = $(this).val();
		console.log('deleteid:' + deleteid);
		//削除処理
		$.ajax(
			{
			url: '/delete',
			type: 'POST',
			data: {'deleteid': deleteid},
			}
		).then(
			function(data) {
				//通信成功時
				console.log(data);
				alert("削除しました");
				location.href="/";
			},
			function(data) {
				//通信失敗時
				console.log(data);
				alert("通信に失敗しました");
			}
		);
		} else {
			//キャンセル	
			return false;
		};
	});
	
	$('#createUser-btn').on('click', function() {
		alert("アカウント作成しました。");
	});
	
	$('.logout-btn').on('click', function() {
		if(confirm("ログアウトしますか？")){
		//ログアウト
		} else {
		return false;
		}
	
	});