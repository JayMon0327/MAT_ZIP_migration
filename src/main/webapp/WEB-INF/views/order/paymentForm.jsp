<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<!-- jQuery -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
    <title>결제 페이지</title>


<!-- PortOne SDK -->
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script>
    var paymentId = "${paymentForm.paymentId}";
    var storeId = "${paymentForm.storeId}";
    var finalPrice = "${paymentForm.finalPrice}";
    var userEmail = "${principal.member.email}";
    var userName = "${principal.member.name}";
    var IMP = window.IMP;
    IMP.init("imp85150734");

        function requestPay() {
            IMP.request_pay({
                pg: "kakaopay.TC0ONETIME", // PG사
                pay_method: "card", // 결제수단
                merchant_uid: paymentId, // 주문번호
                name: storeId, // 주문명
                amount: finalPrice, // 결제 금액
                buyer_email: userEmail, // 구매자 이메일
                buyer_name: userName, // 구매자 이름
            }, function (rsp) {
                if (rsp.success) {
                    if (rsp.success) {
                    // 결제 성공 시 서버로 결제 정보 전송
                    axios.post('/api/payment/complete', {
                        imp_uid: rsp.imp_uid,
                        merchant_uid: rsp.merchant_uid
                    }).then(function(response) {
                        // 성공 로직 처리
                        alert("결제 성공");
                    }).catch(function(error) {
                        // 에러 처리
                        console.error("결제 정보 전송 실패", error);
                    });
                } else {
                    // 결제 실패 시 로직
                    alert(`결제에 실패하였습니다. 에러 내용: ${rsp.error_msg}`);
                }
            }
            });
        }

        function requestNaverPay() {
            IMP.request_pay({
                pg: "naverco.JayMonTest", // PG사
                pay_method: "card", // 결제수단
                merchant_uid: paymentId, // 주문번호
                name: storeId, // 주문명
                amount: finalPrice, // 결제 금액
                buyer_email: userEmail, // 구매자 이메일
                buyer_name: userName, // 구매자 이름
            }, function (rsp) {
                if (rsp.success) {
                    if (rsp.success) {
                    // 결제 성공 시 서버로 결제 정보 전송
                    axios.post('/api/payment/complete', {
                        imp_uid: rsp.imp_uid,
                        merchant_uid: rsp.merchant_uid
                    }).then(function(response) {
                        // 성공 로직 처리
                        alert("결제 성공");
                    }).catch(function(error) {
                        // 에러 처리
                        console.error("결제 정보 전송 실패", error);
                    });
                } else {
                    // 결제 실패 시 로직
                    alert(`결제에 실패하였습니다. 에러 내용: ${rsp.error_msg}`);
                }
            }
            });
        }

        function requestTossPay() {
            IMP.request_pay({
                pg: "toss_brandpay.iamporttest_3", // PG사
                pay_method: "card", // 결제수단
                merchant_uid: paymentId, // 주문번호
                name: storeId, // 주문명
                amount: finalPrice, // 결제 금액
                buyer_email: userEmail, // 구매자 이메일
                buyer_name: userName, // 구매자 이름
            }, function (rsp) {
                if (rsp.success) {
                    if (rsp.success) {
                    // 결제 성공 시 서버로 결제 정보 전송
                    axios.post('/api/payment/complete', {
                        imp_uid: rsp.imp_uid,
                        merchant_uid: rsp.merchant_uid
                    }).then(function(response) {
                        // 성공 로직 처리
                        alert("결제 성공");
                    }).catch(function(error) {
                        // 에러 처리
                        console.error("결제 정보 전송 실패", error);
                    });
                } else {
                    // 결제 실패 시 로직
                    alert(`결제에 실패하였습니다. 에러 내용: ${rsp.error_msg}`);
                }
            }
            });
        }
</script>
</head>
<body>
    <h2>결제 정보</h2>
    <p>결제 ID: ${paymentForm.paymentId}</p>
    <p>스토어 ID: ${paymentForm.storeId}</p>
    <p>채널 키: ${paymentForm.channelKey}</p>

    <h2>주문 상품</h2>
        <c:forEach items="${paymentForm.orderItems}" var="orderItem">
            <p>상품명: ${orderItem.item.name}</p>
            <p>수량: ${orderItem.count}</p>
            <p>가격: ${orderItem.orderPrice}</p>
        </c:forEach>

    <button onclick="requestPay()">카카오결제하기</button>
    <button onclick="requestNaverPay()">네이버결제하기</button>
    <button onclick="requestTossPay()">토스결제하기</button>

</body>

</html>