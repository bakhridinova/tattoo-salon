<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page import="com.example.demo.model.dao.DataBaseInfo" %>
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
    <title><fmt:message key="title.all_orders"/></title>
</head>
<body>
<header class="header">
    <div class="container">
        <div class="header__inner">
            <nav class="header__nav">
                <ul class="header__menu">
                    <li class="header__menu-item">
                        <a style="font-size: 60px; color: white;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.STATISTICS}">
                            <fmt:message key="header.navbar.stats"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px; color: white;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_IMAGES}">
                            <fmt:message key="header.navbar.images"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px; color: white;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_RATINGS}">
                            <fmt:message key="header.navbar.ratings"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px; color: gray;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_ORDERS}">
                            <fmt:message key="header.navbar.orders"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px; color: white;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_USERS}">
                            <fmt:message key="header.navbar.users"/>
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
                                    <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_ORDERS}&lang=ru">Рус</a>
                                </c:when>

                                <c:otherwise>
                                    <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_ORDERS}&lang=en">Eng</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
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
            <h2 style="margin-left: -100px">
                <fmt:message key="text.all_entities.press_to_sort"/>
            </h2>
            <br/> <br/>
            <table id="all_entities_orders" class="sortable">
                <tr>
                    <th><fmt:message key="table.orders.column.${DataBaseInfo.ORDERS_TABLE_PK_COLUMN}"/></th>
                    <th><fmt:message key="table.orders.column.${DataBaseInfo.ORDERS_TABLE_FK_USER_ID_COLUMN}"/></th>
                    <th><fmt:message key="table.orders.column.${DataBaseInfo.ORDERS_TABLE_FK_IMAGE_ID_COLUMN}"/></th>
                    <th><fmt:message key="table.orders.column.${DataBaseInfo.ORDERS_TABLE_STATUS_COLUMN}"/></th>
                    <th><fmt:message key="table.orders.column.${DataBaseInfo.ORDERS_TABLE_WITH_DISCOUNT_COLUMN}"/></th>
                    <th><fmt:message key="table.orders.column.${DataBaseInfo.ORDERS_TABLE_AMOUNT_COLUMN}"/></th>
                    <th><fmt:message key="table.orders.column.${DataBaseInfo.ORDERS_TABLE_CREATED_AT_COLUMN}"/></th>
                </tr>
                <c:forEach items="${requestScope.all_orders}" var="order">
                    <tr>
                        <td>${order.getId()}</td>
                        <td>${order.getUserId()}</td>
                        <td>${order.getImageId()}</td>
                        <td><fmt:message key="status.order.${order.getStatus().toString().toLowerCase()}"/></td>
                        <td><fmt:message key="status.order.type.${order.isWithDiscount()}"/></td>
                        <td>${order.getAmount()}</td>
                        <td>${order.getCreatedAt()}</td>
                    </tr>
                </c:forEach>
            </table>
            <script src="${pageContext.request.contextPath}/js/sorttable.js"></script>
        </div>
    </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>
