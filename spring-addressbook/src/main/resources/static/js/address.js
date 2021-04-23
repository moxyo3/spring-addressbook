$('#delete').submit(function() {
	if(confirm('本当に削除しますか？')) {
		//削除処理
		alert('削除しました');
	} else {
		return false;
    }
});