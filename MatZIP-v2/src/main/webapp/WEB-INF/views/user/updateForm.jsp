<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file = "../layout/header.jsp" %>

    <main class="form-signin w-100 m-auto">
      <form>
        <input type="hidden" id="id" value="${principal.member.id}"/>
        <h1 class="h3 mb-3 fw-normal">회원 정보 수정</h1>

        <div class="form-floating">
          <label for="username">Username</label>
          <input type="username" value="${principal.member.username}" class="form-control"
          id="username" placeholder="username" readonly>
        </div>

        <c:if test="${empty principal.member.provider}">
        <div class="form-floating">
          <label for="password">password</label>
          <input type="password" class="form-control"
          id="password" placeholder="password">
        </div>
        <div class="form-floating">
          <label for="passwordCheck">passwordCheck</label>
          <input type="password" class="form-control"
          id="passwordCheck" placeholder="passwordCheck">
        </div>
        </c:if>

        <div class="form-floating">
          <input type="nickName" class="form-control" id="nickName" placeholder="Enter nickName">
          <label for="nickName">nickName</label>
        </div>
        <div class="form-floating">
          <label for="email">Email</label>
          <input type="email" value="${principal.member.email}" class="form-control" id="email" placeholder="Enter email">
        </div>
        <div class="form-floating">
          <input type="text" class="form-control" id="addressCity" placeholder="Enter city">
          <label for="addressCity">City</label>
        </div>
        <div class="form-floating">
          <input type="text" class="form-control" id="addressStreet" placeholder="Enter street">
          <label for="addressStreet">Street</label>
        </div>
        <div class="form-floating">
          <input type="text" class="form-control" id="addressZipcode" placeholder="Enter zipcode">
          <label for="addressZipcode">Zipcode</label>
        </div>

      </form>
        <button class="btn btn-primary w-100 py-2" id="btn-update">수정완료</button>

    </main>

<script src="/js/user.js"></script>
<%@ include file = "../layout/footer.jsp" %>

