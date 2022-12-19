<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page import="java.lang.Math" %>
<%@ page import="com.example.demo.model.entity.enumerator.UserStatus" %>
<%@ page import="com.example.demo.controller.command.CommandType" %>
<%@ page import="com.example.demo.model.entity.enumerator.UserRole" %>
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
    <title><fmt:message key="title.catalog"/></title>
</head>
<body>
<header class="header">
    <div class="container">
        <div class="header__inner">
            <nav class="header__nav">
                <ul class="header__menu">
                    <li class="header__menu-item">
                        <a style="font-size: 60px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.HOME}">
                            <fmt:message key="header.navbar.salon"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="padding-top: 20px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.HOME}">
                            <fmt:message key="header.navbar.home"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="padding-top: 20px; color:gray;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}">
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
                    <c:choose>
                        <c:when test="${not empty requestScope.sort}">
                            <c:choose>
                                <c:when test="${not empty requestScope.order}">
                                    <c:set var="link" value="http://localhost:8080/home?command=${CommandType.CATALOG}&sort=${requestScope.sort}&order=${requestScope.order}"/>
                                </c:when>

                                <c:otherwise>
                                    <c:set var="link" value="http://localhost:8080/home?command=${CommandType.CATALOG}&sort=${requestScope.sort}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:when>

                        <c:otherwise>
                            <c:set var="link" value="http://localhost:8080/home?command=${CommandType.CATALOG}"/>
                        </c:otherwise>
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
                                    <a style="font-size: 40px" href="${link}&lang=ru">Рус</a>
                                </c:when>

                                <c:otherwise>
                                    <a style="font-size: 40px" href="${link}&lang=en">Eng</a>
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
    <div class="catalog">
        <div class="container">
            <div class="catalog__filter"
                style="display: flex; padding-bottom: 60px; padding-left: 10px; padding-top: 20px">
                <div class="catalog__filter-item">
                    <label style="font-size: 30px; margin-right: 20px">
                        <fmt:message key="label.catalog.sort_by"/>
                        <label class="dropdown">
                            <c:choose>
                                <c:when test="${requestScope.sort.equals('price')}">
                                    <a href="javascript:void(0)" class="dropbtn"><fmt:message key="label.catalog.sort_by.option.price"/></a>
                                    <label class="dropdown-content">
                                        <label>
                                            <a style="font-size: 25px" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}&sort=rating&page=${requestScope.current}">&nbsp;
                                                <fmt:message key="label.catalog.sort_by.option.rating"/>
                                            </a>
                                        </label>
                                        <label>
                                            <a style="font-size: 25px" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}&page=${requestScope.current}">&nbsp;
                                                <fmt:message key="label.catalog.deselect"/>
                                            </a>
                                        </label>
                                    </label>
                                </c:when>
                                <c:when test="${requestScope.sort.equals('rating')}">
                                    <a href="javascript:void(0)" class="dropbtn"><fmt:message key="label.catalog.sort_by.option.rating"/></a>
                                    <label class="dropdown-content">
                                        <label>
                                            <a style="font-size: 25px" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}&sort=price&page=${requestScope.current}">&nbsp;
                                                <fmt:message key="label.catalog.sort_by.option.price"/>
                                            </a>
                                        </label>
                                        <label>
                                            <a style="font-size: 25px" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}&page=${requestScope.current}">&nbsp;
                                                <fmt:message key="label.catalog.deselect"/>
                                            </a>
                                        </label>
                                    </label>
                                </c:when>

                                <c:otherwise>
                                    <a href="javascript:void(0)" class="dropbtn"><fmt:message key="label.catalog.select"/></a>
                                    <label class="dropdown-content">
                                        <label>
                                            <a style="font-size: 25px" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}&sort=rating&page=${requestScope.current}">&nbsp;&nbsp;
                                                <fmt:message key="label.catalog.sort_by.option.rating"/>
                                            </a>
                                        </label>
                                        <label>
                                        <a style="font-size: 25px" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}&sort=price&page=${requestScope.current}">&nbsp;&nbsp;
                                            <fmt:message key="label.catalog.sort_by.option.price"/>
                                        </a>
                                    </label>
                                    </label>
                                </c:otherwise>
                            </c:choose>
                        </label>
                    </label>

                    <c:choose>
                        <c:when test="${not empty requestScope.sort}">
                            <label style="font-size: 30px; margin-right: 20px">
                                <fmt:message key="label.catalog.order"/>
                                <label class="dropdown">
                                    <c:choose>
                                        <c:when test="${requestScope.order.equals('desc')}">
                                            <a href="javascript:void(0)" class="dropbtn">
                                                <fmt:message key="label.catalog.order.option.descending"/>
                                            </a>
                                            <label class="dropdown-content">
                                                <label>
                                                    <a style="font-size: 25px" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}&sort=${requestScope.sort}&order=asc&page=${requestScope.current}">&nbsp;&nbsp;
                                                        <fmt:message key="label.catalog.order.option.ascending"/>
                                                    </a>
                                                </label>
                                            </label>
                                        </c:when>

                                        <c:when test="${requestScope.order.equals('asc')}">
                                            <a href="javascript:void(0)" class="dropbtn">
                                                <fmt:message key="label.catalog.order.option.ascending"/>
                                            </a>
                                            <label class="dropdown-content">
                                                <label>
                                                    <a style="font-size: 25px" href="${pageContext.request.contextPath}/home?command=${CommandType.CATALOG}&sort=${requestScope.sort}&order=desc&page=${requestScope.current}">&nbsp;&nbsp;
                                                        <fmt:message key="label.catalog.order.option.descending"/>
                                                    </a>
                                                </label>
                                            </label>
                                        </c:when>
                                    </c:choose>
                                </label>
                            </label>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <br/>
            <div class="catalog__inner">
                <c:forEach items="${sessionScope.catalog}" var="item">
                    <div class="catalog__card">
                        <img src="${item.getUrl()}" alt=''>
                        <p>
                            ${item.getShortDescription().substring(0, 15)} &nbsp;&nbsp;&nbsp;&nbsp;
                                $${item.getPrice()} &nbsp;&nbsp;&nbsp;&nbsp;
                                ★${item.getRating()}
                            <c:choose>
                                <c:when test="${!sessionScope.user.getRole().equals(UserRole.GUEST)}">
                                    <a style="color: gray" href="${pageContext.request.contextPath}/home?command=${CommandType.IMAGE_DETAILS}&image=${item.getId()}" target="_blank">
                                        <fmt:message key="text.catalog.more_about_image"/>...
                                    </a>
                                </c:when>
                            </c:choose>

                        </p>
                    </div>
                    <br>
                </c:forEach>
            </div>
        </div>
        <div style="justify-content: center" class="container">
            <div class="pagination">


                <c:set var="current" value="${requestScope.current}"/>
                <c:set var="pages" value="${(sessionScope.size - 1) / 20 + 1}"/>
                <fmt:parseNumber var="pages" integerOnly="true" type="number" value="${pages}" />


                <c:choose>
                    <c:when test="${current == 1}">
                        <a class="disabled">
                            &laquo;&nbsp;&nbsp;<fmt:message key="text.catalog.pagination.prev"/></a>
                    </c:when>

                    <c:otherwise>
                        <a href="${link}&page=${Math.max(1, current - 1)}">
                            &laquo;&nbsp;&nbsp;<fmt:message key="text.catalog.pagination.prev"/></a>
                    </c:otherwise>
                </c:choose>

                <c:forEach var="i" varStatus="loop" begin="1" end="${pages}">
                    <c:choose>
                        <c:when test="${current == i}">
                            <a class="current" href="${link}&page=${i}">${i}</a>
                        </c:when>

                        <c:otherwise>
                            <a href="${link}&page=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:choose>
                    <c:when test="${current == pages}">
                        <a class="disabled">
                            <fmt:message key="text.catalog.pagination.next"/>&raquo;&nbsp;&raquo;</a>
                    </c:when>

                    <c:otherwise>
                        <a href="${link}&page=${Math.min(current + 1, pages)}">
                            <fmt:message key="text.catalog.pagination.next"/>&nbsp;&nbsp;&raquo;</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>
