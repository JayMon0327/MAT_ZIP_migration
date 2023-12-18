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
    var usedPoint = "${paymentForm.usedPoint}";
    var orderId = "${paymentForm.order.id}";
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
                    alert("결제 성공");
                    location.href = "/";
                }).fail(function(error) {
                    console.error("결제 실패", error);
                });
            } else {
                alert(`결제에 실패하였습니다. 에러 내용: ${rsp.error_msg}`);
            }
        })};

</script>
</head>
<body>
    <h2>결제 정보</h2>
    <p>주문번호: ${paymentForm.paymentId}</p>
    <p>상점명: ${paymentForm.storeId}</p>

    <h2>주문 상품</h2>
        <c:forEach items="${paymentForm.orderItems}" var="orderItem">
            <p>상품명: ${orderItem.item.name}</p>
            <p>수량: ${orderItem.count}</p>
            <p>가격: ${orderItem.orderPrice}</p>
        </c:forEach>
        <p>총 주문금액: ${paymentForm.finalPrice}</p>

    <button onclick="requestPay()">카카오결제하기</button>
    <button onclick="requestNaverPay()">네이버결제하기</button>
    <button onclick="requestTossPay()">토스결제하기</button>

</body>

</html>