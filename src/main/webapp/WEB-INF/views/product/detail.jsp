<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>

    <c:if test="${products.member.id == principal.member.id}">
        <a href="/product/${product.id}/updateForm" class="btn btn-warning">수정</a>
        <button id="btn-delete" class="btn btn-danger">삭제</button>
    </c:if>

    <br /> <br />
    <div>상품 번호 : <span id="id"><i>${products.id}</i></span> 판매자 : <span><i>${products.member.username}</i></span></div>
    <br />
    <h3>${products.title}</h3>
    <div>${products.description}</div>

    <!-- 이미지 정보 표시 -->
    <c:forEach items="${products.images}" var="image">
        <img src="/images/${image.storeFileName}" width="300" height="300" alt="Product Image">
    </c:forEach>

    <!-- 아이템 정보 표시 -->
    <c:forEach items="${products.items}" var="item">
        <div>${item.name} - 가격: ${item.price}, 재고: ${item.stockQuantity}</div>
    </c:forEach>
</div>

<%@ include file="../layout/footer.jsp"%>
