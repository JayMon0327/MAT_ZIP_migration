<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>

    <c:if test="${products.member.id == principal.member.id}">
        <a href="/product/${products.id}/updateForm" class="btn btn-warning">수정</a>
        <button id="btn-delete" class="btn btn-danger">삭제</button>
    </c:if>
    <!-- 상품 게시판 표시 -->

    <br /> <br />
    <div>상품 번호 : <span id="id"><i>${products.id}</i></span> 판매자 : <span><i>${products.member.username}</i></span></div>
    <br />
    <h3>${products.title}</h3>
    <div>${products.description}</div>

    <!-- 이미지 정보 표시 -->
    <c:forEach items="${products.images}" var="image">
        <img src="/api/images/${image.storeFileName}" width="300" height="300" alt="Product Image">
    </c:forEach>

    <!-- 아이템 정보 표시 -->
    <c:forEach items="${products.items}" var="item">
        <div>${item.name} - 가격: ${item.price}, 재고: ${item.stock}</div>
    </c:forEach>

<br />
    <!-- 상품 주문하기 표시 -->

    <!-- 주문 정보 표시 -->
    <select id="productSelect">
      <option value="">선택</option>
      <c:forEach items="${products.items}" var="item">
        <option value="${item.id}">${item.name}</option>
      </c:forEach>
    </select>
    <div id="selectedProducts"></div>

    <br />

    <form id="orderForm" action="/order" method="post">
      <!-- 여기에 동적으로 상품 ID와 수량 input 태그가 추가됩니다 -->
      <!-- 판매자 ID -->
      <input type="hidden" name="storeId" value="${products.member.username}" />
    </form>
    <button id="orderButton">주문하기</button>

    </div>

<%@ include file="review.jsp"%>
<script src="/js/product.js"></script>
<script src="/js/orderForm.js"></script>
<%@ include file="../layout/footer.jsp"%>
