<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resources"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="create.request"/></title>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/signin.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
</head>
<body>
<div class="container">
    <div class="btn-group pull-left">
        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
            <fmt:message key="login.lang"/><span class="caret"></span>
        </button>
        <ul class="dropdown-menu" role="menu">
            <li><a href="login.jsp?language=en">English</a></li>
            <li><a href="login.jsp?language=ru">Русский</a></li>
        </ul>

        <a class="logout" href="${pageContext.request.contextPath}/logout">
            <button class="btn btn-default" type="button"><fmt:message key="button.logout"/></button>
        </a>
        <a class="logout" href="welcome.jsp">
            <button class="btn btn-default" type="button"><fmt:message key="button.back"/></button>
        </a>
        <p class="h1"><fmt:message key="title.requestPage"/></p>
    </div>
</div>
<div class="container">
    <div class="col-md-10">
        <div class="form-group">
            <label for="typeOfRequest">Request type for admin</label>
            <select class="form-control" id="typeOfRequest">
                <option>Create</option>
                <option>Delete</option>
                <option>Edit</option>
            </select>
        </div>
        <div class="form-group">
            <label for="activityNameForRequest">Activity name</label>
            <select class="form-control" id="activityNameForRequest">
                <option>Activity1</option>
                <option>Activity2</option>
                <option>Activity3</option>
            </select>
        </div>
        <div class="form-group">
            <label for="commentForAdmin">Comment</label>
            <input type="text" class="form-control" id="commentForAdmin"
                   placeholder="<fmt:message key="placeholder.comment"/>">
        </div>
    </div>
</div>
</body>
</html>
