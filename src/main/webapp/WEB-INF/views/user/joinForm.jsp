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
          <input type="nickName" class="form-control" id="nickName" placeholder="Enter nickName">
          <label for="nickName">nickName</label>
        </div>
        <div class="form-floating">
          <input type="email" class="form-control" id="email" placeholder="Enter email">
          <label for="email">Email</label>
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
        <button class="btn btn-primary w-100 py-2" id="btn-save">회원가입 완료</button>

    </main>

<script src="/js/user.js"></script>
<%@ include file = "../layout/footer.jsp" %>

