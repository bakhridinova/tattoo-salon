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
    <title><fmt:message key="title.signin"/></title>
    <style>
        .header__signUp-link {
            position: absolute;
            top: 180px;
            right: 200px;
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
                        <a style="font-size: 60px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.START_SIGN_IN}">
                            <fmt:message key="text.signin.title"/>
                        </a>
                    </li>
                </ul>
            </nav>
            <a class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.HOME}">
                <fmt:message key="text.other.back_to_home"/>
            </a>
            <a style="top: 100px" class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.START_SIGN_UP}">
                <fmt:message key="text.signin.signup"/>
            </a>
        </div>
    </div>
</header>

<main class="main">
    <div class="sign">
        <div class="container">
            <form action="home" method="post">
                <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.FINISH_SIGN_IN}">
                <div style="margin-top: 100px" class="sign__inner">
                    <label><fmt:message key="label.username"/></label>
                    <br/>
                    <label>
                        <input type="text" placeholder="<fmt:message key="placeholder.username"/>" name="${AttributeParameterHolder.PARAMETER_USER_NAME}" required/>
                    </label>
                    <br/> <br/>
                    <label><fmt:message key="label.password"/></label>
                    <br/>
                    <label>
                        <input type="password" placeholder="<fmt:message key="placeholder.password"/>" name="${AttributeParameterHolder.PARAMETER_USER_PASSWORD}" id="id_password" required/>
                        <i class="far fa-eye" id="togglePassword" style="margin-left: -30px; color: black; cursor: pointer;"></i>
                    </label>
                    <c:choose>
                        <c:when test="${not empty requestScope.error_message}">
                            <p style="position: absolute; left: 110px; font-size: 15px; margin-top: 10px; color:red;">
                                    <fmt:message key="message.invalid.${requestScope.error_message}"/>
                            </p>
                        </c:when>
                    </c:choose>
                    <br/> <br/> <br/>
                    <input type="submit" value="<fmt:message key="button.signin"/>"/>
                    <img class="sign__inner-image"
                         src="images/with_those_who_love.png" alt="">
                </div>
            </form>
        </div>
    </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
<script>
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
</body>
</html>