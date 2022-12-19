<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.example.demo.controller.navigation.AttributeParameterHolder" %>
<%@ page import="com.example.demo.controller.command.CommandType" %>
<fmt:setLocale value="${sessionScope.localization}" scope="session"/>
<fmt:setBundle basename="localization.localizedtext"/>

<html lang="${AttributeParameterHolder.SESSION_SCOPE_ATTRIBUTE_PARAMETER_LOCALIZATION}">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <jsp:include page="/view/fragment/head_links.jsp"/>
  <title><fmt:message key="title.delete_account"/></title>
</head>
<body>
<header class="header">
  <div class="container">
    <div class="header__inner">
      <nav class="header__nav">
        <div class="header__menu">
          <div class="header__menu-item">
            <a style="font-size: 60px; color: gray" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_DETAILS}">
              <fmt:message key="text.delete_account.title"/>
            </a>
          </div>
        </div>
      </nav>
      <button class="header__signIn-btn">
        <a href="${pageContext.request.contextPath}/home?command=${CommandType.SIGN_OUT}">
          <fmt:message key="button.signout"/>
        </a>
      </button>

      <a class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_DETAILS}">
        <fmt:message key="text.other.back_to_profile"/>
      </a>
    </div>
  </div>
</header>
<main class="main">
  <div class="sign">
    <div class="container">
      <form action="home" method="post">
        <div class="sign__inner" style="margin-top: 100px">
          <label><fmt:message key="label.delete_account"/></label> <br/>
          <label>
            <input type="password" placeholder="<fmt:message key="placeholder.password"/>" name="${AttributeParameterHolder.PARAMETER_USER_PASSWORD}" id="id_password" required/>
            <i class="far fa-eye" id="togglePassword" style="margin-left: -30px; color: black; cursor: pointer;"></i>
          </label> <br/> <br/>
        </div>
        <div class="bottom_container" style="margin-bottom: 500px">
          <div class="bottom_container-buttons">
            <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.FINISH_DELETE_ACCOUNT}">
            <input style="background-color: red; margin-top: 80px;"  onclick="showAlert()"  type="submit" value="<fmt:message key="button.delete_account.delete_permanently"/>">
          </div>
        </div>
      </form>
    </div>
  </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
</body>
<script>
  function showAlert() {
    alert ('Your personal information including your ratings and orders will be permanently removed, would you like to continue?');
  }

  const togglePassword = document.querySelector('#togglePassword');
  const password = document.querySelector('#id_password');

  togglePassword.addEventListener('click', function (e) {
    // toggle the type attribute
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    // toggle the eye slash icon
    this.classList.toggle('fa-eye-slash');
  });
</script>
</html>