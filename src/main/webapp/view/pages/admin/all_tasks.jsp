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
    <title><fmt:message key="title.tasks"/></title>
</head>
<body>
<header class="header">
    <div class="container">
        <div class="header__inner">
            <nav class="header__nav">
                <ul class="header__menu">
                    <li class="header__menu-item">
                        <a style="font-size: 60px;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_DETAILS}">
                            <fmt:message key="header.navbar.account_details"/>
                        </a>
                    </li>

                    <li class="header__menu-item">
                        <a style="padding-top: 20px;  color: gray" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_TASKS}">
                            <fmt:message key="header.navbar.tasks"/>
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
                                    <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_TASKS}&lang=ru">Рус</a>
                                </c:when>

                                <c:otherwise>
                                    <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_TASKS}&lang=en">Eng</a>
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

            <a class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.HOME}">
                <fmt:message key="text.other.back_to_home"/>
            </a>
        </div>
    </div>
</header>
<main class="main">
    <div class="tasks">
        <div class="container">
            <div style="min-height: 600px;" class="info__inner">
                <c:choose>
                    <c:when test="${not empty sessionScope.all_tasks}">
                        <c:forEach items="${sessionScope.all_tasks}" var="task">
                            <form action="home" method="post">
                                <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.PROCEED_NEW_DESIGN}"/>
                                <input type="hidden" name="${AttributeParameterHolder.PARAMETER_USER_ID}" value="${task.getUserId()}"/>
                                <input type="hidden" name="${AttributeParameterHolder.PARAMETER_IMAGE_ID}" value="${task.getId()}"/>
                                <div class="task__card">
                                    <img src="${task.getUrl()}" alt="">
                                    <div class="task__card-text">
                                        <p>
                                            <label><fmt:message key="text.image_details.price"/>
                                                $ <input style="background-color: #333333; color: white; border-radius: 0.4em; height: 22px;"
                                                         name="${AttributeParameterHolder.PARAMETER_IMAGE_PRICE}" type="number" min="10" max="80" required>
                                            </label>
                                        </p> <br/>
                                        <p>
                                            <b><fmt:message key="text.image_details.user"/></b>
                                                ${task.getUserId()}
                                        </p> <br/>
                                        <p style="max-width: 550px;">
                                            <b><fmt:message key="text.image_details.short_desc"/></b>
                                                ${task.getShortDescription()}
                                        </p> <br/>
                                        <p style="max-width: 550px;">
                                            <b><fmt:message key="text.image_details.long_desc"/></b>
                                                ${task.getLongDescription()}
                                        </p><br/>

                                        <input style="background-color: transparent; color: gray; margin-left: -60px; font-size: 20px" type="submit" value="<fmt:message key="text.tasks.approve"/>">
                                        <br/>
                                        <hr/>
                                    </div>
                                </div>
                            </form>
                            <br/> <br/>
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

