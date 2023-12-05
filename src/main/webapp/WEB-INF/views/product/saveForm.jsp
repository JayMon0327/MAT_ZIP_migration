<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file = "../layout/header.jsp" %>
          <form>
            <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

            <div class="form-group">
              <input type="text" class="form-control" id="title">
            </div>

            <div class="form-group">
              <textarea class="form-control summernote" rows="5" id="content"></textarea>
            </div>
          </form>

            <button class="btn btn-primary w-100 py-2" id= "btn-save">글쓰기 완료</button>

<script>
$('.summernote').summernote({
  placeholder: 'Summernote for Bootstrap 5',
  tabsize: 2,
  height: 400,
  lang: 'ko-KR'
});
</script>

<script src="/js/product.js"></script>
<%@ include file = "../layout/footer.jsp" %>

