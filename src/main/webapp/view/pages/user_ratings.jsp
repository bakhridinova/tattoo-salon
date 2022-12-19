<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page import="com.example.demo.controller.command.CommandType" %>
<%@ page import="com.example.demo.util.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}" scope="session"/>
<fmt:setBundle basename="localization.localizedtext"/>

<html lang="${sessionScope.localisation}">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <jsp:include page="/view/fragment/head_links.jsp"/>
    <title><fmt:message key="title.user_ratings"/></title>
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
                        <a style="margin-top: 20px; color: gray;" class="header__menu-link"  href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_RATINGS}">
                            <fmt:message key="header.navbar.ratings"/>
                        </a>
                    </li>
                    <li class="header__menu-item">
                        <a style="margin-top: 20px;" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_ORDERS}">
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
                                    <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_RATINGS}&lang=ru">Рус</a>
                                </c:when>

                                <c:otherwise>
                                    <a style="font-size: 40px" href="${pageContext.request.contextPath}/home?command=${CommandType.ACCOUNT_RATINGS}&lang=en">Eng</a>
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
                    <c:when test="${not empty sessionScope.all_user_ratings}">
                        <c:forEach items="${sessionScope.all_user_ratings}" var="rating">
                            <c:set var="date_and_time" value="${DateTimeFormatter.format(rating.getCreatedAt().toString())}"/>
                            <button class="accordion">
                                <a>
                                    <fmt:message key="text.user_ratings.date"/>
                                        ${date_and_time.substring(0, 11)}${date_and_time.substring(23)}
                                </a>
                                <a>
                                    <fmt:message key="text.user_ratings.time"/>
                                        ${DateTimeFormatter.format(rating.getCreatedAt()).substring(11, 19)}
                                </a>
                                <a>
                                    <fmt:message key="text.user_ratings.user"/>
                                        ${rating.getUser().getUsername()}
                                </a>
                                <a>
                                    <fmt:message key="text.user_ratings.rating"/>
                                        ${rating.getRating()}
                                </a>
                            </button>

                            <c:choose>
                                <c:when test="${sessionScope.all_user_ratings.indexOf(rating) == sessionScope.all_user_ratings.size() - 1}">
                                    <div style="border-bottom-right-radius: 15px; border-bottom-left-radius: 15px" class="panel">
                                        <img src="${rating.getImage().getUrl()}" alt="image">
                                        <p>${rating.getReview()}
                                            <a style="color:gray;" href="${pageContext.request.contextPath}/home?command=${CommandType.IMAGE_DETAILS}&image=${rating.getImage().getId()}" target="_blank">
                                                <fmt:message key="text.user_ratings.view"/>
                                            </a></p>
                                    </div>
                                </c:when>

                                <c:otherwise>
                                    <div class="panel">
                                        <img src="${rating.getImage().getUrl()}" alt="image">
                                        <p>${rating.getReview()}
                                            <a style="color:gray;" href="${pageContext.request.contextPath}/home?command=${CommandType.IMAGE_DETAILS}&image=${rating.getImage().getId()}" target="_blank">
                                                <fmt:message key="text.user_ratings.view"/>
                                            </a></p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:when>

                    <c:otherwise>
                        <div class="nothing--to--show">
                            <p><fmt:message key="text.other.nothing_to_show"/></p>
                        </div>
                    </c:otherwise>
                </c:choose>

                <script>
                    var acc = document.getElementsByClassName("accordion");
                    var i;

                    for (i = 0; i < acc.length; i++) {
                        acc[i].addEventListener("click", function() {
                            this.classList.toggle("active");
                            var panel = this.nextElementSibling;
                            if (panel.style.maxHeight) {
                                panel.style.maxHeight = null;
                            } else {
                                panel.style.maxHeight = panel.scrollHeight + "px";
                            }
                        });
                    }
                </script>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
</body>
</html>