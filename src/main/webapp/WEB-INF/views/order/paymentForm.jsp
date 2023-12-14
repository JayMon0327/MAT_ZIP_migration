<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--STEP 1-->
<!--포트원 SDK 가져오기-->
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script th:inline="javascript"></script>

    var paymentId = [[${paymentId}]];
    var storeId = [[${storeId}]];
    var channelKey = [[${channelKey}]];
    var naverpay_channelKey = [[${naverPayChannelKey}]];
    var kakaopay_channelKey = [[${kakaoPayChannelKey}]];


    function payment(payMethod, easyPayProvider, isHub){
    //STEP 2
    //파라미터 셋팅 후 결제창 호출
    //고객정보
    const customer = {
        customerId : 'cid-'+[[${user.id}]],
        fullName : [[${user.name}]],
        phoneNumber : [[${user.phone}]],
        email : [[${user.email}]]
        };

        var paymentData ={

            storeId: storeId,
            paymentId: paymentId,
            payMethod: payMethod,
            orderName: [[${product.name}]],
            totalAmount: [[${product.amount}]],
            currency : 'KRW',
            customer : customer,
            customData: [[${order.id}]],
            confirmUrl: 'https://fb4f-182-220-157-203.ngrok-free.app/v2/order/confirm',
            redirectUrl: 'https://fb4f-182-220-157-203.ngrok-free,app/v2/payment/redirect',

        };

        var easyPay = {
            easyPayProvider: easyPayProvider
        };

        //일반 신용카드 결제시
        //'payMethod' : 'CARD'

        //허브형 간편 결제시
        //'payMethod' : 'CARD'
        //'easyPayProvider' : 'NAVERPAY', 'KAKAOPAY', 'APPLEPAY' 등 간편결제사 코드
        //channelKey에 PG사의 키

        // 직연동 간편 결제 시
        //'payMethod' : 'EASY_PAY'
        //channelKey에 간편결제사의 키

        if(payMethod == 'EASY_PAY'){
            paymentData.easyPay = easyPay;
            if(easyPayProvider == "NAVERPAY" && isHub == false){
                paymentData.channelKey = naverpay_channelKey;
            } else if(easyPayProvider == "KAKAOPAY" && isHub == false){
                paymentData.channelKey = kakaopay_channelKey;
            } else if(isHub == true){
                paymentData.channelKey = channelKey;
            }
        } else{
            paymentData.channelKey = channelKey;
        }

        const response = PortOne.requestPayment(paymentDate)
                .then(function(response){
                    console.log(response); //응답 객체가 개발자 도구 - 콘솔에 출력된다.
                    alert('response 수신:'+JSON.stringify(response));
                    //STEP 3
                    //가맹점 백엔드 서버로 결과를 가져온다.
                    jQuery.ajax({
                        url: "/v2/payment/callback",
                        method: "POST",
                        headers: {"Content-Type": "application/json"},
                        data: JSON.stringify(response)
                    }).done(function(data){
                        //STEP6
                        alert("서버 처리 결과 : " + data.status);
                        //콜백에서 처리한 결과를 사용자에게 표시하거나 결제내역 페이지로 이동할 수 있다.
                    });
                });
}