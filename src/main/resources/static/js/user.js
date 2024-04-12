let index ={
    init: function(){
        $("#btn-save").on("click", (event)=>{ // ()=> this를 바인딩하기 위해서 사용
            event.preventDefault();
            this.save();
        });

         $("#btn-update").on("click", (event)=>{ // ()=> this를 바인딩하기 위해서 사용
            event.preventDefault();
            this.update();
        });

        $("#btn-password").on("click", (event)=>{ // ()=> this를 바인딩하기 위해서 사용
            event.preventDefault();
            this.password();
        });
    },

    save: function(){
        let data ={
            username: $("#username").val(),
            password: $("#password").val(),
            passwordCheck: $("#passwordCheck").val(),
            nickName: $("#nickName").val(),
            email: $("#email").val(),
            address: {
                    city: $("#addressCity").val(),
                    street: $("#addressStreet").val(),
                    zipcode: $("#addressZipcode").val()
                    }
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
                nickName: $("#nickName").val(),
                email: $("#email").val(),
                address: {
                        city: $("#addressCity").val(),
                        street: $("#addressStreet").val(),
                        zipcode: $("#addressZipcode").val()
                        }
            };

            $.ajax({
                type: "PUT",
                url: "/user",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function(resp){
                    alert("회원정보 수정이 완료되었습니다.");
                    location.href = "/";
            }).fail(function(error){
                console.log(error);
                if (error.responseJSON && error.responseJSON.message) {
                        alert(error.responseJSON.message);
                } else if (error.responseText) {
                    alert(error.responseText);
                } else {
                    alert("회원정보 수정에 실패하였습니다.");
                }
            });
        },

    password: function(){
            let data ={
                id: $("#id").val(),
                currentPassword: $("#currentPassword").val(),
                newPassword: $("#newPassword").val(),
                newPasswordCheck: $("#newPasswordCheck").val(),
            };

            $.ajax({
                type: "PUT",
                url: "/user/password",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function(resp){
                    alert("비밀번호 변경이 완료되었습니다.");
                    location.href = "/";
            }).fail(function(error){
                console.log(error);
                if (error.responseJSON && error.responseJSON.message) {
                        alert(error.responseJSON.message);
                } else if (error.responseText) {
                    alert(error.responseText);
                } else {
                    alert("비밀번호 변경에 실패하였습니다.");
                }
            });
        }
}

index.init();