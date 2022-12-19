<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.demo.controller.command.CommandType" %>
<%@ page import="com.example.demo.controller.navigation.AttributeParameterHolder" %>
<fmt:setLocale value="${sessionScope.localization}" scope="session"/>
<fmt:setBundle basename="localization.localizedtext"/>
<html lang="${AttributeParameterHolder.SESSION_SCOPE_ATTRIBUTE_PARAMETER_LOCALIZATION}">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css">
    <jsp:include page="/view/fragment/head_links.jsp"/>
    <title>404</title>
</head>
<body>
<main>
    <div class="container">
        <div style="min-height: 400px;" class="error__info">
            <p style="margin-top: 300px; margin-left: 400px;">
                <fmt:message key="text.other.page_not_found"/>
                <i class="fa-regular fa-face-frown"></i>
            </p>
            <p style="margin-top: 20px; margin-left: 400px;">
                <a href="${pageContext.request.contextPath}/home?command=${CommandType.HOME}">
                    <fmt:message key="text.other.back_to_home"/>
                </a>
            </p>
        </div>
    </div>
</main>

</body>
</html>
