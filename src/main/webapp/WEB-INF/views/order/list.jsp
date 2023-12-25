<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

Copy code
<h1>결제 내역</h1>
<table>
    <thead>
        <tr>
            <th>결제 ID</th>
            <th>주문 ID</th>
            <th>결제 금액</th>
            <th>결제 일시</th>
            <th>조치</th>
        </tr>
    </thead>
    <tbody>
            <c:forEach var="payment" items="${payments.content}">
                <tr>
                    <td><c:out value="${payment.impUid}" /></td>
                    <td><c:out value="${payment.orderId}" /></td>
                    <td><c:out value="${payment.amount}" /></td>
                    <td><c:out value="${payment.createDate}" /></td>
                    <div class="form-group">
                        <input type="hidden" id="orderId" value="${payment.orderId}"/>
                    </div>
                    <td><button class="btn-cancel-payment" id="btn-cancel-payment">주문 취소</button></td>
                </tr>
            </c:forEach>
        </tbody>
</table>

    <!-- 페이지네이션 -->
    <div>
        <c:if test="${payments.hasPrevious()}">
            <a href="${pageContext.request.contextPath}/payment/detail/${memberId}?page=${payments.number - 1}">이전</a>
        </c:if>

        <c:forEach begin="0" end="${payments.totalPages - 1}" var="pageNum">
            <a href="${pageContext.request.contextPath}/payment/detail/${memberId}?page=${pageNum}">${pageNum + 1}</a>
        </c:forEach>

        <c:if test="${payments.hasNext()}">
            <a href="${pageContext.request.contextPath}/payment/detail/${memberId}?page=${payments.number + 1}">다음</a>
        </c:if>
    </div>

<script src="/js/paymentCancel.js"></script>
<%@ include file="../layout/footer.jsp"%>
