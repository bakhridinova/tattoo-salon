<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page import="com.example.demo.util.DateTimeFormatter" %>
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
    <title><fmt:message key="title.user_account"/></title>
</head>
<body>
<header class="header">
    <div class="container">
        <div class="header__inner">
            <nav class="header__nav">
                <ul class="header__menu">
                    <li class="header__menu-item">
                        <a style="font-size: 60px; color: gray;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_DETAILS}">
                            <fmt:message key="header.navbar.account_details"/>
                        </a>
                    </li>

                    <c:choose>
                        <c:when test="${sessionScope.user.getRole().equals(UserRole.ADMIN)}">
                            <li class="header__menu-item">
                                <a style="padding-top: 20px;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ALL_TASKS}">
                                    <fmt:message key="header.navbar.tasks"/>
                                </a>
                            </li>
                        </c:when>

                        <c:otherwise>
                            <li class="header__menu-item">
                                <a style="margin-top: 20px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_RATINGS}">
                                    <fmt:message key="header.navbar.ratings"/>
                                </a>
                            </li>
                            <li class="header__menu-item">
                                <a style="margin-top: 20px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_ORDERS}">
                                    <fmt:message key="header.navbar.orders"/>
                                </a>
                            </li>
                            <li class="header__menu-item">
                                <a style="margin-top: 20px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_IMAGES}">
                                    <fmt:message key="header.navbar.images"/>
                                </a>
                            </li>
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
                                    <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_DETAILS}&lang=ru">Рус</a>
                                </c:when>

                                <c:otherwise>
                                    <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_DETAILS}&lang=en">Eng</a>
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
    <div class="sign">
        <div class="container">
            <div class="sign__inner">
                <label>
                    <fmt:message key="label.username"/>
                </label> <br/>
                <label>
                    <input style="width: 20%" type="text" name="user_name" value="${sessionScope.user.getUsername()}" readonly/>
                    <i class="fa-solid fa-star" style="margin-left: -30px; color: gray; cursor: pointer;"></i>
                </label> <br/> <br/>

                <label>
                    <fmt:message key="label.birth_date"/>
                </label> <br/>
                <label>
                    <c:set var="date_of_birth" value="${DateTimeFormatter.format(sessionScope.user.getBirthDate().toString())}"/>
                    <input style="width: 20%" type="text" name="birth_date" value="${date_of_birth.substring(0, 11)}${date_of_birth.substring(23)}" readonly/>
                    <i class="fa-solid fa-cake-candles" style="margin-left: -30px; color: gray; cursor: pointer;"></i>
                </label> <br/> <br/>

                <label>
                    <fmt:message key="label.gender"/>
                </label> <br/>
                <label>
                    <input style="width: 20%" type="text" name="gender" value="<fmt:message key="label.gender.${sessionScope.user.getGender().toString().toLowerCase()}"/>" readonly/>
                    <i class="fa-solid fa-venus-mars" style="margin-left: -30px; color: gray; cursor: pointer;"></i>
                </label> <br/> <br/>
            </div>

            <div style="margin-top: -290px; margin-left: 200px" class="sign__inner">
                <label>
                    <fmt:message key="label.full_name"/>
                </label> <br/>
                <label>
                    <input style="width: 45%" type="text" name="full_name" value="${sessionScope.user.getFullName()}" readonly/>
                </label> <br/> <br/>

                <label>
                    <fmt:message key="label.email_address"/>
                </label> <br/>
                <label>
                    <input style="width: 45%" type="text" name="email_address" value="${sessionScope.user.getEmailAddress()}" readonly/>
                    <i class="fa-solid fa-envelope" style="margin-left: -30px; color: gray; cursor: pointer;"></i>
                </label> <br/> <br/>

                <label>
                    <fmt:message key="label.contact_number"/>
                </label> <br/>
                <label>
                    <input style="width: 45%" type="text" name="contact_number" value="${sessionScope.user.getContactNumber()}" readonly/>
                    <i class="fa-solid fa-address-book" style="margin-left: -30px; color: gray; cursor: pointer;"></i>
                </label> <br/> <br/>
            </div>
        </div>

        <div class="bottom_container">
            <div class="bottom_container-buttons">
                <form action="home" method="post">
                    <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.START_EDIT_ACCOUNT_DETAILS}">
                    <input type="submit" value="<fmt:message key="button.edit_account"/>"/>
                </form>
                <br/> <br/>

                <c:choose>
                    <c:when test="${!sessionScope.user.getRole().equals(UserRole.ADMIN)}">
                        <form action="home" method="post">
                            <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.START_DELETE_ACCOUNT}">
                            <input style="background-color: red; margin-top: 80px;" type="submit" value="<fmt:message key="button.delete_account"/>">
                        </form>
                    </c:when>
                </c:choose>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>