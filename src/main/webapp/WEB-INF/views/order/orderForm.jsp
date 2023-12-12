<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <h2>주문 정보</h2>

    <!-- 배송지 입력 폼 -->
    <form id="paymentForm" action="/payment" method="post">
        <h3>배송지 정보</h3>
        <label>주소: <input type="text" name="address" /></label><br />
        <label>우편번호: <input type="text" name="zipcode" /></label><br />

        <h3>구매 정보</h3>
        <c:forEach items="${order.items}" var="item">
            <div>상품명: ${item.itemName}</div>
            <div>수량: ${item.itemStock}</div>
            <input type="hidden" name="itemId" value="${item.itemId}" />
            <input type="hidden" name="itemName" value="${item.itemName}" />
            <input type="hidden" name="itemStock" value="${item.itemStock}" />
            <!-- 가격 정보가 필요하다면 여기에 추가 -->
        </c:forEach>
        <input type="hidden" name="sellerName" value="${order.sellerName}" />

        <h3>총 가격: <span id="totalPrice"></span></h3>

        <button type="submit">결제하기</button>
    </form>
</div>

<%@ include file="../layout/footer.jsp"%>