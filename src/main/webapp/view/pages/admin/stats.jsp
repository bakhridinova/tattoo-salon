<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title><fmt:message key="title.stats"/></title>
</head>
<body>
<header class="header">
    <div class="container">
        <div class="header__inner">
            <nav class="header__nav">
                <ul class="header__menu">
                    <li class="header__menu-item">
                        <a style="font-size: 60px; color: gray" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.STATISTICS}">
                            <fmt:message key="header.navbar.stats"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_IMAGES}">
                            <fmt:message key="header.navbar.images"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_RATINGS}">
                            <fmt:message key="header.navbar.ratings"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_ORDERS}">
                            <fmt:message key="header.navbar.orders"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_USERS}">
                            <fmt:message key="header.navbar.users"/>
                        </a>
                    </li>
                </ul>
            </nav>
            <a style="top: 170px" class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.HOME}">
                <fmt:message key="text.other.back_to_home"/>
            </a>
        </div>
    </div>
</header>
<main class="main">
    <div class="info">
        <div style="min-height: 600px;" class="container">
<%--            <div id="chartContainerOrder" style="height: 300px; width: 60%; margin-top: 100px; margin-left: -200px;"></div>--%>
<%--            <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>--%>
<%--            <br/> <br/>--%>
<%--            <div id="chartContainerImage" style="height: 300px; width: 60%; margin-top: -320px; margin-left: 500px;"></div>--%>
<%--            <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>--%>
        </div>
    </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>