<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page import="com.example.demo.controller.command.CommandType" %>
<%@ page import="com.example.demo.model.entity.enumerator.UserRole"%>
<%@ page import="com.example.demo.model.entity.enumerator.UserStatus"%>
<%@ page import="com.example.demo.model.entity.enumerator.RatingStatus"%>
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
    <title><fmt:message key="title.image_details"/></title>
    <style>
        img {
            margin-top: 100px;
            margin-left: 100px;
            width: 400px;
            height: 400px;
        }

        button {
            color: white;
            background-color: transparent;
        }
    </style>
    <title>Details</title>
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
                            <fmt:message key="header.navbar.image_details"/>
                        </a>
                    </div>
                </div>
            </nav>
            <a style="top: 100px" class="header__signUp-link" onclick="windowClose()" href="#">
                <fmt:message key="text.other.back_to_page"/>
            </a>
            <c:choose>
                <c:when test="${sessionScope.user.getRole().equals(UserRole.USER) and !requestScope.ordered}">
                    <form action="home" method="post">
                        <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.CREATE_NEW_ORDER}"/>
                        <input type="hidden" name="${AttributeParameterHolder.PARAMETER_IMAGE_ID}" value="${requestScope.image.getId()}"/>
                        <input style="background-color: transparent; position: absolute; font-size: 27px; top: 150px; right: 60px; width: 200px;" onclick="showAlert()"  type="submit" value="<fmt:message key="text.image_details.create_new_order"/>"/>
                    </form>
                </c:when>
            </c:choose>
        </div>
    </div>
</header>
<main class="main">
    <div class="info">
        <div class="container">
            <div style="min-height: 550px;" class="info__inner">
                <div class="info__card">
                    <img src="${requestScope.image.getUrl()}" alt="">
                    <div class="info__card-text">
                        <p>
                            <b><fmt:message key="text.image_details.price"/></b>
                            $${requestScope.image.getPrice()}
                        </p> <br/>
                        <p>
                            <b><fmt:message key="text.image_details.order"/></b>
                        ${requestScope.image.getOrders()}
                        </p> <br/>
                        <p>
                            <b><fmt:message key="text.image_details.rating"/></b>
                        ${requestScope.image.getRating()}
                        </p> <br/>
                        <p>
                            <b><fmt:message key="text.image_details.short_desc"/></b>
                        ${requestScope.image.getShortDescription()}
                        </p> <br/>
                        <p>
                            <b><fmt:message key="text.image_details.long_desc"/></b>
                        ${requestScope.image.getLongDescription()}
                        </p><br/>
                        <hr/>
                    </div>
                </div>
                <br/>
                <p class="info__text">
                    <fmt:message key="text.image_details.reviews"/>
                </p>
                <c:choose>
                    <c:when test="${requestScope.ordered and !requestScope.rated}">
                        <br/>
                        <a style="font-size: 25px; margin-left: 70px;" class="info__text"
                        href="${pageContext.request.contextPath}/home?command=${CommandType.START_CREATE_RATING}&image=${requestScope.image.getId()}">
                            <fmt:message key="text.image_details.add_yours"/>
                        </a>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${not empty requestScope.all_image_details}">
                        <c:forEach items="${requestScope.all_image_details}" var="item">
                            <div class="info__review">
                                <p>
                                    <b>
                                        <c:choose>
                                            <c:when test="${item.getUser().getStatus().equals(UserStatus.DELETED)}">
                                                <fmt:message key="text.image_details.deleted"/>
                                            </c:when>

                                            <c:otherwise>
                                                ${item.getUser().getUsername()}
                                            </c:otherwise>
                                        </c:choose>
                                    </b>
                                </p>
                                <br/>
                                <c:choose>
                                    <c:when test="${not empty requestScope.rating}">
                                        <form action="home" method="post">
                                            <c:choose>
                                                <c:when test="${requestScope.rating.getId().equals(0)}">
                                                    <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.FINISH_CREATE_RATING}"/>
                                                </c:when>

                                                <c:otherwise>
                                                    <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.FINISH_EDIT_RATING}"/>
                                                </c:otherwise>
                                            </c:choose>

                                            <input type="hidden" name="${AttributeParameterHolder.PARAMETER_IMAGE_ID}" value="${requestScope.image.getId()}"/>
                                            <input type="hidden" name="${AttributeParameterHolder.PARAMETER_RATING_ID}" value="${item.getId()}"/>

                                            <div style="margin-left: -10px;" class="rate">
                                                <c:forEach begin="0" end="4" var="i">
                                                    <input type="radio" id="star${5 - i}" name="${AttributeParameterHolder.PARAMETER_RATING_RATING}" value="${5 - i}" required/>
                                                    <label for="star${5 - i}" title="text">${5 - i} stars</label>
                                                </c:forEach>
                                            </div>
                                            <a style="margin-top: 15px; font-size: 20px; margin-left: 10px;"
                                               href="${pageContext.request.contextPath}/home?command=${CommandType.IMAGE_DETAILS}&image=${requestScope.image.getId()}">
                                                <fmt:message key="button.image_details.back"/>
                                            </a>
                                            <input style="background-color: transparent; font-size: 20px; margin-top: -38px; margin-left: 250px; width: 120px;"
                                                   type="submit" value="<fmt:message key="button.image_details.save"/>"/>
                                            <br/>
                                            <label>

                                                <c:choose>
                                                    <c:when test="${requestScope.rating.getId().equals(0)}">
                                                        <textarea spellcheck="true" class="textarea" rows="4" cols="40" minlength="20" placeholder="<fmt:message key="placeholder.textarea"/>"
                                                                  name="${AttributeParameterHolder.PARAMETER_RATING_REVIEW}"></textarea>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <textarea spellcheck="true" class="textarea" rows="4" cols="40" minlength="20" placeholder="<fmt:message key="placeholder.textarea"/>"
                                                                  name="${AttributeParameterHolder.PARAMETER_RATING_REVIEW}"> ${requestScope.rating.getReview()}
                                                        </textarea>
                                                    </c:otherwise>
                                                </c:choose>

                                            </label>
                                        </form>
                                    </c:when>

                                    <c:otherwise>
                                        <p>
                                            <b><fmt:message key="text.image_details.rating_rating"/></b>
                                                ${item.getRating()}
                                        </p>
                                        <c:choose>
                                            <c:when test="${item.getStatus().equals(RatingStatus.EDITED)}">
                                                <p style="margin-top: -20px; font-size: 16px; margin-left: 200px;"><fmt:message key="text.image_details.rating_edited"/> </p>
                                            </c:when>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${sessionScope.user.getUsername().equals(item.getUser().getUsername())
                                                and empty requestScope.rating}">
                                                <a style="margin-top: -20px; margin-left: 120px; font-size: 18px;"
                                                   href="${pageContext.request.contextPath}/home?command=${CommandType.START_EDIT_RATING}&image=${requestScope.image.getId()}&rating=${item.getId()}">
                                                    <fmt:message key="button.image_details.edit"/>
                                                </a>
                                            </c:when>
                                        </c:choose>

                                        <p>
                                           <br/> <b><fmt:message key="text.image_details.rating_review"/></b>
                                                ${item.getReview()}
                                        </p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </c:when>

                    <c:otherwise>
                        <p style="margin-top: 50px; margin-left: 200px;"><fmt:message key="text.image_details.no_reviews"/></p>
                        <br/> <br/> <br/> <br/> <br/> <br/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
<script>
    function showAlert() {
        alert ('You sure you want to order this design?');
    }

   function windowClose() {
       window.open('', '_parent', '');
       window.close();
   }
</script>
</body>
</html>