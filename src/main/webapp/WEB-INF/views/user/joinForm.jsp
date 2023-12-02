<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file = "../layout/header.jsp" %>

    <main class="form-signin w-100 m-auto">
      <form>
        <h1 class="h3 mb-3 fw-normal">회원가입</h1>

        <div class="form-floating">
          <input type="username" class="form-control" id="username" placeholder="username">
          <label for="username">Username</label>
        </div>
        <div class="form-floating">
          <input type="password" class="form-control" id="password" placeholder="password">
          <label for="password">password</label>
        </div>
        <div class="form-floating">
          <input type="email" class="form-control" id="email" placeholder="Enter email">
          <label for="email">Email</label>
        </div>

      </form>
        <button class="btn btn-primary w-100 py-2" id="btn-save">회원가입 완료</button>

    </main>

<script src="/js/user.js"></script>
<%@ include file = "../layout/footer.jsp" %>

