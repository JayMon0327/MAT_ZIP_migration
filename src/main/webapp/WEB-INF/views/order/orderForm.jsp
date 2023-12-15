<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <div>${principal.member.nickName}님</div>
    <div>적립금 현황 ${principal.member.point}원</div>
    <h2>주문 정보</h2>

    <!-- 배송지 입력 폼 -->
    <form id="paymentForm" action="/order/paymentForm" method="post">
        <h3>배송지 정보</h3>
        <!-- 배송지 정보 -->
        <div class="form-floating">
          <input type="text" class="form-control" name="address.city" id="addressCity" value="${order.address.city}" placeholder="Enter city">
          <label for="addressCity">City</label>
        </div>
        <div class="form-floating">
          <input type="text" class="form-control" name="address.street" id="addressStreet" value="${order.address.street}" placeholder="Enter street">
          <label for="addressStreet">Street</label>
        </div>
        <div class="form-floating">
          <input type="text" class="form-control" name="address.zipcode" id="addressZipcode" value="${order.address.zipcode}" placeholder="Enter zipcode">
          <label for="addressZipcode">Zipcode</label>
        </div>


        <h3>구매 정보</h3>
        <!-- 주문 항목 정보 -->
        <c:forEach items="${order.items}" var="item" varStatus="status">
            <div>
                <label>상품명:</label>
                <span>${item.itemName}</span>
                <input type="hidden" name="itemDtos[${status.index}].itemName" value="${item.itemName}" />
            </div>
            <div>
                <label>수량:</label>
                <span>${item.count}</span>
                <input type="hidden" name="itemDtos[${status.index}].count" value="${item.count}" />
            </div>
            <input type="hidden" name="itemDtos[${status.index}].itemId" value="${item.itemId}" />
            <!-- 가격 정보가 필요하다면 여기에 추가 -->
        </c:forEach>
        <div>
            <label>판매자:</label>
            <span>${order.storeId}</span>
            <input type="hidden" name="storeId" value="${order.storeId}" />
        </div>

        <div class="form-floating">
          <label>사용할 포인트 입력</label>
          <input type="text" class="form-control" name="usedPoint" id="usedPoint" placeholder="Enter point">
        </div>

        <input type="hidden" name="totalPrice" value="${order.totalPrice}" />
        <h3>총 가격: <span id="totalPrice">${order.totalPrice}원</span></h3>

        <button type="submit">결제하기</button>
    </form>
</div>

<%@ include file="../layout/footer.jsp"%>