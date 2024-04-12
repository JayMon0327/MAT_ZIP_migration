let index = {
    init: function(){
        $(".btn-cancel-payment").on("click", function(){
            let orderId = $(this).data("order-id");
            index.cancelPayment(orderId);
        });
    },

    cancelPayment: function(orderId){
        $.ajax({
            type: "DELETE",
            url: "/api/order/" + orderId,
            dataType: "json"
        }).done(function(resp){
            alert("주문 취소가 완료되었습니다.");
            location.reload();
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    }
}

index.init();
