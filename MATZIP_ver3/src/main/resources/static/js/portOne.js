    var IMP = window.IMP;
    IMP.init("imp85150734");

    function requestPay() {
        IMP.request_pay({
            pg: "kakaopay.TC0ONETIME",
            pay_method: "card",
            merchant_uid: paymentId,
            name: storeId,
            amount: finalPrice,
            buyer_email: userEmail,
            buyer_name: userName,
        }, function (rsp) {
            if (rsp.success) {
                $.ajax({
                    url: "/api/payment/order",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        imp_uid: rsp.imp_uid,
                        merchant_uid: rsp.merchant_uid,
                        amount: rsp.paid_amount,
                        usedPoint: usedPoint,
                        orderId: orderId
                    }),
                }).done(function(response) {
                    sessionStorage.setItem('successData', JSON.stringify(response));
                    location.href = "/payment/successPage";
                }).fail(function(error) {
                    console.error("결제 실패", error);
                });
            } else {
                alert(`결제에 실패하였습니다. 에러 내용: ${rsp.error_msg}`);
            }
        })};

