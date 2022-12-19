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
    <title><fmt:message key="title.signup"/></title>
    <script src="js/mask.js"></script>
    <script src="js/mask_list.js"></script>
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
                        <a style="font-size: 60px" class="header__menu-link" href="${pageContext.request.contextPath}/home?command=${CommandType.START_SIGN_UP}">
                            <fmt:message key="text.signup.title"/>
                        </a>
                    </li>
                </ul>
            </nav>
            <a class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.HOME}">
                <fmt:message key="text.other.back_to_home"/>
            </a>
            <a style="top: 100px" class="header__signUp-link" href="${pageContext.request.contextPath}/home?command=${CommandType.START_SIGN_IN}">
                <fmt:message key="text.signup.signin"/>
            </a>
        </div>
    </div>
</header>
<main class="main">
    <div class="sign">
        <div class="container">
            <form action="home" method="post">
                <input type="hidden" name="${AttributeParameterHolder.PARAMETER_COMMAND}" value="${CommandType.FINISH_SIGN_UP}">
                <div class="sign__inner">
                    <label>
                        <fmt:message key="label.username.required"/>
                    </label> <br/>
                    <label>
                        <input style="width: 20%" value="${requestScope.user_name}" placeholder="<fmt:message key="placeholder.username"/>" type="text"  name="${AttributeParameterHolder.PARAMETER_USER_NAME}" required/>
                    </label> <br/> <br/>

                    <label>
                        <fmt:message key="label.password.required"/>
                    </label> <br/>
                    <label>
                        <input style="width: 20%" value="${requestScope.user_password}" placeholder="<fmt:message key="placeholder.password"/>" type="password" id="password" name="${AttributeParameterHolder.PARAMETER_USER_PASSWORD}" required>
                        <i class="fa-regular fa-eye" id="togglePassword" style="margin-left: -30px; color: black; cursor: pointer;"></i>
                    </label> <br/> <br/>

                    <label>
                        <fmt:message key="label.password.repeat.required"/>
                    </label> <br/>
                    <label>
                        <input style="width: 20%" value="${requestScope.user_password}" placeholder="<fmt:message key="placeholder.password.repeat"/>" type="password" id="confirm_password" name="${AttributeParameterHolder.PARAMETER_USER_PASSWORD}" required>
                    </label> <br/> <br/>

                    <div class="sign__inner-item">
                        <label style="margin: 0;">
                            <fmt:message key="label.gender.required"/>
                        </label> <br/> <br/>
                        <c:choose>
                            <c:when test="${requestScope.user_gender.equals('male')}">
                                <label style="margin-left: 0" class="sign__inner-item-gender">
                                    <input type="radio" name="user_gender" value="male" required checked>
                                    <fmt:message key="label.gender.male"/>
                                </label>
                            </c:when>

                            <c:otherwise>
                                <label style="margin-left: 0" class="sign__inner-item-gender">
                                    <input type="radio" name="user_gender" value="male" required>
                                    <fmt:message key="label.gender.male"/>
                                </label>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${requestScope.user_gender.equals('female')}">
                                <label class="sign__inner-item-gender">
                                    <input type="radio" name="user_gender" value="female" required checked>
                                    <fmt:message key="label.gender.female"/>
                                </label>
                            </c:when>

                            <c:otherwise>
                                <label class="sign__inner-item-gender">
                                    <input type="radio" name="user_gender" value="female" required>
                                    <fmt:message key="label.gender.female"/>
                                </label>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${requestScope.user_gender.equals('other')}">
                                <label class="sign__inner-item-gender">
                                    <input type="radio" name="user_gender" value="other" required checked>
                                    <fmt:message key="label.gender.other"/>
                                </label>
                            </c:when>

                            <c:otherwise>
                                <label class="sign__inner-item-gender">
                                    <input type="radio" name="user_gender" value="other" required>
                                    <fmt:message key="label.gender.other"/>
                                </label>
                            </c:otherwise>
                        </c:choose>
                        <br/> <br/> <br/>
                        <label style="margin: 0;">
                            <fmt:message key="label.birth_date.required"/>
                        </label><br/>
                        <label style="margin-left: 0;">
                            <input style="width: 28%;" type="date" name="${AttributeParameterHolder.PARAMETER_USER_BIRTH_DATE}" required/>
                        </label> <br/> <br/>

                        <label style="margin: 0;">
                            <fmt:message key="label.email_address.required"/>
                        </label> <br/>
                        <label style="margin-left: 0;">
                            <input style="color: #000; width: 28%;" type="email" value="${requestScope.user_email_address}" placeholder="example@email.com" name="${AttributeParameterHolder.PARAMETER_USER_EMAIL_ADDRESS}" required/>
                        </label> <br/> <br/>
                    </div>

                    <label>
                        <fmt:message key="label.full_name"/>
                    </label> <br/>
                    <label>
                        <input style="text-transform: capitalize; width: 45%;" type="text" value="${requestScope.user_full_name}" placeholder="<fmt:message key="placeholder.full_name"/>" name="${AttributeParameterHolder.PARAMETER_USER_FULL_NAME}"/>
                    </label> <br/> <br/>

                    <label>
                        <fmt:message key="label.contact_number"/>
                    </label> <br/>

                    <label>
                        <input style="width: 45%" type="text" value="${requestScope.user_contact_number}" placeholder="<fmt:message key="placeholder.contact_number"/>" name="${AttributeParameterHolder.PARAMETER_USER_CONTACT_NUMBER}">
                        <script>mask('input[name="${AttributeParameterHolder.PARAMETER_USER_CONTACT_NUMBER}"]');</script>
                    </label> <br/> <br/>

                    <c:choose>
                        <c:when test="${not empty requestScope.error_message}">
                            <p style="position: absolute; left: 110px; margin-top: -10px; font-size: 15px; color:red;">
                                <fmt:message key="message.invalid.${requestScope.error_message}"/>
                            </p>
                            <br/>
                        </c:when>
                    </c:choose>
                    <input type="submit" style="background-color: gray; width: 45%;" value="<fmt:message key="button.signup"/>">
                    <img class="sign__upper-image"
                         src="https://user-images.githubusercontent.com/100201504/204161923-d6992e06-448a-4518-9a52-d0d47c06e793.png" alt="">
                </div>
            </form>
        </div>
    </div>
</main>
<jsp:include page="/view/fragment/footer.jsp"/>
<script>
    var input = document.querySelector("#phone");
    var iti = window.intlTelInput(input, {
        // separateDialCode:true,
        utilsScript: "https://cdn.jsdelivr.net/npm/intl-tel-input@17.0.3/build/js/utils.js",
    });

    // store the instance variable so we can access it in the console e.g. window.iti.getNumber()
    window.iti = iti;
</script>
</body>
<script>
    let password = document.getElementById("password")
        , confirm_password = document.getElementById("confirm_password");

    function validatePassword(){
        if(password.value !== confirm_password.value) {
            confirm_password.setCustomValidity("Passwords Don't Match");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;

    const togglePassword = document.querySelector('#togglePassword');

    togglePassword.addEventListener('click', function (e) {
        // toggle the type attribute
        const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
        password.setAttribute('type', type);
        // toggle the eye slash icon
        this.classList.toggle('fa-eye-slash');
    });
</script>
</html>