<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<h1>결제 내역</h1>
    <table>
        <thead>
            <tr>
                <th>결제 ID</th>
                <th>주문 ID</th>
                <th>결제 금액</th>
                <th>결제 일시</th>
                <!-- 추가적인 필드에 대한 헤더 -->
            </tr>
        </thead>
        <tbody>
            <c:forEach var="payment" items="${payments}">
                <tr>
                    <td><c:out value="${payment.impUid}" /></td>
                    <td><c:out value="${payment.order.id}" /></td>
                    <td><c:out value="${payment.amount}" /></td>
                    <td><c:out value="${payment.createdAt}" /></td>
                    <!-- 추가적인 필드에 대한 데이터 -->
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 페이지네이션 -->
    <div>
        <c:if test="${payments.hasPrevious()}">
            <a href="${pageContext.request.contextPath}/payment/${memberId}?page=${payments.number - 1}">이전</a>
        </c:if>

        <c:forEach begin="0" end="${payments.totalPages - 1}" var="pageNum">
            <a href="${pageContext.request.contextPath}/payment/${memberId}?page=${pageNum}">${pageNum + 1}</a>
        </c:forEach>

        <c:if test="${payments.hasNext()}">
            <a href="${pageContext.request.contextPath}/payment/${memberId}?page=${payments.number + 1}">다음</a>
        </c:if>
    </div>


<%@ include file="../layout/footer.jsp"%>
