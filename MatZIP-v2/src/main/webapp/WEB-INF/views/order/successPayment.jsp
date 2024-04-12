<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div>
    <h3>결제 성공</h3>
    <p>판매자: <span id="order-name"></span></p>
    <p>결제 금액: <span id="amount"></span></p>
    <p>구매자: <span id="buyer-name"></span></p>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var successData = JSON.parse(sessionStorage.getItem('successData'));
        if (successData) {
            var orderName = successData.orderName;
            var amount = successData.amount;
            var buyerName = successData.buyerName;

            document.getElementById('order-name').textContent = orderName;
            document.getElementById('amount').textContent = amount;
            document.getElementById('buyer-name').textContent = buyerName;

            sessionStorage.removeItem('successData'); // 사용 후 데이터 삭제
        }
    });
</script>
<%@ include file="../layout/footer.jsp"%>
