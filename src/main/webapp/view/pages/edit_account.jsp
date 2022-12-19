<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page import="com.example.demo.model.entity.enumerator.UserGender" %>
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
  <title><fmt:message key="title.edit_account"/></title>
    <script src="js/mask.js"></script>
    <script src="js/mask_list.js"></script>
</head>
<body>
<header class="header">
  <div class="container">
    <div class="header__inner">
      <nav class="header__nav">
        <div class="header__menu">
          <div class="header__menu-item">
            <a style="font-size: 60px; color: gray" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.START_EDIT_ACCOUNT_DETAILS}">
                <fmt:message key="text.edit_account.title"/>
            </a>
          </div>
        </div>
      </nav>
        <button onclick="showAlert()" class="header__signIn-btn">
            <a href="${pageContext.request.contextPath}/home?command=${CommandType.SIGN_OUT}">
                <fmt:message key="button.signout"/>
            </a>
        </button>

      <a class="header__signUp-link" onclick="showAlert()" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_DETAILS}">
          <fmt:message key="text.other.back_to_profile"/>
      </a>
    </div>
  </div>
</header>
<main class="main">
    <div class="sign">
        <div class="container">
            <form action="home" method="post">
                <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.FINISH_EDIT_ACCOUNT_DETAILS}">
                <div class="sign__inner">
                    <label>
                        <fmt:message key="label.username.required"/>
                    </label> <br/>
                    <label>
                        <input style="width: 20%" type="text" placeholder="<fmt:message key="placeholder.username"/>" name="${AttributeParameterHolder.PARAMETER_USER_NAME}" value="${sessionScope.user.getUsername()}" readonly
                        maxlength="20"/>
                    </label> <br/> <br/>
                    <label>
                        <fmt:message key="label.birth_date.required"/>
                    </label> <br/>
                    <label>
                        <input type="date" name="${AttributeParameterHolder.PARAMETER_USER_BIRTH_DATE}" required>
                    </label> <br/> <br/>

                    <label><fmt:message key="label.gender.required"/></label> <br/> <br/>
                    <c:choose>
                        <c:when test="${sessionScope.user.getGender().equals(UserGender.MALE)}">
                            <label class="sign__inner-item-gender">
                                <input type="radio" name="${AttributeParameterHolder.PARAMETER_USER_GENDER}" value="male" required checked>
                                <fmt:message key="label.gender.male"/>
                            </label>
                        </c:when>

                        <c:otherwise>
                            <label class="sign__inner-item-gender">
                                <input type="radio" name="${AttributeParameterHolder.PARAMETER_USER_GENDER}" value="male" required>
                                <fmt:message key="label.gender.male"/>
                            </label>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${sessionScope.user.getGender().equals(UserGender.FEMALE)}">
                            <label class="sign__inner-item-gender">
                                <input type="radio" name="${AttributeParameterHolder.PARAMETER_USER_GENDER}" value="female" required checked>
                                <fmt:message key="label.gender.female"/>
                            </label>
                        </c:when>

                        <c:otherwise>
                            <label class="sign__inner-item-gender">
                                <input type="radio" name="${AttributeParameterHolder.PARAMETER_USER_GENDER}" value="female" required>
                                <fmt:message key="label.gender.female"/>
                            </label>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${sessionScope.user.getGender().equals(UserGender.OTHER)}">
                            <label class="sign__inner-item-gender">
                                <input type="radio" name="${AttributeParameterHolder.PARAMETER_USER_GENDER}" value="other" required checked>
                                <fmt:message key="label.gender.other"/>
                            </label>
                        </c:when>

                        <c:otherwise>
                            <label class="sign__inner-item-gender">
                                <input type="radio" name="${AttributeParameterHolder.PARAMETER_USER_GENDER}" value="other" required>
                                <fmt:message key="label.gender.other"/>
                            </label>
                        </c:otherwise>
                    </c:choose>
                    <br/> <br/>
                </div>




                <div style="margin-top: -270px; margin-left: 200px" class="sign__inner">
                    <label>
                        <fmt:message key="label.full_name"/>
                    </label> <br/>
                    <label>
                        <input  style="text-transform: capitalize; width: 45%;" type="text" placeholder="<fmt:message key="placeholder.full_name"/>" name="${AttributeParameterHolder.PARAMETER_USER_FULL_NAME}" value="${sessionScope.user.getFullName()}"
                        maxlength="50"/>
                    </label> <br/> <br/>

                    <label>
                        <fmt:message key="label.email_address.required"/>
                    </label> <br/>
                    <label>
                        <input style="width: 45%" type="text" placeholder="example@email.com" name="${AttributeParameterHolder.PARAMETER_USER_EMAIL_ADDRESS}" value="${sessionScope.user.getEmailAddress()}"/>
                    </label> <br/> <br/>

                    <label>
                        <fmt:message key="label.contact_number"/>
                    </label> <br/>
                    <label>
                        <input style="width: 45%" type="text" placeholder="<fmt:message key="placeholder.contact_number"/>" name="${AttributeParameterHolder.PARAMETER_USER_CONTACT_NUMBER}" value="${sessionScope.user.getContactNumber()}" />
                        <script>mask('input[name="${AttributeParameterHolder.PARAMETER_USER_CONTACT_NUMBER}"]');</script>
                    </label> <br/> <br/>
                </div>

                <div class="bottom_container" style="margin-bottom: 250px">
                    <div class="bottom_container-buttons">
                        <c:choose>
                            <c:when test="${not empty requestScope.error_message}">
                                <p style="margin-top: -50px; margin-left: 945px; color: red">
                                    <fmt:message key="message.invalid.${requestScope.error_message}"/>
                                </p>
                                <br/>
                            </c:when>
                        </c:choose>
                        <input type="submit" value="<fmt:message key="button.edit_account.save_changes"/>">
                    </div>
                </div>
            </form>
        </div>
    </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
<script>
    function showAlert() {
        alert ('All changes you made will be lost, would you like to continue?');
    }
</script>
</body>
</html>
