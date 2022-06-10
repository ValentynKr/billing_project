<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.session.getValue(language)}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html>
<head>

    <title><fmt:message key="registration.title" /></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>

</head>
<body>
<a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-secondary btn-lg active" role="button" aria-pressed="true"><fmt:message key="button.back"/></a>

<div class="container-fluid">
    <form class="form-signin" action="<c:url value="/registration"/>" method="post">
        <h2 class="form-signin-heading fade in text-center"><fmt:message key="registration.label"/></h2>
        <input type="text" class="form-control" name="name" placeholder="<fmt:message key="placeholder.username"/>" required/>
        <input type="text" class="form-control" name="email" placeholder="<fmt:message key="placeholder.email"/>" required/>
        <hr>
        <input type="password" class="form-control" name="password" placeholder="<fmt:message key="placeholder.password"/>" required/>
        <input type="password" class="form-control" name="confPassword" placeholder="<fmt:message key="placeholder.confirmPassword"/>" required/>
        <button class="btn btn-lg btn-default btn-block" type="submit"><fmt:message key="registration.button"/></button>
    </form>
    <c:if test="${not empty sessionScope.Alert}">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="alert alert-danger"><p class="text-center"><strong>${sessionScope.Alert}</strong></p></div>
                <c:remove var="Alert"/>
            </div>
        </div>
    </c:if>

</div>

<script src="../billing_project/js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>

</body>
</html>
