<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page import="com.example.demo.util.DateTimeFormatter" %>
<%@ page import="com.example.demo.controller.command.CommandType" %>
<%@ page import="com.example.demo.model.entity.enumerator.OrderStatus" %>
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
    <title><fmt:message key="title.user_orders"/></title>
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
                        <a style="margin-top: 20px;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_RATINGS}">
                            <fmt:message key="header.navbar.ratings"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px; color: gray;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_ORDERS}">
                            <fmt:message key="header.navbar.orders"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_IMAGES}">
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
                                    <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_ORDERS}&lang=ru">Рус</a>
                                </c:when>

                                <c:otherwise>
                                    <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_ORDERS}&lang=en">Eng</a>
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
                    <c:when test="${not empty sessionScope.all_user_orders}">
                        <c:forEach items="${sessionScope.all_user_orders}" var="order">
                            <div class="order__card">
                                <img src="${order.getImage().getUrl()}" alt="">
                                <div class="box">
                                    <p>
                                        <fmt:message key="text.user_orders.user"/>
                                            ${order.getUser().getUsername()}
                                    </p>
                                    <p>
                                        <fmt:message key="text.user_orders.status"/>
                                        <fmt:message key="status.order.${order.getStatus().toString().toLowerCase()}"/>
                                    </p>
                                    <c:choose>
                                        <c:when test="${order.isWithDiscount()}">
                                        <p>
                                            <fmt:message key="text.user_orders.discount"/> 15%
                                        </p>
                                        </c:when>

                                        <c:otherwise>
                                            <p>
                                                <fmt:message key="text.user_orders.discount"/> 0%
                                            </p>
                                        </c:otherwise>

                                    </c:choose>
                                    <p>
                                        <fmt:message key="text.user_orders.amount"/>
                                        $${order.getAmount()}
                                    </p>
                                    <c:set var="date_and_time" value="${DateTimeFormatter.format(order.getCreatedAt().toString())}"/>
                                    <p>
                                        <fmt:message key="text.user_orders.date"/>
                                            ${date_and_time.substring(0, 11)}
                                            ${date_and_time.substring(23)}
                                    </p>
                                    <p>
                                        <fmt:message key="text.user_orders.time"/>
                                            ${date_and_time.substring(11, 19)}
                                    </p>
                                    <c:choose>
                                         <c:when test="${order.getStatus().equals(OrderStatus.AWAITING_PAYMENT)}">
                                            <form action="${pageContext.request.contextPath}/home?command=${CommandType.PROCEED_ORDER}&order=${order.getId()}" method="post">
                                                <input style="background-color: transparent; color:gray; width: 90px; font-size: 15px; margin-top: -10px; margin-left: -20px;"
                                                       type="submit" value="<fmt:message key="button.user_orders.pay_now"/>">
                                            </form>
                                            <form action="${pageContext.request.contextPath}/home?command=${CommandType.CANCEL_ORDER}&order=${order.getId()}" method="post">
                                                <input style="background-color: transparent; color: gray; width: 90px; font-size: 15px; margin-top: -45px; margin-left: 60px;"
                                                       type="submit" value="<fmt:message key="button.user_orders.cancel_order"/>">
                                            </form>
                                        </c:when>

                                        <c:otherwise>
                                            <a style="color: gray;" href="${pageContext.request.contextPath}/home?command=${CommandType.IMAGE_DETAILS}&image=${order.getImage().getId()}" target="_blank">
                                                <fmt:message key="button.user_orders.rate"/>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
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