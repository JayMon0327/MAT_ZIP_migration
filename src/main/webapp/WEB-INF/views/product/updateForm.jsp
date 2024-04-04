<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file = "../layout/header.jsp" %>
        <form id="update-form" enctype="multipart/form-data">
            <div class="form-group">
                <input type="hidden" id="id" value="${products.id}"/>
            </div>
            <!-- 상품 정보 -->
            <div class="form-group">
                <label for="productDto.title">상품명</label>
                <input type="text" class="form-control" id="title" name="productDto.title" value="${products.title}" required>
            </div>
            <div class="form-group">
                <label for="productDto.description">상품 설명</label>
                <textarea class="form-control" id="description" name="productDto.description" required>${products.description}</textarea>
            </div>
            <div class="form-group">
                <label for="imageFiles">이미지 파일들</label>
                <input type="file" class="form-control" id="imageFiles" name="productDto.imageFiles" multiple required>
            </div>
            <!-- 아이템 정보 -->
            <c:forEach items="${products.items}" var="item" varStatus="status">
                <div class="form-group">
                    <label for="items[${status.index}].id"></label>
                    <input type="hidden" class="form-control" id="items[${status.index}].id" name="items[${status.index}].id" value="${item.id}" required>
                </div>
                <div class="form-group">
                    <label for="items[${status.index}].name">아이템 이름</label>
                    <input type="text" class="form-control" id="items[${status.index}].name" name="items[${status.index}].name" value="${item.name}" required>
                </div>
                <div class="form-group">
                    <label for="items[${status.index}].price">가격</label>
                    <input type="number" class="form-control" id="items[${status.index}].price" name="items[${status.index}].price" value="${item.price}" required>
                </div>
                <div class="form-group">
                    <label for="items[${status.index}].stock">재고 수량</label>
                    <input type="number" class="form-control" id="items[${status.index}].stock" name="items[${status.index}].stock" value="${item.stock}" required>
                </div>
            </c:forEach>
        </form>
        <button type="button" id="add-item">아이템 추가</button>
        <button class="btn btn-primary w-100 py-2" id="btn-update">상품 수정</button>

<script src="/js/product.js"></script>
<%@ include file = "../layout/footer.jsp" %>

