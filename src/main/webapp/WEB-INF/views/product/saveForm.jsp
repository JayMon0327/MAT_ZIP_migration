<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file = "../layout/header.jsp" %>

          <form id="save-form" enctype="multipart/form-data">
              <!-- 상품 정보 -->
              <div class="form-group">
                  <label for="productDto.title">상품명</label>
                  <input type="text" class="form-control" id="title" name="productDto.title" required>
              </div>
              <div class="form-group">
                  <label for="productDto.description">상품 설명</label>
                  <textarea class="form-control" id="description" name="productDto.description" required></textarea>
              </div>
              <div class="form-group">
                  <label for="imageFiles">이미지 파일들</label>
                  <input type="file" class="form-control" id="imageFiles" name="productDto.imageFiles" multiple required>
              </div>

              <!-- 아이템 정보 -->
              <div class="form-group">
                  <label for="items[0].name">아이템 이름</label>
                  <input type="text" class="form-control" id="items[0].name" name="items[0].name" required>
              </div>
              <div class="form-group">
                  <label for="items[0].price">가격</label>
                  <input type="number" class="form-control" id="items[0].price" name="items[0].price" required>
              </div>
              <div class="form-group">
                  <label for="items[0].stock">재고 수량</label>
                  <input type="number" class="form-control" id="items[0].stock" name="items[0].stock" required>
              </div>

          </form>
              <button type="button" id="add-item">아이템 추가</button>
          <button class="btn btn-primary w-100 py-2" id="btn-save">상품 등록</button>

<script src="/js/item.js"></script>
<script src="/js/product.js"></script>
<%@ include file = "../layout/footer.jsp" %>

