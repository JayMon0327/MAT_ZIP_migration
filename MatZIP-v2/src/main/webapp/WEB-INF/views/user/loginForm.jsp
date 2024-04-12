<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file = "../layout/header.jsp" %>

    <div class="container">
    <!-- 시큐리티는 x-www-form-url-encoded 타입만 인식 -->

    	<form action="/login" method="post">
    		<div class="form-group">
    			<label for="username">Username</label>
    			<input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
    		</div>

    		<div class="form-group">
    			<label for="password">Password</label>
    			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
    		</div>

    		<button id="btn-login" class="btn btn-primary">로그인</button>
    	</form>
            <br />
            <a href="/oauth2/authorization/kakao">
            <img src="/image/kakao_login_button.png"
                alt="kakao" height="38px">
            </a>

            <a href="/oauth2/authorization/naver">
            <img src="/image/naver_login_button.png"
                alt="naver" height="38px">
            </a>

            <a href="/oauth2/authorization/google" >
            <img src="/image/google_login_button.png"
                alt="google" height="38px">
            </a>

            <a href="/oauth2/authorization/facebook">
            <img src="/image/facebook_login_button.png"
                alt="facebook" height="38px">
            </a>

    </div>

<%@ include file = "../layout/footer.jsp" %>

