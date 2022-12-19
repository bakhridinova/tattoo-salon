<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page import="com.example.demo.controller.command.CommandType" %>
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
  <title><fmt:message key="title.new_image"/></title>
</head>
<body>
<header class="header">
  <div class="container">
    <div class="header__inner">
      <nav class="header__nav">
        <div class="header__menu">
          <div class="header__menu-item">
            <a style="font-size: 60px" class="header__menu-link"
               href="${pageContext.request.contextPath}/home?command=${CommandType.IMAGE_DETAILS}?image=${sessionScope.image.getId()}">
              <fmt:message key="header.navbar.new_image"/>
            </a>
          </div>
        </div>
      </nav>
      <a style="top: 100px;" class="header__signUp-link" onclick="windowClose()" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}&page=${requestScope.current}">
        <fmt:message key="text.other.back_to_page"/>
      </a>
    </div>
  </div>
</header>
<main class="main">
  <div class="info">
    <div class="container">
      <form action="home" method="post">
        <input type="hidden" name="command" value="${CommandType.FINISH_CREATE_DESIGN}">
          <div class="info__inner">
            <div class="info__card">
              <div style="margin-left: 100px" class="info__card-text">
                <p><fmt:message key="text.new_image.image_link"/></p> <br/>
                <label>
                  <textarea style="height: 100px; width: 500px;" class="textarea" id="the-textarea1" name="${AttributeParameterHolder.PARAMETER_IMAGE_URL}" placeholder="<fmt:message key="placeholder.paste_link"/>"></textarea>
                </label>

                <p style="margin-top: -50px;"><fmt:message key="text.new_image.short_desc"/></p> <br/>
                <label>
                  <textarea style="height: 100px; width: 500px;" spellcheck="true" class="textarea" id="the-textarea2"  name="${AttributeParameterHolder.PARAMETER_IMAGE_SHORT_DESC}" minlength="20" maxlength="100" placeholder="<fmt:message key="placeholder.textarea"/> "></textarea>
                </label>
              </div>
              <div style="margin-left: 100px;" class="info__card-text">
                <p><fmt:message key="text.new_image.long_desc"/></p> <br/>
                <label>
                  <textarea style="height: 200px; width: 500px;" spellcheck="true" class="textarea" id="the-textarea3"  name="${AttributeParameterHolder.PARAMETER_IMAGE_LONG_DESC}" minlength="50" maxlength="500" placeholder="<fmt:message key="placeholder.textarea"/>"></textarea>
                </label>
              </div>
              <div class="bottom_container" style="margin-bottom: 700px">
                <div class="bottom_container-buttons">
                  <input style="right: 200px;  top: 550px;" type="submit" value="<fmt:message key="button.create_design.save"/>">
                </div>
              </div>
            </div>
            <br/>
          </div>
      </form>
    </div>
  </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
<script>
  function windowClose() {
    window.open('','_parent','');
    window.close();
  }
</script>
</body>
</html>