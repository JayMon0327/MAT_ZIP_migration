<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file = "../layout/header.jsp" %>
<script>
    // 포트원 전역 변수 선언
    var paymentId = "${paymentForm.paymentId}";
    var storeId = "${paymentForm.storeId}";
    var finalPrice = "${paymentForm.finalPrice}";
    var userEmail = "${principal.member.email}";
    var userName = "${principal.member.nickName}";
    var usedPoint = "${paymentForm.usedPoint}";
    var orderId = "${paymentForm.order.id}";
</script>
<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
<script src="/js/portOne.js"></script>

<title>결제 페이지</title>

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

<%@ include file="../layout/footer.jsp"%>