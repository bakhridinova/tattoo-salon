<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page import="com.example.demo.controller.command.CommandType" %>
<%@ page import="com.example.demo.model.entity.enumerator.ImageStatus" %>
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
  <title><fmt:message key="title.user_images"/></title>
  <style>
    .info__inner {
      display: flex;
      gap: 50px;
      flex-direction: row;
      flex-wrap: wrap;
      margin-left: -100px;
    }
  </style>
</head>
<body>
<header class="header">
  <div class="container">
    <div class="header__inner">
      <nav class="header__nav">
        <ul class="header__menu">
          <li class="header__menu-item">
            <a style="font-size: 60px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_DETAILS}">
              <fmt:message key="header.navbar.account_details"/>
            </a>
          </li>
          <li class="header__menu-item">
            <a style="margin-top: 20px; " class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_RATINGS}">
              <fmt:message key="header.navbar.ratings"/>
            </a>
          </li>
          <li class="header__menu-item">
            <a style="margin-top: 20px;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_ORDERS}">
              <fmt:message key="header.navbar.orders"/>
            </a>
          </li>
          <li class="header__menu-item">
            <a style="margin-top: 20px; color: gray;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_IMAGES}">
              <fmt:message key="header.navbar.images"/>
            </a>
          </li>
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
                  <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_IMAGES}&lang=ru">Рус</a>
                </c:when>

                <c:otherwise>
                  <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_IMAGES}&lang=en">Eng</a>
                </c:otherwise>
              </c:choose>
            </div>
          </li>
        </ul>
      </nav>
      <button class="header__signIn-btn">
        <a href="${pageContext.request.contextPath}/home?command=${CommandType.SIGN_OUT}">
          <fmt:message key="button.signout"/>
        </a>
      </button>

      <a style="top: 180px;" class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.HOME}">
        <fmt:message key="text.other.back_to_home"/>
      </a>
    </div>
  </div>
</header>
<main class="main">
  <div class="info">
    <div class="container">
      <div style="min-height: 600px;" class="info__inner">
        <c:choose>
          <c:when test="${not empty sessionScope.all_user_images}">
            <c:forEach items="${sessionScope.all_user_images}" var="image">
              <div class="task__card">
                <img src="${image.getUrl()}" alt="">
                <div class="task__card-text">
                  <p>
                    <b><fmt:message key="text.image_details.price"/></b>
                      <c:choose>
                        <c:when test="${image.getPrice().equals(0.0)}">
                          <fmt:message key="text.user_images.no_price"/>
                        </c:when>

                        <c:otherwise>
                          $${image.getPrice()}
                        </c:otherwise>
                      </c:choose>
                  </p> <br/>
                  <p>
                    <b><fmt:message key="text.image_details.status"/></b>
                    <fmt:message key="status.image.${image.getStatus().toString().toLowerCase()}"/>
                  </p> <br/>
                  <p style="max-width: 550px;">
                    <b><fmt:message key="text.image_details.short_desc"/></b>
                      ${image.getShortDescription()}
                  </p> <br/>
                  <p style="max-width: 550px;">
                    <b><fmt:message key="text.image_details.long_desc"/></b>
                      ${image.getLongDescription()}
                  </p><br/>

                  <c:choose>
                    <c:when test="${image.getStatus().equals(ImageStatus.APPROVED)}">
                      <a style="color: gray" href="${pageContext.request.contextPath}/home?command=${CommandType.IMAGE_DETAILS}&image=${image.getId()}" target="_blank">
                        <fmt:message key="text.catalog.more_about_image"/>...
                      </a> <br/> <br/>
                    </c:when>
                  </c:choose>
                  <hr/>
                </div>
              </div>
            </c:forEach>
          </c:when>

          <c:otherwise>
            <div class="nothing--to--show">
              <p><fmt:message key="text.other.nothing_to_show"/></p>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>