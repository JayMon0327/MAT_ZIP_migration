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
			$("#btn-review-save").on("click", ()=>{
				this.reviewSave();
			});
		},

		save: function(){
			let formData = new FormData(document.getElementById("save-form"));

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
			let productId = $("#id").val();
			let formData = new FormData(document.getElementById("update-form"));

			$.ajax({
				type: "PUT",
				url: `/api/product/${productId}`,
				data: formData,
				processData: false, // 데이터를 query string으로 변환하지 않음 -> s3 배포시 해제
                contentType: false, // multipart/form-data로 설정 -> s3 배포시 해제
				dataType: "json"
			}).done(function(resp){
				alert("상품 수정이 완료되었습니다.");
				location.href = `/product/${productId}`;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});
		},

		reviewSave: function(){
			let formData = new FormData(document.getElementById("review-form"));
			let productId = $("#productId").val();

			$.ajax({
				type: "POST",
				url: `/api/product/${productId}/review`,
				data: formData,
                processData: false, // 데이터를 query string으로 변환하지 않음 -> s3 배포시 해제
                contentType: false, // multipart/form-data로 설정 -> s3 배포시 해제
                dataType: "json"
			}).done(function(resp){
				alert("댓글작성이 완료되었습니다.");
				location.href = `/product/${productId}`;
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