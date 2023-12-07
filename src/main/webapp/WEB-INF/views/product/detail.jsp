<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>

    <c:if test="${products.member.id == principal.member.id}">
        <a href="/product/${products.id}/updateForm" class="btn btn-warning">수정</a>
        <button id="btn-delete" class="btn btn-danger">삭제</button>
    </c:if>

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
        <div>${item.name} - 가격: ${item.price}, 재고: ${item.stockQuantity}</div>
    </c:forEach>

    <div class="card">
            <form id="review-form" enctype="multipart/form-data">
                <div class="form-group">
                <input type="hidden" id="memberId" name="memberId" value="${principal.member.id}" />
                </div>
                <div class="form-group">
                <input type="hidden" id="productId" name="productId" value="${products.id}" />
                </div>
                <div class="card-body, form-group">
                    <label for="content">리뷰내용</label>
                    <textarea id="content" class="form-control" name="content" rows="1"></textarea>
                </div>
                <div class="card-body, form-group">
                    <label for="rating">평점</label>
                    <textarea id="rating" class="form-control" name="rating" rows="1"></textarea>
                </div>
                 <div class="card-body, form-group">
                    <label for="imageFiles">이미지 파일들</label>
                    <input type="file" class="form-control" id="imageFiles" name="imageFiles" multiple required>
                </div>
                <div class="card-footer">
                </div>
    		</form>
                <button type="button" id="btn-review-save" class="btn btn-primary">등록</button>
    	</div>
    	<br />
    	<div class="card">
    		<div class="card-header">댓글 리스트</div>
    		<ul id="review-box" class="list-group">
    			<c:forEach var="review" items="${products.reviews}">

    				<li id="review-${review.id}" class="list-group-item d-flex justify-content-between">
    					<div>${review.content}</div>
    					<!-- 이미지 정보 표시 -->
                        <c:forEach items="${review.images}" var="image">
                            <img src="/api/images/${image.storeFileName}" width="150" height="150" alt="Review Image">
                        </c:forEach>
    					<div class="d-flex">
    						<div class="font-italic">작성자 : ${review.member.username} &nbsp;</div>
    						<c:if test="${review.member.id eq principal.member.id}">
    							<button onClick="index.reviewDelete(${products.id}, ${review.id})" class="badge">삭제</button>
    						</c:if>

    					</div>
    				</li>

    			</c:forEach>
    		</ul>
    	</div>



</div>

<script src="/js/product.js"></script>
<%@ include file="../layout/footer.jsp"%>
