let index ={
    init: function(){
        $("#btn-save").on("click", ()=>{ // ()=> this를 바인딩하기 위해서 사용
            this.save();
        });

         $("#btn-update").on("click", ()=>{ // ()=> this를 바인딩하기 위해서 사용
            this.update();
        });
    },

    save: function(){
        let data ={
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            type: "POST",
            url: "/auth/joinMember",
            data: JSON.stringify(data), //http body데이터
            contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
            dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면 => javascript로 변환해서 전달)
        }).done(function(resp){
                alert("회원가입이 완료되었습니다.");
                location.href = "/";
        }).fail(function(error){
            // 서버로부터 반환된 오류 메시지를 활용
            console.log(error);
            if (error.responseJSON && error.responseJSON.message) {
                    alert(error.responseJSON.message);
            } else if (error.responseText) {
                alert(error.responseText);
            } else {
                alert("회원가입에 실패하였습니다.");
            }
        });
    },

    update: function(){
            let data ={
                id: $("#id").val(),
                username: $("#username").val(),
                password: $("#password").val(),
                email: $("#email").val()
            };

            $.ajax({
                type: "PUT",
                url: "/user",
                data: JSON.stringify(data), //http body데이터
                contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지(MIME)
                dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열(생긴게 json이라면 => javascript로 변환해서 전달)
            }).done(function(resp){
                alert("회원수정이 완료되었습니다.");
                location.href ="/";
            }).fail(function(resp){
                alert(JSON.stringify(error));
            }); //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청
        }
}

index.init();