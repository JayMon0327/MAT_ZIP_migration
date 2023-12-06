let index = {
		init: function(){
			$("#btn-save").on("click", ()=>{
				this.save();
			});
			$("#btn-delete").on("click", ()=>{
				this.deleteById();
			});
			$("#btn-update").on("click", ()=>{
				this.update();
			});
			$("#btn-reply-save").on("click", ()=>{
				this.replySave();
			});
		},

		save: function(){
			let formData = new FormData(document.getElementById("product-form"));

			$.ajax({
				type: "POST",
				url: "/api/product",
				data: formData,
				processData: false, // 데이터를 query string으로 변환하지 않음 -> s3 배포시 해제
				contentType: false, // multipart/form-data로 설정 -> s3 배포시 해제
				dataType: "json"
			}).done(function(resp){
				alert("상품 등록이 완료되었습니다.");
				location.href = "/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
		},

		deleteById: function(){
			let id = $("#id").text();

			$.ajax({
				type: "DELETE",
				url: "/api/product/"+id,
				dataType: "json"
			}).done(function(resp){
				alert("상품 삭제가 완료되었습니다.");
				location.href = "/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
		},

		update: function(){
			let id = $("#id").val();

			let data = {
                title: $("#title").val(),
                content: $("#description").val(),
                content: $("#price").val(),
                content: $("#stock").val()
			};

			$.ajax({
				type: "PUT",
				url: "/api/product/"+id,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json"
			}).done(function(resp){
				alert("상품 수정이 완료되었습니다.");
				location.href = "/";
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
		},

		replySave: function(){
			let data = {
					userId: $("#userId").val(),
					boardId: $("#boardId").val(),
					content: $("#reply-content").val()
			};

			$.ajax({
				type: "POST",
				url: `/api/board/${data.boardId}/reply`,
				data: JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				dataType: "json"
			}).done(function(resp){
				alert("댓글작성이 완료되었습니다.");
				location.href = `/board/${data.boardId}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
		},

		replyDelete : function(boardId, replyId){
			$.ajax({
				type: "DELETE",
				url: `/api/board/${boardId}/reply/${replyId}`,
				dataType: "json"
			}).done(function(resp){
				alert("댓글삭제 성공");
				location.href = `/board/${boardId}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
		},
}

index.init();