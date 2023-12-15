        var IMP = window.IMP;
        IMP.init('imp85150734'); // 가맹점 식별코드

        function requestKakaoPay() {
            IMP.request_pay({
                pg: "kcp.TC0ONETIME", // PG사
                pay_method: "card", // 결제수단
                merchant_uid: "${paymentForm.paymentId}", // 주문번호
                name: "${paymentForm.storeId}", // 주문명
                amount: "${paymentForm.totalPrice}", // 결제 금액
                buyer_email: "${principal.member.email}", // 구매자 이메일
                buyer_name: "${principal.member.name}", // 구매자 이름
            }, function (rsp) {
                if (rsp.success) {
                    alert("결제 성공");
                    // 결제 성공 시 로직
                } else {
                    alert("결제 실패");
                    // 결제 실패 시 로직
                }
            });
        }

        function requestNaverPay() {
            IMP.request_pay({
                pg: "kcp.JayMonTest", // PG사
                pay_method: "card", // 결제수단
                merchant_uid: "${paymentForm.paymentId}", // 주문번호
                name: "${paymentForm.storeId}", // 주문명
                amount: "${paymentForm.totalPrice}", // 결제 금액
                buyer_email: "${principal.member.email}", // 구매자 이메일
                buyer_name: "${principal.member.name}", // 구매자 이름
            }, function (rsp) {
                if (rsp.success) {
                    alert("결제 성공");
                    // 결제 성공 시 로직
                } else {
                    alert("결제 실패");
                    // 결제 실패 시 로직
                }
            });
        }

        function requestTossPay() {
            IMP.request_pay({
                pg: "kcp.iamporttest_3", // PG사
                pay_method: "card", // 결제수단
                merchant_uid: "${paymentForm.paymentId}", // 주문번호
                name: "${paymentForm.storeId}", // 주문명
                amount: "${paymentForm.totalPrice}", // 결제 금액
                buyer_email: "${principal.member.email}", // 구매자 이메일
                buyer_name: "${principal.member.name}", // 구매자 이름
            }, function (rsp) {
                if (rsp.success) {
                    alert("결제 성공");
                    // 결제 성공 시 로직
                } else {
                    alert("결제 실패");
                    // 결제 실패 시 로직
                }
            });
        }
