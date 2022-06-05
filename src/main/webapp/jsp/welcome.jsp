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
    <title><fmt:message key="welcome.title"/></title>
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
        <p class="h1"><fmt:message key="placeholder.cabinet"/></p>
    </div>
</div>
<hr>
<div class="container">
    <div class="row">
        <div class="col-sm-2"><p><strong><fmt:message key="placeholder.username"/></strong></p></div>
        <div class="col-sm-2">${sessionScope.user.name}</div>
    </div>
    <div class="row">
        <div class="col-sm-2"><p><strong><fmt:message key="placeholder.email"/></strong></p></div>
        <div class="col-sm-2">${sessionScope.user.email}</div>
    </div>
    <div class="row">
        <div class="col-sm-2"><p><strong><fmt:message key="placeholder.status"/></strong></p></div>
        <c:if test="${sessionScope.user.admin}">
            <div class="col-sm-2"><fmt:message key="placeholder.adminstatus"/></div>
        </c:if>
        <c:if test="${!sessionScope.user.admin}">
            <div class="col-sm-2"><fmt:message key="placeholder.userstatus"/></div>
        </c:if>
    </div>
</div>
<hr>
<div class="container">
    <div class="col-md-10">
        <table class="table">
            <thead>
            <tr>
                <th><fmt:message key="userActivity.activityCategoryName"/></th>
                <th><fmt:message key="userActivity.activityName"/></th>
                <th><fmt:message key="userActivity.durationOfActivity"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="userActivity" items="${sessionScope.userActivities}">
                <tr>
                    <td>${userActivity.activityCategoryName}</td>
                    <td>${userActivity.activityName}</td>
                    <td>${userActivity.activityDuration}</td>
                    <td>
                        <form class="form-group" action="${pageContext.request.contextPath}/addUserActivityDuration"
                              method="post">
                            <input type="text" class="form" name="userActivityNewDuration"
                                   placeholder="<fmt:message key="placeholder.userActivityDuration"/>">
                            <input type="hidden" name="userActivityName" value="${userActivity.activityName}">
                            <button class="btn btn-xs btn-default" type="submit"><fmt:message
                                    key="button.submit"/></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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
            <input type="text" class="form-control" id="commentForAdmin" placeholder="<fmt:message key="placeholder.comment"/>">
        </div>
    </div>
</div>

<c:if test="${not empty sessionScope.Alert}">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="alert alert-danger"><p class="text-center"><strong>${sessionScope.Alert}</strong></p></div>
            <c:remove var="Alert"/>
        </div>
    </div>
</c:if>
</body>
</html>
