<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page import="com.example.demo.controller.command.CommandType" %>
<%@ page import="com.example.demo.model.entity.enumerator.UserRole" %>
<%@ page import="com.example.demo.model.entity.enumerator.UserStatus" %>
<%@ page import="com.example.demo.controller.navigation.AttributeParameterHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}" scope="session"/>
<fmt:setBundle basename="localization.localizedtext"/>

<html lang="${AttributeParameterHolder.SESSION_SCOPE_ATTRIBUTE_PARAMETER_LOCALIZATION}">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <jsp:include page="/view/fragment/head_links.jsp"/>
  <title><fmt:message key="title.home"/></title>
</head>
<body>
<header class="header">
  <div class="container">
    <div class="header__inner">
      <nav class="header__nav">
        <ul class="header__menu">
          <li class="header__menu-item">
            <a style="font-size: 60px;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.HOME}">
              <fmt:message key="header.navbar.salon"/>
            </a>
          </li>
          <li class="header__menu-item">
            <a style="padding-top: 20px;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.HOME}">
              <fmt:message key="header.navbar.home"/>
            </a>
          </li>
          <li class="header__menu-item">
            <a style="padding-top: 20px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}">
              <fmt:message key="header.navbar.catalog"/>
            </a>
          </li>
          <c:choose>
            <c:when test="${!sessionScope.user.getRole().equals(UserRole.GUEST)}">
              <li class="header__menu-item">
                <a  style="padding-top: 20px;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_DETAILS}">
                  <fmt:message key="header.navbar.account"/>
                </a>
              </li>
            </c:when>
          </c:choose>
          <li class="dropdown">
            <a style="font-size: 40px; padding-top: 20px; margin-left: -15px;" href="javascript:void(0)" class="dropbtn">
              <c:choose>
                <c:when test="${sessionScope.localization.equals('en')}">
                  Eng
                </c:when>
                <c:otherwise>
                  Рус
                </c:otherwise>
              </c:choose>
            </a>
            <div class="dropdown-content">
              <c:choose>
                <c:when test="${sessionScope.localization.equals('en')}">
                  <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.DEFAULT}&lang=ru">Рус</a>
                </c:when>

                <c:otherwise>
                  <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.DEFAULT}&lang=en">Eng</a>
                </c:otherwise>
              </c:choose>
            </div>
          </li>
        </ul>
      </nav>
      <c:choose>
        <c:when test="${!sessionScope.user.getRole().equals(UserRole.GUEST)}">
          <button class="header__signIn-btn">
            <a href="${pageContext.request.contextPath}/home?command=${CommandType.SIGN_OUT}">
              <fmt:message key="button.signout"/>
            </a>
          </button>

          <c:choose>
            <c:when test="${sessionScope.user.getRole().equals(UserRole.ADMIN)}">
              <li class="header__menu-item">
                <a class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.STATISTICS}">
                  <fmt:message key="header.navbar.stats"/>
                </a>
              </li>
            </c:when>

            <c:when test="${sessionScope.user.getRole().equals(UserRole.USER) and
                              !sessionScope.user.getStatus().equals(UserStatus.PRO)}">
              <li class="header__menu-item">
                <a style="font-size: 25px;" class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.START_CREATE_DESIGN}"
                   target="_blank">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <fmt:message key="text.catalog.add_first_line"/>
                  <br/> <br/>
                  <fmt:message key="text.catalog.add_second_line"/>
                </a>
              </li>
            </c:when>
          </c:choose>
        </c:when>

        <c:otherwise>
          <button class="header__signIn-btn">
            <a href="${pageContext.request.contextPath}/home?command=${CommandType.START_SIGN_IN}">
              <fmt:message key="button.signin"/>
            </a>
          </button>
          <a class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.START_SIGN_UP}">
            <fmt:message key="text.home.signup"/>
          </a>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</header>

<main class="main">
  <section class="top">
    <div class="container">
      <div class="top__inner">
        <div class="top__picture">
          <img class="top__images" src="images/top_quote.png" alt="top">
        </div>
      </div>
    </div>
  </section>
  <section class="middle">
    <div class="container">
      <div class="middle__inner">
        <div class="middle__picture">
          <img class="middle__images" src="images/left_photo.png" alt="intro">
        </div>
        <div class="middle__picture">
          <img class="middle__images" src="images/right_quote.png" alt="intro">
        </div>
      </div>
    </div>
  </section>
  <section class="bottom">
    <div class="container">
      <div class="bottom__inner">
        <div class="bottom__picture">
          <img class="bottom__images" src="images/left_quote.png" alt="intro">
        </div>
        <div class="bottom__picture">
          <img class="bottom__images" src="images/right_photo.png" alt="intro">
        </div>
      </div>
    </div>
  </section>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>