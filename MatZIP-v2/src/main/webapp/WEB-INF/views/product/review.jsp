<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<c:choose>
    <c:when test="${empty principal}">
        <div>로그인 후 리뷰 작성을 할 수 있습니다.</div>
    </c:when>
    <c:otherwise>
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
                        <label for="imageFiles"></label>
                        <input type="file" class="form-control" id="imageFiles" name="imageFiles" multiple required>
                    </div>
                    <div class="card-footer">
                    </div>
                </form>
                    <button type="button" id="btn-review-save" class="btn btn-primary">등록</button>
            </div>
    </c:otherwise>
</c:choose>

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